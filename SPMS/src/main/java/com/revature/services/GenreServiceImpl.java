package com.revature.services;

import java.util.List;

import com.revature.beans.Genre;
import com.revature.daos.GenreDAO;
import com.revature.daos.GenreDAOImpl;

public class GenreServiceImpl implements GenreService {

	private GenreDAO gdao = new GenreDAOImpl();
	@Override
	public List<Genre> getGenres() {
		// TODO Auto-generated method stub
		return gdao.getGenres();
	}

}
