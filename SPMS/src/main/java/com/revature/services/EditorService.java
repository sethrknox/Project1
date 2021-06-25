package com.revature.services;

import java.util.List;

import com.revature.beans.Editor;
import com.revature.beans.Genre;

public interface EditorService {
	public Editor getEditor(String username, String password);

	public List<Genre> getCommittees(Integer id);
}
