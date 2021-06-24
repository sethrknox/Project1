package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Editor;
import com.revature.utils.HibernateUtil;
import com.revature.utils.JDBCConnection;

public class EditorDAOImpl implements EditorDAO {

	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public void add(Editor e) {
		// TODO Auto-generated method stub
//		String sql = "insert into project1.editors values (default, ?, ?, ?, ?, ?) returning *;";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, e.getUsername());
//			ps.setString(2, e.getPassword());
//			ps.setString(3, e.getFirst());
//			ps.setString(4, e.getLast());
//			ps.setString(5, e.getType());
//			
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				e.setId(rs.getInt("id"));
//			}
//		} catch (SQLException x) {
//			// TODO Auto-generated catch block
//			x.printStackTrace();
//		}
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.save(e);
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
	}
	
	@Override
	public Editor getById(Integer id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		Editor e = null;
		
		try {
			tx = s.beginTransaction();
			e = s.get(Editor.class, id);
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		
		return e;
	}

	@Override
	public void update(Editor t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Editor getEditor(String username, String password) {
		// TODO Auto-generated method stub
//		String sql = "select * from project1.editors where username = ? and password = ?";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, username);
//			ps.setString(2, password);
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				Editor e = new Editor();
//				e.setId(rs.getInt("id"));
//				e.setUsername(username);
//				e.setPassword(password);
//				e.setFirst(rs.getString("first"));
//				e.setLast(rs.getString("last"));
//				e.setType(rs.getString("type"));
//				return e;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		Editor e = null;
		
		try {
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Editor> cq = cb.createQuery(Editor.class);
			Root<Editor> root = cq.from(Editor.class);
			cq.select(root).where(cb.and(
					cb.equal(root.get("username"), username),
					cb.equal(root.get("password"), password)));
			
			e = s.createQuery(cq).getSingleResult();
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		return e;
	}
	
	@Override
	public boolean usernameExists(String username) {
		// TODO Auto-generated method stub
		String sql = "(select username from project1.authors) union (select username from project1.editors)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				if (rs.getString("username").equals(username)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
