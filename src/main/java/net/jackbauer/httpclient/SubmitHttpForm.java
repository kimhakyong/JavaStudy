package net.jackbauer.httpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

public class SubmitHttpForm {
	private static String url = "http://localhost:8080/web/httpclient/param.jsp";
	private static String filePath = "D:\\\\Dev\\\\Eclipse\\\\2019-03\\\\workspace\\\\study\\\\src\\\\main\\\\java\\\\net\\\\jackbauer\\\\httpclient\\\\donepage.html";

	public static void main(String[] args) {

		// Instantiate an HttpClient
		HttpClient client = new HttpClient();

		// Instantiate a GET HTTP method
		HttpMethod method = new GetMethod(url);

		// Define name-value pairs to set into the QueryString
		NameValuePair nvp1 = new NameValuePair("firstName", "fname");
		NameValuePair nvp2 = new NameValuePair("lastName", "lname");
		NameValuePair nvp3 = new NameValuePair("email", "email@email.com");

		method.setQueryString(new NameValuePair[]{nvp1, nvp2, nvp3});

		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			int statusCode = client.executeMethod(method);

			System.out.println("QueryString >>> " + method.getQueryString());
			System.out.println("Status Text >>>" + HttpStatus.getStatusText(statusCode));

			// Get data as a String
			System.out.println(method.getResponseBodyAsString());

			// OR as a byte array
			byte[] res = method.getResponseBody();

			// write to file
			fos.write(res);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// release connection
			method.releaseConnection();
		}
	}
}