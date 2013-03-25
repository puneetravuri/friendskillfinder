package edu.cmu.andrew.vravuri.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cmu.andrew.vravuri.controller.action.Action;
import edu.cmu.andrew.vravuri.controller.action.AuthenticateAction;
import edu.cmu.andrew.vravuri.controller.action.DeleteAction;
import edu.cmu.andrew.vravuri.controller.action.DisplayAction;
import edu.cmu.andrew.vravuri.controller.action.LoginAction;

/**
 * Servlet implementation class EndorsementLister
 */
public class EndorsementLister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Action.add(new AuthenticateAction());
		Action.add(new LoginAction());
		Action.add(new DisplayAction());
		Action.add(new DeleteAction());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		String action = path.substring(path.lastIndexOf('/') + 1);

		String nextPage = Action.perform(action, request);

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
		} else if (nextPage.endsWith(".jsp")) {
			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);
		} else {
			response.sendRedirect(nextPage);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
