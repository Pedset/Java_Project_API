//arbie test commentar
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
 * Servlet implementation class Servlet2
 */
@WebServlet("/BusStops")
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		request.getRequestDispatcher("index.jsp").include(request, response);
		if (request.getParameter("cityName").equals("")) {
			
			request.getRequestDispatcher("ErrorPage.jsp").include(request, response);
		}
		else {
			try {
		URL api_url = new URL("http://www.labs.skanetrafiken.se/v2.2/querystation.asp?inpPointfr=" + request.getParameter("cityName").replaceAll("\\s",""));
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
			
			//System.out.println(inputLine);
			// Save the temp line into the full response
			ApiResponse += inputLine;
		}
		in.close();
		//System.out.println(ApiResponse);
		
		//Call a method to make a XMLdoc out of the full response
		Document doc = convertStringToXMLDocument(ApiResponse);
		
		doc.getDocumentElement().normalize();
		// check that the XML response is OK by getting the Root element 
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		// Create a Node list that gets everything in and under the "clouds" tag  
		
		
		NodeList nList = doc.getElementsByTagName("StartPoints");
		// loop through the content of the tag
		

		out.print("<p>Bus stop</p>");
		out.print("<form action=\"test\">");
		out.print("<select name=\"busStopL\">");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			// Save a node of the current list id 
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				NodeList nodelist = eElement.getElementsByTagName("Point");
			for (int x =0 ; x < nodelist.getLength(); x++) {
				Node node1= nodelist.item(x);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement1 = (Element) node1;
				NodeList nL3 = eElement1.getElementsByTagName("Name");
				NodeList nL4 = eElement1.getElementsByTagName("Id");
				for (int y=0 ; y < nL3.getLength(); y++) {
					Node node2= nL3.item(y);
					Node node3= nL4.item(y);
					/*
					ï¿½ = Ã¥, ï¿½ = Ã¤, ï¿½ = Ã¶ , ï¿½ = ï¿½? eller Ã– , ï¿½ = Ã„ , ï¿½ = Ã… , ï¿½=Ã©
					
					*/
					
					
					String tempString = node2.getTextContent();
					
						
					
						out.print("<option>" + encoding(tempString) + " (ID:" + node3.getTextContent() + ")" + "</option>");
					//	out.print("<option>" + tempString + " (ID:" + node3.getTextContent() + ")" + "</option>");
						
										}
					
				}
				}
			}
			}
		
		out.print("<br><input type=\"submit\" value=\"Search for bus\">");
		out.print("</form>");

		}
		
		catch (Exception e) {
			out.print(e);
		}
		
		out.flush();	
	
		}
		
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

