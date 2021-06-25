package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.revature.beans.EditorRequest;
import com.revature.beans.Genre;
import com.revature.beans.SPForm;
import com.revature.services.EditorRequestService;
import com.revature.services.EditorRequestServiceImpl;
import com.revature.services.GenreService;
import com.revature.services.GenreServiceImpl;
import com.revature.services.SPFormService;
import com.revature.services.SPFormServiceImpl;

@MultipartConfig(maxFileSize = 10*1024*1024,maxRequestSize = 20*1024*1024,fileSizeThreshold = 5*1024*1024)
public class SPFormServlet extends HttpServlet{

	private Gson gson = new Gson();
	private SPFormService ss = new SPFormServiceImpl();
	private EditorRequestService ers = new EditorRequestServiceImpl();
	private GenreService gs = new GenreServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		System.out.println("In spform servlet");
		String uri = request.getRequestURI();
		System.out.println("URI: "+uri);
		System.out.println("Session id: " + session.getId());
		//List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
		Integer draft_id = 0;
		if (uri.matches("/SPMS/spform/draft/([0-9]*)")) {
			System.out.println("WE GOT A DRAFT");
			draft_id = Integer.parseInt(uri.substring(19));
			uri = uri.substring(0, 18);
			System.out.println(uri);
			System.out.println(draft_id);
		}
		switch (uri) {
		case "/SPMS/spform/view": {
			System.out.println("SPForm /view");
			List<SPForm> forms = null;
			int id = (Integer)session.getAttribute("id");
			System.out.println(id);
			forms = ss.getForms(id);
			if (forms != null) { // got some forms
				System.out.println("GOT THE FORMS");
			}
			System.out.println(forms);
			response.getWriter().append(gson.toJson(forms));
			
			break;
		}
		case "/SPMS/spform/genres": {
			System.out.println("SPForm /genres");
			List<Genre> genres = null;
			genres = gs.getGenres();
			
			System.out.println(genres);
			response.getWriter().append(gson.toJson(genres));
			break;
		}
		case "/SPMS/spform/submit": {
			System.out.println("SPForm /submit");
			ObjectMapper om = new ObjectMapper();
			SPForm spf = om.readValue(request.getReader(), SPForm.class);
			System.out.println(spf);
			ss.createForm(spf, (Integer)session.getAttribute("id"));
			//spf.setAuthor_id(session.getAttribute("id"));
			System.out.println(spf);
			//SPForm test = gson.fromJson(request.getReader(), SPForm.class);
			response.getWriter().append(gson.toJson("Submitted form."));
			break;
		}
		case "/SPMS/spform/resubmit": {
			System.out.println("SPForm /resubmit");
			Integer form_id = gson.fromJson(request.getReader(), Integer.class);
			System.out.println(form_id);
			ss.resubmit(form_id, (Integer)session.getAttribute("id"));
			response.getWriter().append(gson.toJson(true));
			break;
		}
		case "/SPMS/spform/editor/view": {
			System.out.println("SPForm /editor/view");
			Integer id = (Integer)session.getAttribute("id");
			String type = (String)session.getAttribute("type");
			System.out.println(id);
			System.out.println(type);
			List<SPForm> forms = ss.getEditorForms(id, type);
			if (forms == null) {
				//failed to get forms
			}
			System.out.println(forms);
			response.getWriter().append(gson.toJson(forms));
			break;
		}
		case "/SPMS/spform/editor/view/drafts": {
			System.out.println("SPFORM /view/drafts");
			Integer id = (Integer)session.getAttribute("id");
			String type = (String)session.getAttribute("type");
			System.out.println(id);
			System.out.println(type);
			List<SPForm> forms = ss.getEditorDrafts(id, type);
			if (forms == null) {
				
			}
			System.out.println(forms);
			response.getWriter().append(gson.toJson(forms));
			break;
		}
		case "/SPMS/spform/approve": {
			System.out.println("SPForm /approve");
			Integer editor_id = (Integer)session.getAttribute("id");
			System.out.println("Editor id: "+editor_id);
			Integer form_id = gson.fromJson(request.getReader(), Integer.class);
			System.out.println("Form id: "+form_id);
			String editor_type = (String)session.getAttribute("type");
			System.out.println("Editor: "+editor_type);
			
			ss.approve(form_id, editor_id, editor_type);
			response.getWriter().append(gson.toJson("approved"));
			break;
		}
		case "/SPMS/spform/deny": {
			System.out.println("SPForm /deny");
			Integer editor_id = (Integer)session.getAttribute("id");
			System.out.println("Editor id: "+editor_id);
			String editor_type = (String)session.getAttribute("type");
			System.out.println("Editor: "+editor_type);
			SPForm obj = gson.fromJson(request.getReader(), SPForm.class);
			System.out.println("JSON: "+obj);
			ss.deny(obj.getId(), obj.getDenial_reason(), editor_id, editor_type);
			response.getWriter().append(gson.toJson("denied"));
			break;
		}
		case "/SPMS/spform/draft": {
			InputStream inputStream = null;
			Part filePart = request.getPart("file");
			if(filePart != null) {
				System.out.println(filePart.getName());
	            System.out.println(filePart.getSize());
	            System.out.println(filePart.getContentType());
	             
	            // obtains input stream of the upload file
	            inputStream = filePart.getInputStream();
			}
			String fileName = String.valueOf("fileName");
			ss.submitDraft(draft_id, inputStream);
			response.getWriter().append(gson.toJson("submitted"));
			break;
		}
		case "/SPMS/spform/draft/approve": {
			System.out.println("SPForm /draft/approve");
			Integer editor_id = (Integer)session.getAttribute("id");
			System.out.println("Editor id: "+editor_id);
			Integer form_id = gson.fromJson(request.getReader(), Integer.class);
			System.out.println("Form id: "+form_id);
			String editor_type = (String)session.getAttribute("type");
			System.out.println("Editor: "+editor_type);
			
			ss.approveDraft(form_id, editor_id, editor_type);
			response.getWriter().append(gson.toJson("approved"));
			break;
		}
		case "/SPMS/spform/request": {
			System.out.println("SPForm /request");
			ObjectMapper om = new ObjectMapper();
			EditorRequest er = om.readValue(request.getReader(), EditorRequest.class);
			//EditorRequest er = gson.fromJson(request.getReader(), EditorRequest.class);
			//er.setReq_id((Integer)session.getAttribute("id"));
			System.out.println(er);
			ers.sendRequest(er, (Integer)session.getAttribute("id"));
			response.getWriter().append(gson.toJson("submitted"));
			break;
		}
		case "/SPMS/spform/author/view/requests": {
			System.out.println("SPForm /author/view/requests");
			List<EditorRequest> reqs = ers.getRequests((Integer)session.getAttribute("id"));
			response.getWriter().append(gson.toJson(reqs));
			break;
		}
		case "/SPMS/spform/reply": {
			System.out.println("SPForm /reply");
			EditorRequest er = gson.fromJson(request.getReader(), EditorRequest.class);
			System.out.println(er);
			ers.sendReply(er);
			response.getWriter().append(gson.toJson("reply sent"));
			break;
		}
		case "/SPMS/spform/editor/view/incoming": {
			System.out.println("SPForm /editor/view/incoming");
			Integer editor_id = (Integer)session.getAttribute("id");
			System.out.println("Editor id: "+editor_id);
			String editor_type = (String)session.getAttribute("type");
			System.out.println("Editor: "+editor_type);
			
			List<EditorRequest> reqs = ers.getEditorIncomingRequests(editor_id, editor_type);
			response.getWriter().append(gson.toJson(reqs));
			break;
		}
		case "/SPMS/spform/editor/view/outgoing": {
			System.out.println("SPForm /editor/view/outgoing");
			Integer editor_id = (Integer)session.getAttribute("id");
			System.out.println("Editor id: "+editor_id);
			String editor_type = (String)session.getAttribute("type");
			System.out.println("Editor: "+editor_type);
			
			List<EditorRequest> reqs = ers.getEditorOutgoingRequests(editor_id, editor_type);
			response.getWriter().append(gson.toJson(reqs));
			break;
		}
		case "/SPMS/spform/edit": {
			System.out.println("SPform /edit");
			ObjectMapper om = new ObjectMapper();
			SPForm spf = om.readValue(request.getReader(), SPForm.class);
			System.out.println(spf);
			ss.requestEdit(spf);
			response.getWriter().append(gson.toJson("sent author the edit request"));
			break;
			
		}
		case "/SPMS/spform/author/approve": {
			System.out.println("SPForm /author/approve");
			Integer form_id = gson.fromJson(request.getReader(), Integer.class);
			System.out.println(form_id);
			ss.updateEdits(form_id, "approve");
			response.getWriter().append(gson.toJson("author approved changes"));
			break;
		}
		case "/SPMS/spform/author/deny": {
			System.out.println("SPForm /author/deny");
			Integer form_id = gson.fromJson(request.getReader(), Integer.class);
			System.out.println(form_id);
			ss.updateEdits(form_id, "deny");
			response.getWriter().append(gson.toJson("author denied changes"));
			break;
		}
		default: {
			System.out.println("SPForm default");
			response.sendError(418, "Not implemented yet.");
		}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
