package edu.cmu.andrew.vravuri.utils;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.oauth.OAuthService;

public class OAuthUtils {

	private static String apiKey = "ku7ctmtnx52r";
	private static String secretKey = "F2IvYSqWa88VXd48";
	// private String state = "ajd3GDLSfA70b26N7P4sA530fdsafSA";
	private static String scope = "r_network";
	private static String redirectURL = "http://localhost:8080/authenticate.do";
	//private static String redirectURL = "http://friendskillfinder.appspot.com/authenticate.do";

	public static OAuthService getOAuthService() {
		return new ServiceBuilder().provider(LinkedInApi.class).apiKey(apiKey)
				.apiSecret(secretKey).callback(redirectURL).scope(scope)
				.build();
	}

}
