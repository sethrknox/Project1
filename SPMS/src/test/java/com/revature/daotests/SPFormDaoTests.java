package com.revature.daotests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

import com.revature.beans.SPForm;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;

public class SPFormDaoTests {

	private SPFormDAO sdao = new SPFormDAOImpl();
	@Test
	public void addFormTest() {
		SPForm spf = new SPForm("Seth", "Knox", 1, "Test Title", new Date(System.currentTimeMillis()),
				 "Novel", "Fantasy", "Very cool book", "Description stuff", "draft");
		spf.setAe_id(1);
		spf.setGe_id(1);
		spf.setSe_id(1);
		sdao.addForm(spf);
		System.out.println(spf);
		assertNotEquals(null, spf.getId());
	}

}
