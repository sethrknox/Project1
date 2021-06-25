package com.revature.services;

import com.revature.beans.Author;

public interface AuthorService {
	public Author getAuthor(String username, String password);
	public Integer getPoints(Integer id);
	public boolean registerAuthor(Author au);
}
