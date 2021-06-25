package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.beans.Editor;
import com.revature.beans.Genre;
import com.revature.services.EditorService;
import com.revature.services.EditorServiceImpl;

public class EditorServlet extends HttpServlet{

	private Gson gson = new Gson();
	private EditorService es = new EditorServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		System.out.println("In editor servlet");
		String uri = request.getRequestURI();
		System.out.println("URI: "+uri);
		System.out.println("Session id: " + session.getId());
		switch (uri) {
		case "/SPMS/editor/login": {
			session.setMaxInactiveInterval(0);
			Editor ed = gson.fromJson(request.getReader(), Editor.class);
			System.out.println(ed);
			Editor e = es.getEditor(ed.getUsername(), ed.getPassword());
			if (e != null) { // logged in
				session.setAttribute("username", e.getUsername());
				session.setAttribute("password", e.getPassword());
				session.setAttribute("first", e.getFirst());
				session.setAttribute("last", e.getLast());
				session.setAttribute("id", e.getId());
				session.setAttribute("type", e.getType());
				response.getWriter().append(gson.toJson(e));
			} else {
				response.getWriter().append(gson.toJson("Login failed."));
			}
			//System.out.println(e);
			System.out.println("End of editor login");
			break;
		}
		case "/SPMS/editor/logout": {
			session.invalidate();
			String msg = "Logged out";
			response.getWriter().append(gson.toJson(msg));
			break;
		}
		case "/SPMS/editor/type": {
			String type = (String)session.getAttribute("type");
			response.getWriter().append(gson.toJson(type));
			break;
		}
		case "/SPMS/editor/id": {
			Integer id = (Integer)session.getAttribute("id");
			response.getWriter().append(gson.toJson(id));
			break;
		}
		case "/SPMS/editor/committees": {
			Integer id = (Integer)session.getAttribute("id");
			List<Genre> committees = es.getCommittees(id);
			response.getWriter().append(gson.toJson(committees));
			break;
		}
		default: {
			System.out.println("Editor default");
			response.sendError(418, "Not implemented yet.");
		}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request,response);
	}
}
