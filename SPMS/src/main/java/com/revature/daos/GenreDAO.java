package com.revature.daos;

import java.util.List;

import com.revature.beans.Genre;

public interface GenreDAO extends GenericDAO<Genre> {

	public List<Genre> getGenres();
}
