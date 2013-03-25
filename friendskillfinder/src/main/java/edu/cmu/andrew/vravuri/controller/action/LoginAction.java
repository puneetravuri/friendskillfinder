package edu.cmu.andrew.vravuri.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scribe.model.Token;

/**
 * Performs login action
 * 
 */
public class LoginAction extends Action {

	@Override
	public String getName() {
		return "login.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Token accessToken = (Token) session.getAttribute("accessToken");

		if (accessToken != null)
			return "display.do";
		else
			return "login.jsp";
	}
}
