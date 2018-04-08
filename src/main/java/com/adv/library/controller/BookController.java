package com.adv.library.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adv.library.exception.RecordNotFoundException;
import com.adv.library.exception.SampleDataAccessException;
import com.adv.library.model.Book;
import com.adv.library.repo.BookRepository;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Resource
	private BookRepository bookRepository;

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
    
    @RequestMapping("/getAll")
    @ResponseBody
    public List<Book> getAllBooks() {
    	
    	log.debug("getAllBooks called");
    	
    	List<Book> books = bookRepository.findAll();
    	    
    	log.info("Number of books available:" + books.size());
 
    	return books;
    }
    
    @RequestMapping(value="/getBook/id/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public String getBook( @PathVariable("bookId") long bookId) {
    	
    	log.debug("/getBook/id/{bookId} called, with bookId:{}", bookId);
    
    	String returnMessage = null;
    	Book book = null;
    	
		try {
			book = bookRepository.findBook(bookId);
			returnMessage = "BookId:" + bookId + ", BookName:" +  book.getBookName();
			log.info(returnMessage);
		} catch (RecordNotFoundException e) {
			log.error(e.getMessage());
			returnMessage = "Book with bookId:" + bookId + " not found";
		}
    
        return returnMessage;
    }
    
    @RequestMapping(value="/getBook/name/{bookName}", method = RequestMethod.GET)
    @ResponseBody
    public String getBook( @PathVariable("bookName") String bookName) {
    	
    	String returnMessage = null;
    	StringBuilder bookNames = new StringBuilder();
    	
    	log.debug("/getBook/name/{bookName}, with bookName:{}", bookName);
    	
    	List<Book> books = bookRepository.findBooks(bookName);
    	
    	books.forEach(b -> bookNames.append(",").append(b.getBookName()));
    	
    	returnMessage = String.format("Number of matches found for bookName:%s are %d.",  bookName, books.size());
    	if (bookNames.length() > 0) {
    		bookNames.deleteCharAt(0);
    		returnMessage = returnMessage + "The bookNames are:" + bookNames;
    	}
    	
    	log.info(returnMessage);
    	
        return returnMessage;
    }
    
    @RequestMapping(value="/addBook", method = RequestMethod.GET)
    @ResponseBody
    public String addBook( @RequestParam("bookName") String bookName, @RequestParam("authorName") String authorName,
    		@RequestParam("category") String category,@RequestParam("price") double price, @RequestParam("quantity") long quantity) {
    	
    	String returnMessage;
    	boolean result = false;
    	
    	log.debug("/addBook called with bookName:{}, authorName:{}, category:{}, price:{}, quantity:{}", 
    			bookName, authorName, category, price, quantity);
    	
    	Book book = new Book(bookName, authorName, category, price, quantity);
    	
    	try {
    		result = bookRepository.saveBook(book);
    		if (result) {
    			returnMessage = String.format("Book:%s successfully added", bookName);
    		} else {
    			returnMessage = String.format("Book:%s addition failed", bookName);
    		}	
    	} catch(SampleDataAccessException e) {
    		returnMessage = String.format("Book:%s addition failed", bookName);
    		log.error(returnMessage,e);
    	}
    	
    	log.info(returnMessage);
    	
        return returnMessage;
    }

}