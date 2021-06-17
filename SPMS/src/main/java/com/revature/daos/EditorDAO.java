package com.revature.daos;

import com.revature.beans.Editor;

public interface EditorDAO {

	public void addEditor(Editor e);
	public Editor getEditor(String username, String password);
	public boolean usernameExists(String username);
}
