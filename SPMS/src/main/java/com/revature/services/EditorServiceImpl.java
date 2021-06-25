package com.revature.services;

import java.util.List;

import com.revature.beans.Editor;
import com.revature.beans.Genre;
import com.revature.daos.EditorDAO;
import com.revature.daos.EditorDAOImpl;

public class EditorServiceImpl implements EditorService {

	private EditorDAO edao = new EditorDAOImpl();
	@Override
	public Editor getEditor(String username, String password) {
		// TODO Auto-generated method stub
		return edao.getEditor(username, password);
	}
	@Override
	public List<Genre> getCommittees(Integer id) {
		// TODO Auto-generated method stub
		return edao.getCommittees(id);
	}

}
