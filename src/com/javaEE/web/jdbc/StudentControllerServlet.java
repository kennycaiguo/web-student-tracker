package com.javaEE.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//import org.apache.catalina.servlet4preview.RequestDispatcher;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet(name="/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		//create our student db util...and pass in the conn pool/datasource
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}catch (Exception exc) {
			throw new ServletException(exc);
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//List the student.... in MV fashion
			listStudents(request, response);
			}
			catch (Exception exc) {
				throw new ServletException(exc);
			}
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		
		//add student to the request
		request.setAttribute("STUDENT_LIST", students);
		
		//send to JSP page (view)
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
