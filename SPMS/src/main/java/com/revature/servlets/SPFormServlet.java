package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		switch (uri) {
		case "/SPMS/spform/view": {
			//System.out.println(parameterNames);
			un = (String) session.getAttribute("username");
			pw = (String) session.getAttribute("password");
			System.out.println(un +" "+ pw);
			System.out.println(session.getAttribute("username"));
			List<SPForm> forms = null;
			int id = (Integer)session.getAttribute("id");
			System.out.println(id);
			forms = sdao.getForms(id);
			if (forms != null) { // got some forms
				System.out.println("GOT THE FORMS");
			}
			System.out.println(forms);
			response.getWriter().append(gson.toJson(forms));
			
			break;
		}
		case "/SPMS/spform/genres": {
			//System.out.println(parameterNames);
			un = (String) session.getAttribute("username");
			pw = (String) session.getAttribute("password");
			System.out.println(un +" "+ pw);
			System.out.println(session.getAttribute("username"));
			List<Genre> genres = null;
			genres = gdao.getGenres();
			
			System.out.println(genres);
			response.getWriter().append(gson.toJson(genres));
			break;
		}
		case "/SPMS/spform/submit": {
			System.out.println("IN SPMF SERVLET SUBMIT");
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
