package com.revature.services;

import java.sql.Date;

import com.revature.beans.Author;
import com.revature.beans.SPForm;
import com.revature.daos.AuthorDAOImpl;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;

public class SPFormServiceImpl implements SPFormService {

	private SPFormDAO sdao = new SPFormDAOImpl();
	private AuthorDAOImpl adao = new AuthorDAOImpl();
	
	@Override
	public void createForm(SPForm spf, Integer author_id) {
		// TODO Auto-generated method stub
		Author a = adao.getById(author_id);
		spf.setAuthor_id(a);
		Date d = new Date(System.currentTimeMillis());
		spf.setSubmit_date(d);
		Integer points = a.getPoints();
		Integer cost = 0;
		String type = spf.getStory_type().getType();
		if ("novel".equals(type)) {
			cost = 50;
		} else if ("novella".equals(type)) {
			cost = 25;
		} else if ("short story".equals(type)) {
			cost = 20;
		} else if ("article".equals(type)) {
			cost = 10;
		}
		if (points < cost) {
			spf.setStatus("on hold");
		} else {
			spf.setStatus("pending");
			adao.updatePoints(cost);
		}
		sdao.add(spf);
	}

}
