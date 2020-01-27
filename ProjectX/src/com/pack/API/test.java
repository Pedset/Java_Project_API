package com.pack.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
public class test extends HttpServlet {

	private static NodeList LineTypeNameLists;

	private static NodeList NoLists;

	private static NodeList TowardsLists;

	private static NodeList JourneyDateTimeLists;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public test() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
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
			out.print(ftemp);

			URL api_url = new URL("http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=" + ftemp);
			HttpURLConnection linec = (HttpURLConnection) api_url.openConnection();
			linec.setDoInput(true);
			linec.setDoOutput(true);
			linec.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(linec.getInputStream()));
			String inputLine;

			// a String to save the full response to use later
			String ApiResponse = "";
			// loop through the whole response
			while ((inputLine = in.readLine()) != null) {

				// System.out.println(inputLine);
				// Save the temp line into the full response
				ApiResponse += inputLine;
			}

			in.close();

			// Call a method to make a XMLdoc out of the full response
			Document doc = convertStringToXMLDocument(ApiResponse);

			doc.getDocumentElement().normalize();
			
			test.LineTypeNameLists = doc.getElementsByTagName("LineTypeName");

			test.NoLists = doc.getElementsByTagName("Name");

			test.TowardsLists = doc.getElementsByTagName("Towards");

			test.JourneyDateTimeLists = doc.getElementsByTagName("JourneyDateTime");
			
			out.print("<ul>");
			for (int index = 0; index < LineTypeNameLists.getLength(); index++) {
				StringBuffer theString = new StringBuffer(test.JourneyDateTimeLists.item(index).getTextContent());
				
				out.print("<li>" + encoding(test.LineTypeNameLists.item(index).getTextContent()) + " " + encoding(test.NoLists.item(index).getTextContent())
				+ ", Mot " + encoding(test.TowardsLists.item(index).getTextContent()) + "<br> Avgångstid: "
				+ encoding(theString.substring(11, theString.length()-3)) + "</li><br><br>");
			}
			out.print("</ul>");
			
				

			

		} catch (Exception e) {
			out.print(e);
		}

		out.print("last");

	}

	public String encoding (String tempString) {
		for (int ii = 0 ; ii < tempString.length() ; ii++) {
			if (tempString.charAt(ii)=='Ã') {
				StringBuffer s1 = new StringBuffer(tempString);
				switch (tempString.charAt(ii+1)) {
					case '¶':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("¶")+1, "ö");
						tempString = s1.toString();
						break;
					}
					case '¥':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("¥")+1, "å");
						tempString = s1.toString();
						break;
					}
					case '¤':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("¤")+1, "ä");
						tempString = s1.toString();
						break;
					}
					case '?':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("?")+1, "Ö");
						tempString = s1.toString();
						break;
					}
					case '–':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("–")+1, "Ö");
						tempString = s1.toString();
						break;
					}
					case '„':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("„")+1, "Ä");
						tempString = s1.toString();
						break;
					}
					case '…':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("…")+1, "Å");
						tempString = s1.toString();
						break;
					}
					case '©':{
						s1.replace(tempString.indexOf("Ã"), tempString.indexOf("©")+1, "é");
						tempString = s1.toString();
						break;
					}
				}
			}
		} 
		return tempString;
	}

	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
		//
		// factory.setNamespaceAware(true);
		// API to obtain DOM Document instance
		DocumentBuilder builder = null;

		try {
			// Create DocumentBuilder with default configuration
			builder = factory1.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
