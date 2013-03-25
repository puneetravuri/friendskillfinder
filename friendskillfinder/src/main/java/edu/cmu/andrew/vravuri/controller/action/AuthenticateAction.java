package edu.cmu.andrew.vravuri.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import edu.cmu.andrew.vravuri.utils.OAuthUtils;


/**
 * Performs authenticate action
 * 
 */
public class AuthenticateAction extends Action {

	@Override
	public String getName() {
		return "authenticate.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Token reqToken = (Token) session.getAttribute("reqToken");
		OAuthService serv = OAuthUtils.getOAuthService();

		if (reqToken == null) {

			reqToken = serv.getRequestToken();
			String authURL = serv.getAuthorizationUrl(reqToken);
			session.setAttribute("reqToken", reqToken);

			return authURL;
		} else {
			String verifier = request.getParameter("oauth_verifier");

			Verifier v = null;
			if (verifier == null) {
				session.removeAttribute("reqToken");
				return "login.do";
			}
			
			v = new Verifier(verifier);

			Token accessToken = serv.getAccessToken(reqToken, v);
			session.setAttribute("accessToken", accessToken);
			return "display.do";
		}
	}
}
