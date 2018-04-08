package com.adv.library.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adv.library.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/testdata")
public class DataStubController {
	
	private static final Logger log = LoggerFactory.getLogger(DataStubController.class);
	
	@Value("${add.book.base.url}")
	private String addBookBaseURL;
	
	@Value("${add.user.base.url}")
	private String addUserURL;
	
	@RequestMapping("/addBooks")
	@ResponseBody
    public String addBooks() {   	
		
		String returnMessage = null;
		
		log.debug("Entered addBooks method in DataStubController");
		
		Random rand = new Random(); 
		int randomCategoryIndex;
		int randomPriceIndex;
		int randomQuantityIndex;
		String[] categories = {"Drama", "Comedy", "Action", "Biography", "Thriller", "Horror", "Nature", "Historic"};
		double[] prices = {12.5, 28, 15.25, 9.99, 5, 59.99, 100, 32, 91.5, 40, 10};
		int[] quantities = {10, 35, 50, 100, 25, 5, 1, 3, 15, 8, 60, 20, 6, 30};
		String bookName = "Test BookName";
		String authorName = "Test AuthorName";
	    URL url;
	    
	    try {
	    	for (int i=0; i<1000; i++) {
				
				randomCategoryIndex = rand.nextInt(categories.length);
				randomPriceIndex = rand.nextInt(prices.length); 
				randomQuantityIndex = rand.nextInt(quantities.length); 
				
				StringBuilder addBookURLParams = new StringBuilder();
				addBookURLParams.append("?").append("bookName=").append(encodeValue(bookName+i)).append("&");
				addBookURLParams.append("authorName=").append(encodeValue(authorName+i)).append("&");
				addBookURLParams.append("category=").append(encodeValue(categories[randomCategoryIndex])).append("&");
				addBookURLParams.append("price=").append(prices[randomPriceIndex]).append("&");
				addBookURLParams.append("quantity=").append(quantities[randomQuantityIndex]);
				
				String addBookURL = addBookBaseURL + addBookURLParams;
				
				url = new URL(addBookURL);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.getInputStream();
				
				System.out.println("Added Book number:" + i + " with url:" + addBookURL);
	    	}
	    	
	    } catch (Exception e) {
	    	returnMessage = "Error occurred while adding test data for books";
			e.printStackTrace();
		}
	    
	    returnMessage = "Successfully added test data for books";
	    
	    return returnMessage;
    }
	
	@RequestMapping("/addUsers")
	@ResponseBody
    public String addUsers() {   	
		
		String returnMessage = null;
		
		log.debug("Entered addUsers method in DataStubController");
		
		String firstName = "FirstName";
		String lastName = "LastName";
		String address1 = "Address1";
		String address2 = "Address2";
		String city = "City";
		String postalCode = "PostalCode";
		String state = "MA";
		String country = "US";
		
		try {
			
			URL url = new URL(addUserURL);
	
			for (int i=0; i<10; i++) {
				
				User user = new User();
				user.setFirstName(firstName + i);
				user.setLastName(lastName + i);
				user.setAddress1(address1 + i);
				user.setAddress2(address2 + i);
				user.setCity(city + (i%5));
				user.setState(state);
				user.setPostalCode(postalCode + (i%5));
				user.setCountry(country);
				
				String userJson = convertToJson(user);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setConnectTimeout(5000);
	            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setRequestMethod("POST");

	            OutputStream os = conn.getOutputStream();
	            os.write(userJson.getBytes("UTF-8"));
	            os.close();
	            
	            conn.getInputStream();
	            
	            System.out.println("Added user:" + userJson);
			}
			
		} catch (Exception e) {
			returnMessage = "Error occurred while adding test data for users";
			e.printStackTrace();
		}
		
		returnMessage = "Successfully added test data for users";
		
		return returnMessage;
	}
	
	private static String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}
	
	private static String convertToJson(User user) {
		String userJsonString = null;
    
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		userJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return userJsonString;
	}	
}
