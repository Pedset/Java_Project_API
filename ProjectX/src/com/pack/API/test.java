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

import org.dom4j.io.SAXReader;
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
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		try { 
			String temp1 = request.getParameter("busStopL");
			StringBuffer sB = new StringBuffer(temp1);
			String ftemp = "";
			for (int x1 = 0 ; x1 < temp1.length() ; x1++){
				if (temp1.charAt(x1)=='I') {
					if (temp1.charAt(x1+1)=='D') {
					ftemp = sB.substring(x1+3, temp1.length()-1);
					}
				}
			}
			out.print(ftemp);
			System.out.printf(ftemp);
			
		URL api_url1 = new URL("http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=" + ftemp);
		HttpURLConnection linec1 = (HttpURLConnection) api_url1.openConnection();
		linec1.setDoInput(true);
		linec1.setDoOutput(true);
		linec1.setRequestMethod("GET");
		
		BufferedReader in1 = new BufferedReader(new InputStreamReader(linec1.getInputStream()));
		String inputLine1;

		// a String to save the full response to use later
		String ApiResponse1 = "";
		// loop through the whole response
		while ((inputLine1 = in1.readLine()) != null) {
			
			//System.out.println(inputLine);
			// Save the temp line into the full response
			ApiResponse1 += inputLine1;
		}
		in1.close();
		//System.out.println(ApiResponse);
		
		
		//Call a method to make a XMLdoc out of the full response
		Document doc1 = convertStringToXMLDocument(ApiResponse1);
		
		doc1.getDocumentElement().normalize();
		// check that the XML response is OK by getting the Root element 
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		// Create a Node list that gets everything in and under the "clouds" tag  
		
	//	SAXReader reader = new SAXReader();
		//Document document = (Document) reader.read("http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=80000");
		
		
		
		//out.print(" root element = " + doc1.getDocumentElement().getNodeName());
		
		NodeList nList11 = doc1.getElementsByTagName("Lines");
		out.print(nList11.getLength());
		out.print("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<meta http-equiv=\"Content-type\""+
				"content=\"text/html; charset=UTF-8\">\r\n" + 
				"<title>Insert title here</title>\r\n" + 
				"</head>"
				+ "");
			out.print(" x after    ");
			out.print(nList11.getLength());
			for (int temp = 0; temp < nList11.getLength() ; temp++) {

			// Save a node of the current list id 
				out.print("-check1-");
			Node node = nList11.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement55 = (Element) node;
				out.print("-check2-");
				
				
				NodeList nodelist = eElement55.getElementsByTagName("Line");
				
				out.print(" x ");
				out.print(nodelist.getLength());
				out.print(" x ");
				for (int temp2 = 0; temp2 <= nodelist.getLength(); temp2++) {
					out.print("-check3-");
					Node node32 =nodelist.item(temp2);
					if (node32.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement1 = (Element) node32;
						out.print("-check4-");
						NodeList LineTypeNameList1 = eElement1.getElementsByTagName("Line");
						out.print(LineTypeNameList1.getLength());
						NodeList LineTypeNameList = eElement55.getElementsByTagName("LineTypeName");

						NodeList NoList = eElement55.getElementsByTagName("Name");

						NodeList TowardsList = eElement55.getElementsByTagName("Towards");

						NodeList JourneyDateTimeList = eElement55.getElementsByTagName("JourneyDateTime");
						
						
			for (int y=0 ; y < TowardsList.getLength(); y++) {
					

					Node node1= LineTypeNameList.item(y);
					Node node2= NoList.item(y);
					Node node3= TowardsList.item(y);
					Node node4= JourneyDateTimeList.item(y);
					
					out.print("<p>" + encoding(node1.getTextContent()) + ": " + node2.getTextContent() + " mot: "  + encoding(node3.getTextContent()) + " tid : " + encoding(node4.getTextContent()) + "</p><br>");
					
							}
						out.print("hello");
						
						}
					}
			}
			
			}
				
		}
		catch(Exception e) {
			out.print(e);
		}
		
		
		
		
		
		
		
		out.print("last");
		
		
		
		
		
		
		
		
	}
	public String encoding (String tempString) {
		for (int ii = 0 ; ii < tempString.length() ; ii++) {
			if (tempString.charAt(ii)=='�') {
				StringBuffer s1 = new StringBuffer(tempString);
				switch (tempString.charAt(ii+1)) {
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '?':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("?")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
						tempString = s1.toString();
						break;
					}
					case '�':{
						s1.replace(tempString.indexOf("�"), tempString.indexOf("�")+1, "�");
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
		//factory.setNamespaceAware(true);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
