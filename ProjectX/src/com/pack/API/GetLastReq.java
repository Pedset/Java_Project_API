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
 * Servlet implementation class GetLastReq
 */
@WebServlet("/GetLastReq")
public class GetLastReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static NodeList LineTypeNameLists;
	private static NodeList NoLists;
	private static NodeList TowardsLists;
	private static NodeList JourneyDateTimeLists;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLastReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		
		try {
			
			Cookie ck[] = request.getCookies();
			String idtemp = "";
			for (Cookie cookie : ck) {
				if (cookie.getName().equals("cityId")) {
					idtemp = cookie.getValue();
				}
			}
			out.print("<head>");
			out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" async />");
			out.print("</head>");
			out.print("<body>");
			out.print("<div>");

			APIcall getbusList = new APIcall();
			
			out.print(getbusList.getBusList(idtemp));
			
				out.print("</div>");
				out.print("</body>");
		}
		catch (Exception e) {
			out.print(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
