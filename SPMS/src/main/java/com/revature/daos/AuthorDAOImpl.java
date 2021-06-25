package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Author;
import com.revature.utils.HibernateUtil;
import com.revature.utils.JDBCConnection;

public class AuthorDAOImpl implements AuthorDAO {

	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public void add(Author a) {
		// TODO Auto-generated method stub
//		String sql = "insert into project1.authors values (default, ?, ?, ?, ?, default) returning *;";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, a.getUsername());
//			ps.setString(2, a.getPassword());
//			ps.setString(3, a.getFirst());
//			ps.setString(4, a.getLast());
//			
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				a.setId(rs.getInt("id"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.save(a);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
	}
	
	@Override
	public Author getById(Integer id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		Author a = null;
		
		try {
			tx = s.beginTransaction();
			a = s.get(Author.class, id);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		
		return a;
	}

	@Override
	public void update(Author t) {
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
	public Author getAuthor(String username, String password) {
		// TODO Auto-generated method stub
//		String sql = "select * from project1.authors where username = ? and password = ?";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, username);
//			ps.setString(2, password);
//			ResultSet rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				Author a = new Author();
//				a.setId(rs.getInt("id"));
//				a.setUsername(username);
//				a.setPassword(password);
//				a.setFirst(rs.getString("first"));
//				a.setLast(rs.getString("last"));
//				a.setPoints(rs.getInt("points"));
//				return a;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		Author a = null;
		
		try {
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Author> cq = cb.createQuery(Author.class);
			Root<Author> root = cq.from(Author.class);
			cq.select(root).where(cb.and(
					cb.equal(root.get("username"), username),
					cb.equal(root.get("password"), password)));
			
			a = s.createQuery(cq).getSingleResult();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		return a;
	}

	@Override
	public boolean usernameExists(String username) {
		// TODO Auto-generated method stub
		String sql = "(select a.username from project1.authors a where a.username = ?) union (select e.username from project1.editors e where e.username = ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePoints(Integer id, Integer cost, Integer curr_points) {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaUpdate<Author> cu = cb.createCriteriaUpdate(Author.class);
			Root<Author> root = cu.from(Author.class);
			cu.set("points", curr_points - cost);
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
	public Integer getPoints(Integer id) {
		// TODO Auto-generated method stub
		Integer points = -1;
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Author> cq = cb.createQuery(Author.class);
			Root<Author> root = cq.from(Author.class);
			cq.select(root).where(cb.equal(root.get("id"), id));
			
			Author a = s.createQuery(cq).getSingleResult();
			points = a.getPoints();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		return points;
	}

	

}
