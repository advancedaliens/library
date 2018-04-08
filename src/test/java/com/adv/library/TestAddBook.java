package com.adv.library;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

public class TestAddBook {

	@Value("${add.book.base.url}")
	private static String addBookBaseURL;
	
	public static void main(String args[]) {
		
		Random rand = new Random(); 
		int randomCategoryIndex;
		int randomPriceIndex;
		int randomQuantityIndex;
		String[] categories = {"Drama", "Comedy", "Action", "Biography", "Thriller", "Horror", "Nature", "Historic"};
		double[] prices = {12.5, 28, 15.25, 9.99, 5, 59.99, 100, 32, 91.5, 40, 10};
		int[] quantities = {10, 35, 50, 100, 25, 5, 1, 3, 15, 8, 60, 20, 6, 30};
		String bookName = "Test BookName";
		String authorName = "Test AuthorName";
		
		//BookController bookController = new BookController();

	    URL url;
	    
	    try {
	    	for (int i=0; i<1000; i++) {
				
				randomCategoryIndex = rand.nextInt(categories.length);
				randomPriceIndex = rand.nextInt(prices.length); 
				randomQuantityIndex = rand.nextInt(quantities.length); 
			
				//bookController.addBook(bookName+i, authorName+i, categories[randomCategoryIndex], prices[randomPriceIndex], quantities[randomQuantityIndex]);
				
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
			e.printStackTrace();
		}
	}
	
	private static String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		//return URLEncoder.encode(value,"UTF-8");
	    
	    //URLEncoder.encode(param1value,"UTF-8")
	}
	
}
