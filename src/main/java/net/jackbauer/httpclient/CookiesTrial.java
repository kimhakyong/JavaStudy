package net.jackbauer.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

public class CookiesTrial {

	private static String url = "http://127.0.0.1:8080/web/httpclient/cookie.jsp";

	public static void main(String[] args) throws Exception {

		// A new cookie for the domain 127.0.0.1
		// Cookie Name= ABCD Value=00000 Path=/ MaxAge=-1 Secure=False
		Cookie mycookie = new Cookie("127.0.0.1", "ABCD", "00000", "/", -1, false); 

		// Create a new HttpState container
		HttpState initialState = new HttpState();
		initialState.addCookie(mycookie);

		// Set to COMPATIBILITY for it to work in as many cases as possible
		initialState.setCookiePolicy(CookiePolicy.COMPATIBILITY);
		// create new client
		HttpClient httpclient = new HttpClient();
		// set the HttpState for the client
		httpclient.setState(initialState);

		GetMethod getMethod = new GetMethod(url);
		// Execute a GET method
		int result = httpclient.executeMethod(getMethod);

		System.out.println("result >>> " + result);
		System.out.println("statusLine >>> " + getMethod.getStatusLine());

		// Get cookies stored in the HttpState for this instance of HttpClient
		for (Cookie cookie : httpclient.getState().getCookies()) {
			System.out.println("nCookieName=" + cookie.getName());
			System.out.println("Value=" + cookie.getValue());
			System.out.println("Domain=" + cookie.getDomain());
		}
		
		System.out.println(getMethod.getResponseBodyAsString());

		getMethod.releaseConnection();
	}
}