package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.beans.Author;
import com.revature.services.AuthorService;
import com.revature.services.AuthorServiceImpl;

public class AuthorServlet extends HttpServlet{

	private Gson gson = new Gson();
	private AuthorService as = new AuthorServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		System.out.println("In author servlet");
		String uri = request.getRequestURI();
		System.out.println("URI: "+uri);
		System.out.println("Session id: "+session.getId());
		//List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
		switch (uri) {
		case "/SPMS/author/login": {
			session.setMaxInactiveInterval(0);
			Author au = gson.fromJson(request.getReader(), Author.class);
			Author a = as.getAuthor(au.getUsername(), au.getPassword());
			if (a != null) { // logged in
				session.setAttribute("username", a.getUsername());
				session.setAttribute("password", a.getPassword());
				session.setAttribute("first", a.getFirst());
				session.setAttribute("last", a.getLast());
				session.setAttribute("id", a.getId());
				session.setAttribute("points", a.getPoints());
				response.getWriter().append(gson.toJson(a));
			} else {
				response.getWriter().append(gson.toJson("Login failed."));
			}			
			System.out.println("End of Author login");
			break;
		}
		case "/SPMS/author/register": {
			Author au = gson.fromJson(request.getReader(), Author.class);
			System.out.println(au);
			boolean b = as.registerAuthor(au);
			if (b) {
				response.getWriter().append(gson.toJson("Success"));
			} else {
				response.sendError(418, "Username not available.");
			}
			break;
		}
		case "/SPMS/author/logout": {
			session.invalidate();
			String msg = "Logged out";
			response.getWriter().append(gson.toJson(msg));
			break;
		}
		case "/SPMS/author/points": {
			//Integer points = (Integer)session.getAttribute("points");
			Integer points = as.getPoints((Integer)session.getAttribute("id"));
			response.getWriter().append(gson.toJson(points));
			System.out.println("Sent points to javascript: " + points);
			break;
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
