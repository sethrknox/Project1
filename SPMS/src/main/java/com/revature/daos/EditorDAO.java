package com.revature.daos;

import java.util.List;

import com.revature.beans.Editor;
import com.revature.beans.Genre;

public interface EditorDAO extends GenericDAO<Editor>{

	//public void addEditor(Editor e);
	public Editor getEditor(String username, String password);
	public boolean usernameExists(String username);
	public List<Genre> getCommittees(Integer id);
}
