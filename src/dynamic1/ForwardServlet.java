package dynamic1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893822131226590450L;

	private void forwardRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nextJSP = "/faces/home.xhtml";
		String user = req.getPathInfo();
		req.getSession().setAttribute("_user_", user == null ? "__default__" : user);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		forwardRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		forwardRequest(req, resp);
	}

}
