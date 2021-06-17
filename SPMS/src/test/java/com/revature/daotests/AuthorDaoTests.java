package com.revature.daotests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.beans.Author;
import com.revature.daos.AuthorDAO;
import com.revature.daos.AuthorDAOImpl;

public class AuthorDaoTests {

	private AuthorDAO adao = new AuthorDAOImpl();
	
	@Test
	public void registerAuthorTest() {
		Author a = new Author("sethk", "pass", "Seth", "Knox");
		adao.addAuthor(a);
		System.out.println(a.getId());
		assertNotEquals(null, a.getId());
	}
	
	@Test
	public void loginAuthorTest() {
		Author a = null;
		a = adao.getAuthor("sethk", "pass");
		System.out.println(a);
		assertNotEquals(null, a);
	}
	
	@Test
	public void authorExistsTest() {
		boolean check = adao.usernameExists("sethk");
		
		assertEquals(true, check);
	}
	
	
}
