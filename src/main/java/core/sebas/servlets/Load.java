package core.sebas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Load extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1671509401766330487L;

	@Override
	protected void doGet(HttpServletRequest request,final HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/html/load.jsp");
		requestDispatcher.forward(request, response);
	}
	
}
