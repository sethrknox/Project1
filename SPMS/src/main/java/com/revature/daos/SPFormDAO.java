package com.revature.daos;

import java.io.InputStream;
import java.util.List;

import com.revature.beans.EditorRequest;
import com.revature.beans.SPForm;

public interface SPFormDAO extends GenericDAO<SPForm>{

	//public void addForm(SPForm spf);
	public List<SPForm> getForms(Integer id);
	public void resubmit(Integer id);
	public List<SPForm> getEditorForms(Integer id, String type);
	public boolean hasAssistants(String genre);
	public void submitDraft(Integer form_id, InputStream file);
	public List<SPForm> getEditorDrafts(Integer id, String type);
}
