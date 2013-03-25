package edu.cmu.andrew.vravuri.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.andrew.vravuri.model.DataStoreUtils;

/**
 * Performs delete action
 * 
 */
public class DeleteAction extends Action {

	@Override
	public String getName() {
		return "delete.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		DataStoreUtils.clearDataStore();
		HttpSession session = request.getSession();
		session.removeAttribute("reqToken");
		session.removeAttribute("accessToken");

		return "login.do";
	}
}
