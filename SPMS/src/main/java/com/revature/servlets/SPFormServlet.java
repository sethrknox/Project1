package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.beans.Author;
import com.revature.beans.SPForm;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;


public class SPFormServlet extends HttpServlet{

	private Gson gson = new Gson();
	private SPFormDAO sdao = new SPFormDAOImpl();
	
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
