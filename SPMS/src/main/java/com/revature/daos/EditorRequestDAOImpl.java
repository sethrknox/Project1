package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.EditorRequest;
import com.revature.utils.HibernateUtil;
import com.revature.utils.JDBCConnection;

public class EditorRequestDAOImpl implements EditorRequestDAO {

	private Connection conn = JDBCConnection.getConnection();
	//private SPFormDAO sdao = new SPFormDAOImpl();
	@Override
	public void add(EditorRequest t) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.save(t);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
//		String sql = "insert into project1.requests values (default, ?, ?, ?, ?, null) returning *";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, t.getForm_id());
//			ps.setInt(2, t.getReq_id());
//			ps.setString(3, t.getReceiver());
//			ps.setString(4, t.getRequest());
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				// we inserted sucessfully
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public EditorRequest getById(Integer id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		EditorRequest er = null;
		
		try {
			tx = s.beginTransaction();
			er = s.get(EditorRequest.class, id);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
//		String sql = "select * from project1.requests where id = ?";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				EditorRequest temp = new EditorRequest();
//				temp.setId(rs.getInt("id"));
//				temp.setReceiver(rs.getString("receiver"));
//				temp.setRequest(rs.getString("request"));
//				temp.setResponse(rs.getString("response"));
//				//temp.setForm_id(sdao.getById(rs.getInt("form_id")));
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return er;
	}

	@Override
	public void update(EditorRequest t) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
	}

	@Override
	public List<EditorRequest> getRequests(Integer id) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = new ArrayList<EditorRequest>();
		String sql = "select r.id from project1.requests r left join project1.spforms s on r.form_id = s.id where r.receiver = 'author' and s.author_id = ? and r.response is null";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				EditorRequest er = getById(rs.getInt("id"));
				reqs.add(er);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reqs;
	}

	@Override
	public List<EditorRequest> getEditorIncomingRequests(Integer id, String type) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = new ArrayList<EditorRequest>();
		switch(type) {
		case "assistant": {
			String sql = "select r.id from project1.requests r left join project1.spforms s on r.form_id = s.id where r.receiver = 'ae' and s.ae_id = ?";
			
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					EditorRequest er = getById(rs.getInt("id"));
					reqs.add(er);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "general": {
			String sql = "select r.id from project1.requests r left join project1.spforms s on r.form_id = s.id where r.receiver = 'ge' and s.ge_id = ?";
			
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					EditorRequest er = getById(rs.getInt("id"));
					reqs.add(er);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "senior": {
			// seniors cannot have incoming requests for now
			break;
		}
		default: {
			break;
		}
		}
		return reqs;
	}

	@Override
	public List<EditorRequest> getEditorOutgoingRequests(Integer id, String type) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = new ArrayList<EditorRequest>();

		String sql = "select r.id from project1.requests r left join project1.spforms s on r.form_id = s.id where r.req_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				EditorRequest er = getById(rs.getInt("id"));
				reqs.add(er);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqs;
	}


}
