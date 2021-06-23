package com.revature.services;

import java.util.List;

import com.revature.beans.EditorRequest;

public interface EditorRequestService {
	public void sendRequest(EditorRequest er, Integer id);
	public List<EditorRequest> getRequests(Integer id);
	public void sendReply(EditorRequest er);
	public List<EditorRequest> getEditorIncomingRequests(Integer id, String type);
	public List<EditorRequest> getEditorOutgoingRequests(Integer id, String type);
}
