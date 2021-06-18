package com.revature.daotests;

import java.sql.Date;

import org.junit.Test;

import com.revature.beans.Author;
import com.revature.beans.Genre;
import com.revature.beans.SPForm;
import com.revature.beans.Story;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;

public class SPFormDaoTests {

	private SPFormDAO sdao = new SPFormDAOImpl();
	@Test
	public void addFormTest() {
//		SPForm spf = new SPForm("Seth", "Knox", 1, "Test Title", new Date(System.currentTimeMillis()),
//			 "Novel", "Fantasy", "Very cool book", "Description stuff", "draft");
//		spf.setAe_id(1);
//		spf.setGe_id(1);
//		spf.setSe_id(1);
//		sdao.add(spf);
//		System.out.println(spf);
//		assertNotEquals(null, spf.getId());
		Author a = new Author();
		Story s = new Story();
		s.setType("article");
		Genre g = new Genre();
		g.setName("mystery");
		a.setId(1);
		SPForm spf = new SPForm("seth", "knox", a, "temp", new Date(System.currentTimeMillis()),
				s, g, "tag", "desc");
		spf.setSubmit_date(new Date(System.currentTimeMillis()));
		System.out.println(spf);
		sdao.add(spf);
		System.out.println(spf);
	}
	
	@Test
	public void getFormTest() {
		SPForm spf = sdao.getById(3);
		System.out.println(spf);
	}
	
	@Test
	public void getAuthorForms() {
		System.out.println(sdao.getForms(1));
	}

}
