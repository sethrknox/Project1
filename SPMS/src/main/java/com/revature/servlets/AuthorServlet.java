package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.daos.AuthorDAOImpl;
import com.revature.beans.Author;

public class AuthorServlet extends HttpServlet{

	private Gson gson = new Gson();
	private AuthorDAOImpl adao = new AuthorDAOImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		PrintWriter pw = response.getWriter();
//		pw.write("<h1>Hello from the AuthorServlet</h1>");
//		response.getWriter().write("<h2>No PrintWriter Object required.</h2>");
		HttpSession session = request.getSession();
		System.out.println("In author servlet");
		String uri = request.getRequestURI();
		System.out.println(uri);
//		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/hello.jsp");
//		rd.forward(request,response);
		String un = "";
		String pw = "";
		//List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
		switch (uri) {
		case "/SPMS/author/login": {
			//System.out.println(parameterNames);
			un = request.getParameter("username");
			pw = request.getParameter("password");
			System.out.println(un +" "+ pw);
			System.out.println(session.getAttribute("username"));
			Author a = adao.getAuthor(un, pw);
			if (a != null) { // logged in
				session.setAttribute("username", un);
				session.setAttribute("password", pw);
				session.setAttribute("first", a.getFirst());
				session.setAttribute("last", a.getLast());
				session.setAttribute("id", a.getId());
			}
			//response.sendRedirect("http://localhost:8080/SPMS/author.html");
			response.getWriter().append(gson.toJson(a));
			//response.getWriter().append("author.html");
			System.out.println("End of Author login");
			break;
		}
		case "/SPMS/author/logout": {
			session.invalidate();
			break;
		}
		case "/SPMS/author/viewforms": {
			System.out.println("Getting author's forms");
			un = (String) session.getAttribute("username");
			pw = (String) session.getAttribute("password");
			
		}
		default: {
			System.out.println("Author default");
			response.sendError(418, "Not implemented yet.");
		}
		}
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request,response);
	}
}
