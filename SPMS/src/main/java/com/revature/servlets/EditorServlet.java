package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.beans.Editor;
import com.revature.daos.EditorDAO;
import com.revature.daos.EditorDAOImpl;

public class EditorServlet extends HttpServlet{

	private Gson gson = new Gson();
	private EditorDAO edao = new EditorDAOImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		System.out.println("In editor servlet");
		String uri = request.getRequestURI();
		System.out.println(uri);

		String un = "";
		String pw = "";
		switch (uri) {
		case "/SPMS/editor/login": {
			Editor ed = gson.fromJson(request.getReader(), Editor.class);
			System.out.println(ed);
			Editor e = edao.getEditor(ed.getUsername(), ed.getPassword());
			if (e != null) { // logged in
				session.setAttribute("username", un);
				session.setAttribute("password", pw);
				session.setAttribute("first", e.getFirst());
				session.setAttribute("last", e.getLast());
				session.setAttribute("id", e.getId());
				session.setAttribute("type", e.getType());
				
			}
			System.out.println(e);
			response.getWriter().append(gson.toJson(e));
			break;
		}
		case "/SPMS/editor/logout": {
			session.invalidate();
			break;
		}
		case "/SPMS/editor/viewforms": {
			System.out.println("Getting editor's forms");
			un = (String) session.getAttribute("username");
			pw = (String) session.getAttribute("password");
			System.out.println(un +" "+ pw);
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
