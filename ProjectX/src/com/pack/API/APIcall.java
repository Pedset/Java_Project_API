package com.pack.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class APIcall {
	
	private static NodeList LineTypeNameLists;
	private static NodeList NoLists;
	private static NodeList TowardsLists;
	private static NodeList JourneyDateTimeLists;
	
	
	private String txt; 
	
	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
		
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
	
	public String getBusList(String id) {
		String htmlTxt = "";
		
		try {
		
			
		
		URL api_url = new URL("http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=" + id);
		HttpURLConnection linec = (HttpURLConnection) api_url.openConnection();
		linec.setDoInput(true);
		linec.setDoOutput(true);
		linec.setRequestMethod("GET");
		
		// Reads the file received from the api request 
		BufferedReader in = new BufferedReader(new InputStreamReader(linec.getInputStream()));
		String inputLine;

		// An empty String to save the full response to use later
		String ApiResponse = "";
		// loop through the whole response reading every line and as long as its not equal to null we append it to our "ApiResponse" variable.
		while ((inputLine = in.readLine()) != null) {
			ApiResponse += inputLine;
		}
		in.close();
		
		// Call a method to make a XMLdoc out of the full response
		Document doc = convertStringToXMLDocument(ApiResponse);
		doc.getDocumentElement().normalize();
		
		// Extracting all the data with the tag names LineTypeName, Name ...etc and store them all in 4 different NodeList that was created globally 
		APIcall.LineTypeNameLists = doc.getElementsByTagName("LineTypeName");
		APIcall.NoLists = doc.getElementsByTagName("Name");
		APIcall.TowardsLists = doc.getElementsByTagName("Towards");
		APIcall.JourneyDateTimeLists = doc.getElementsByTagName("JourneyDateTime");
					
		htmlTxt += "<ul>";
		
			//since the length of our list for these 4 NodeLists are same we just used LineTypeNameLists.getLength()
			//We want to loop thru them and take each item out of all 4 NodeLists and use them to print out our html list
			//We had NodeList from the start, the code ".item(index)" returns a single Node item that is in our NodeList at a specific index
			//".getTextContent()" returns content of that node in a String format which we send as a parameter to the encoding method (to fix the characters)
			
			for (int index = 0; index < LineTypeNameLists.getLength(); index++) {
				StringBuffer theString = new StringBuffer(APIcall.JourneyDateTimeLists.item(index).getTextContent());
						
				String departureTime = encoding(theString.substring(11, theString.length()-3));
				
				// Creates variables for current time and current time plus ten minutes, to only show busses for the next ten minutes
				DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime currentTime = LocalTime.parse(departureTime, parser);
				LocalTime endTime = LocalTime.now().plusMinutes(10);
				
				if (currentTime.isBefore(endTime)) {						
					// Removes trains and airport shuttles from the results and fixes some issues with how SkåneExpressen was printed
					if (encoding(APIcall.LineTypeNameLists.item(index).getTextContent()).matches("SkåneExpressen")) {
						htmlTxt +="<li>" + encoding(APIcall.NoLists.item(index).getTextContent())
							+ " mot " + encoding(APIcall.TowardsLists.item(index).getTextContent().replaceAll("SkÃ¥neExpressen ", "")) + "<br> Avgångstid: "
							+ departureTime + "</li><br><br>";
					} else if (!encoding(APIcall.LineTypeNameLists.item(index).getTextContent()).matches("Pågatågen|Öresundståg|Flygbuss")) {
							htmlTxt += "<li>" + encoding(APIcall.LineTypeNameLists.item(index).getTextContent()) + " " + encoding(APIcall.NoLists.item(index).getTextContent())
							+ " mot " + encoding(APIcall.TowardsLists.item(index).getTextContent()) + "<br> Avgångstid: "
							+ departureTime + "</li><br><br>";
					}
				}
			}
			htmlTxt +="</ul>";
	}
			catch (Exception e) {
				
			}
				
				
				return htmlTxt;
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
	}
	


