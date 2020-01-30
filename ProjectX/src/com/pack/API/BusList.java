package com.pack.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class BusList extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Setting content type to text/html and create a PrintWriter named "out"
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		
		try {
			// first we need to get the ID from the parameter "busStopL" so we can use it in our API URL
			String tempString = request.getParameter("busStopL");
			StringBuffer sB = new StringBuffer(tempString);
			String ftemp = "";
			for (int x1 = 0; x1 < tempString.length(); x1++) {
				if (tempString.charAt(x1) == 'I') {
					if (tempString.charAt(x1 + 1) == 'D') {
						ftemp = sB.substring(x1 + 3, tempString.length() - 1);
					}
				}
			}
			
			
				Cookie cookie = new Cookie("cityId", ftemp);
				response.addCookie(cookie);
				
				
			out.print("<head>");
			out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" async />");
			out.print("</head>");
			out.print("<body>");
			out.print("<div>");
			
			
			APIcall getbusList = new APIcall();
			
			out.print(getbusList.getBusList(ftemp));
			
			
			
			
				out.print("</div>");
				out.print("</body>");
		}
		catch (Exception e) {
			out.print(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
