package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class EditorServlet extends HttpServlet{

	private Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter pw = response.getWriter();
		
		pw.write("<h1>Hello from the EditorServlet</h1>");
		
		response.getWriter().write("<h2>No PrintWriter Object required.</h2>");

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String first = request.getParameter("first");
		String last = request.getParameter("last");
		
		response.getWriter().write("Hello " + first + " " + last);
	}
}
