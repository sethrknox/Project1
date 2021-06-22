package com.revature.servlets;

import java.io.File;
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
import com.revature.beans.Genre;
import com.revature.beans.SPForm;
import com.revature.daos.GenreDAO;
import com.revature.daos.GenreDAOImpl;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;
import com.revature.services.SPFormService;
import com.revature.services.SPFormServiceImpl;

@MultipartConfig(maxFileSize = 10*1024*1024,maxRequestSize = 20*1024*1024,fileSizeThreshold = 5*1024*1024)
public class SPFormServlet extends HttpServlet{

	private Gson gson = new Gson();
	private SPFormService ss = new SPFormServiceImpl();
	private SPFormDAO sdao = new SPFormDAOImpl();
	private GenreDAO gdao = new GenreDAOImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		System.out.println("In spform servlet");
		String uri = request.getRequestURI();
		System.out.println(uri);
//		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/hello.jsp");
//		rd.forward(request,response);
		String un = "";
		String pw = "";
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
			//System.out.println(parameterNames);
//			un = (String) session.getAttribute("username");
//			pw = (String) session.getAttribute("password");
//			System.out.println(un +" "+ pw);
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
			//System.out.println(parameterNames);
//			un = (String) session.getAttribute("username");
//			pw = (String) session.getAttribute("password");
//			System.out.println(un +" "+ pw);
//			System.out.println(session.getAttribute("username"));
			List<Genre> genres = null;
			genres = gdao.getGenres();
			
			System.out.println(genres);
			response.getWriter().append(gson.toJson(genres));
			break;
		}
		case "/SPMS/spform/submit": {
			System.out.println("SPForm /submit");
//			System.out.println(request);
//			System.out.println(request.getParameterMap());
//			System.out.println(request.getParameterNames());
//			System.out.println(request.getParameter("0"));
//			System.out.println(request.getParameter("firstName"));
//			System.out.println(request.getQueryString());
			ObjectMapper om = new ObjectMapper();
			SPForm spf = om.readValue(request.getReader(), SPForm.class);
			ss.createForm(spf, (Integer)session.getAttribute("id"));
			//spf.setAuthor_id(session.getAttribute("id"));
			System.out.println(spf);
			//SPForm test = gson.fromJson(request.getReader(), SPForm.class);
			//Type collectionType = new TypeToken<Collection<SPForm>>(){}.getType();
			//String enums = gson.fromJson(request.getReader(), collectionType);
			//System.out.println(test);
			//System.out.println(enums);
			//System.out.println(request.getReader());
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
