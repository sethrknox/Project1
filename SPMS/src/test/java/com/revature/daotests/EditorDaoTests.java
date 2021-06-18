package com.revature.daotests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.beans.Editor;
import com.revature.daos.EditorDAO;
import com.revature.daos.EditorDAOImpl;

public class EditorDaoTests {
	
	private EditorDAO edao = new EditorDAOImpl();

	@Test
	public void registerEditorTest() {
		Editor e = new Editor("CoolEditor", "pw", "Cool", "Editor", "Assistant");
		edao.add(e);
		System.out.println(e.getId());
		assertNotEquals(null, e.getId());
	}

	@Test
	public void getEditorTest() {
		Editor e = null;
		e = edao.getEditor("CoolEditor", "pw");
		System.out.println(e);
		assertNotEquals(null, e);
	}
	
	@Test
	public void editorExistsTest() {
		boolean check = edao.usernameExists("editman");
		
		assertEquals(true, check);
	}
}
