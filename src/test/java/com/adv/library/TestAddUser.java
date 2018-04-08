package com.adv.library;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;

import com.adv.library.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestAddUser {

	@Value("${add.user.base.url}")
	private static String addUserURL;
	
	public static void main(String args[]) {
		
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
			e.printStackTrace();
		}
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
