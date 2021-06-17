package com.revature.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class FrontControllerServlet extends HttpServlet {

	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		//response.getWriter().append("Welcome to the Front Controller");
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		//response.getWriter().append("\n" + uri);
		
		HttpSession session = request.getSession();
		session.setAttribute("loggedInUser", "{'username':'seth','password':'potato'}");
		
		System.out.println(session.getAttribute("loggedInUser"));
		session.setMaxInactiveInterval(0);
		
		switch (uri) {
//		case "/SPMS/": {
////			RequestDispatcher rd = request.getRequestDispatcher("index.html");
////			rd.forward(request,response);
//			System.out.println("HERE");
//			response.sendRedirect("src/index.html");
//			break;
//		}
		case "/SPMS/invalidate": {
			session.invalidate();
			break;
		}
		case "/SPMS/id.do": {
			response.getWriter().append("\n"+session.getId());
			break;
		}
		case "/SPMS/author/login.do": {
			System.out.println("calling author servlet");
			response.sendRedirect("/SPMS/author");
//			RequestDispatcher rd = request.getRequestDispatcher("/SPMS/author");
//			rd.forward(request, response);
			break;
		}
		default: {
			System.out.println("Default case");
			response.sendError(418, "Not implemented yet.");
			break;
		}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		doGet(request, response);
	}
}
