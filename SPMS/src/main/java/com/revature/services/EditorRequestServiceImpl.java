package com.revature.services;

import java.util.List;

import com.revature.beans.Editor;
import com.revature.beans.EditorRequest;
import com.revature.daos.EditorDAO;
import com.revature.daos.EditorDAOImpl;
import com.revature.daos.EditorRequestDAO;
import com.revature.daos.EditorRequestDAOImpl;

public class EditorRequestServiceImpl implements EditorRequestService {

	private EditorRequestDAO erdao = new EditorRequestDAOImpl();
	private EditorDAO edao = new EditorDAOImpl();
	@Override
	public void sendRequest(EditorRequest er, Integer id) {
		// TODO Auto-generated method stub
		Editor e = edao.getById(id);
		er.setReq_id(e);
		erdao.add(er);
	}
	@Override
	public List<EditorRequest> getRequests(Integer id) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = erdao.getRequests(id);
		return reqs;
	}
	@Override
	public void sendReply(EditorRequest er) {
		// TODO Auto-generated method stub
		EditorRequest temp = erdao.getById(er.getId());
		temp.setResponse(er.getResponse());
		erdao.update(temp);
	}
	@Override
	public List<EditorRequest> getEditorIncomingRequests(Integer id, String type) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = erdao.getEditorIncomingRequests(id, type);
		return reqs;
		
	}
	@Override
	public List<EditorRequest> getEditorOutgoingRequests(Integer id, String type) {
		// TODO Auto-generated method stub
		List<EditorRequest> reqs = erdao.getEditorOutgoingRequests(id, type);
		return reqs;
	}

}
