package com.revature.daos;

import com.revature.beans.Author;

public interface AuthorDAO extends GenericDAO<Author> {

	//public void addAuthor(Author a);
	public Author getAuthor(String username, String password);
	public boolean usernameExists(String username);
	public void updatePoints(Integer id, Integer cost, Integer curr_points);
	public Integer getPoints(Integer id);
}
