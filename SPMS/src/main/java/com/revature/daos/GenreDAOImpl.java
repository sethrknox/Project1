package com.revature.daos;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Genre;
import com.revature.beans.SPForm;
import com.revature.utils.HibernateUtil;

public class GenreDAOImpl implements GenreDAO{

	@Override
	public void add(Genre t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Genre getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Genre t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Genre> getGenres() {
		// TODO Auto-generated method stub
		Session s = HibernateUtil.getSession();
		Transaction tx = null;
		List<Genre> genres = null;
		
		try {
			tx = s.beginTransaction();
			
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
			Root<Genre> root = cq.from(Genre.class);
			cq.select(root);
			
			genres = s.createQuery(cq).getResultList();
			
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			s.close();
		}
		
		return genres;
	}

}
