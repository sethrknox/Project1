package com.revature.daos;

import com.revature.beans.Author;

public interface AuthorDAO {

	public void addAuthor(Author a);
	public Author getAuthor(String username, String password);
	public boolean usernameExists(String username);
}
