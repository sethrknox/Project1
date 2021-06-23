package com.revature.daos;

import java.util.List;

import com.revature.beans.EditorRequest;

public interface EditorRequestDAO extends GenericDAO<EditorRequest>{
	
	public List<EditorRequest> getRequests(Integer id);
	public List<EditorRequest> getEditorIncomingRequests(Integer id, String type);
	public List<EditorRequest> getEditorOutgoingRequests(Integer id, String type);
}
