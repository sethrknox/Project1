package com.revature.daos;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Author;
import com.revature.beans.Genre;
import com.revature.beans.SPForm;
import com.revature.beans.Story;
import com.revature.utils.HibernateUtil;
import com.revature.utils.JDBCConnection;

public class SPFormDAOImpl implements SPFormDAO {
	private EntityManager em;
	private Connection conn = JDBCConnection.getConnection();
	private AuthorDAO adao = new AuthorDAOImpl();
	
	@Override
	public void add(SPForm spf) {
		// TODO Auto-generated method stub
//		String sql = "insert into project1.spforms values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
//				"?, default, default, default, ?, default"+
//				", ?, default, ?, default, default, default) returning *";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, spf.getAuthor_first());
//			ps.setString(2, spf.getAuthor_last());
//			ps.setInt(3, spf.getAuthor_id());
//			ps.setString(4, spf.getTitle());
//			ps.setDate(5, spf.getEnd_date());
//			ps.setString(6, spf.getStory_type().toLowerCase());
//			ps.setString(7, spf.getGenre().toLowerCase());
//			ps.setString(8, spf.getTag_line());
//			ps.setString(9, spf.getDescription());
//			ps.setString(10, spf.getDraft());
//			
//			ps.setInt(11, spf.getAe_id());
//			ps.setInt(12, spf.getGe_id());
//			ps.setInt(13, spf.getSe_id());
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				spf.setId(rs.getInt("id"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.save(spf);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
	}
	
	@Override
	public void update(SPForm t) {
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
	public SPForm getById(Integer id) {
		// TODO Auto-generated method stub
//		String sql = "select * from ((project1.spforms spf left join project1.genres g on spf.genre = g.id)"+
//					" left join project1.stories s on spf.story_type = s.id) where id = ?";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				SPForm spf = new SPForm();
//				spf.setAuthor_id(rs.getInt("id"));
//				spf.setAuthor_first(rs.getString("first"));
//				spf.setAuthor_last(rs.getString("last"));
//				spf.setTitle(rs.getString("title"));
//				spf.setEnd_date(rs.getDate("end_date"));
//				spf.setStory_type(rs.getString("type"));
//				spf.setGenre(rs.getString("name"));
//				spf.setTag_line(rs.getString("tag_line"));
//				spf.setDescription(rs.getString("description"));
//				spf.setDraft(rs.getString("draft"));
//				spf.setStatus(rs.getString("status"));
//				spf.setSubmit_date(rs.getDate("submit_date"));
//				spf.setPriority(rs.getString("priority"));
//				spf.setAe_id(rs.getInt("ae_id"));
//				spf.setAe_approval(rs.getString("ae_approval"));
//				spf.setGe_id(rs.getInt("ge_id"));
//				spf.setGe_approval(rs.getString("ge_approval"));
//				spf.setSe_id(rs.getInt("se_id"));
//				spf.setSe_approval(rs.getString("se_approval"));
//				spf.setDenial_reason(rs.getString("denial_reason"));
//				spf.setSe_edit(rs.getBoolean("se_edit"));
//				return spf;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		SPForm spf = null;
		
		try {
			tx = s.beginTransaction();
			spf = s.get(SPForm.class, id);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		
		return spf;
	}

	@Override
	public List<SPForm> getForms(Integer id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		List<SPForm> forms = null;
		try {
			tx = s.beginTransaction();
			
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<SPForm> cq = cb.createQuery(SPForm.class);
			Root<SPForm> root = cq.from(SPForm.class);
			cq.select(root).where(cb.equal(root.get("author_id"),id));
			
			forms = s.createQuery(cq).getResultList();
			
			
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		return forms;
	}

	@Override
	public void resubmit(Integer id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaUpdate<SPForm> cu = cb.createCriteriaUpdate(SPForm.class);
			Root<SPForm> root = cu.from(SPForm.class);
			cu.set("status", "pending");
			cu.where(cb.equal(root.get("id"), id));
			
			s.createQuery(cu).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
	}

	@Override
	public List<SPForm> getEditorForms(Integer id, String type) {
		// TODO Auto-generated method stub
		List<SPForm> forms = new ArrayList<SPForm>();
		switch(type) {
		case "assistant": {
			String sql = "select * from project1.spforms s left join project1.committees c on s.genre = c.genre where s.status = 'pending' and s.ae_approval = 'pending' and c.editor_id = ?";
			
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					SPForm spf = getById(rs.getInt("id"));
					forms.add(spf);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "general": {
			String sql = "select * from (select s.id from project1.spforms s left join project1.committees c on s.genre != c.genre where s.status = 'pending' and s.ae_approval = 'approved' and s.ge_approval = 'pending' and c.editor_id = ?) as foo group by foo.id";
			
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					SPForm spf = getById(rs.getInt("id"));
					forms.add(spf);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "senior": {
			String sql = "select * from project1.spforms s left join project1.committees c on s.genre = c.genre where s.status = 'pending' and s.ge_approval = 'approved' and s.se_approval = 'pending' and c.editor_id = ?";
			
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					SPForm spf = getById(rs.getInt("id"));
					forms.add(spf);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		default: {
			forms = null;
			break;
		}
		}
		return forms;
	}

	@Override
	public boolean hasAssistants(String genre) {
		// TODO Auto-generated method stub
		String sql = "select * from project1.committees where genre = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, genre);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void submitDraft(Integer form_id, InputStream file) {
		// TODO Auto-generated method stub
		String sql = "update project1.spforms set draft = ? where id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(2, form_id);
			ps.setBinaryStream(1, file);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
