package com.revature.services;

import com.revature.beans.Author;
import com.revature.daos.AuthorDAOImpl;

public class AuthorServiceImpl implements AuthorService {

	private AuthorDAOImpl adao = new AuthorDAOImpl();
	@Override
	public Author getAuthor(String username, String password) {
		// TODO Auto-generated method stub
		return adao.getAuthor(username, password);
	}

	@Override
	public Integer getPoints(Integer id) {
		// TODO Auto-generated method stub
		return adao.getPoints(id);
	}

	@Override
	public boolean registerAuthor(Author au) {
		// TODO Auto-generated method stub
		// check is false when username doesn't exists -> can register this account
		boolean check = adao.usernameExists(au.getUsername());
		if (!check) {
			au.setPoints(100);
			adao.add(au);
		}
		return !check;
	}

}
