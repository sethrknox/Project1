package com.revature.services;

import java.io.InputStream;
import java.util.List;

import com.revature.beans.SPForm;

public interface SPFormService {

	public void createForm(SPForm spf, Integer author_id);
	public void resubmit(Integer form_id, Integer author_id);
	public List<SPForm> getEditorForms(Integer id, String type);
	public List<SPForm> getForms(Integer id);
	public void approve(Integer form_id, Integer editor_id, String type);
	public void deny(Integer form_id, String reason, Integer editor_id, String type);
	public void submitDraft(Integer draft_id, InputStream inputStream);
	public List<SPForm> getEditorDrafts(Integer id, String type);
	public void approveDraft(Integer form_id, Integer editor_id, String editor_type);
}
