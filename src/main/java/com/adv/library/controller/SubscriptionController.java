package com.adv.library.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adv.library.model.SubscriptionDetail;
import com.adv.library.repo.SubscribtionRepository;

@Controller
@RequestMapping("/subscribe")
public class SubscriptionController {
	
	@Resource
	private SubscribtionRepository subscribtionRepository;
    
	private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);  
    
    @RequestMapping(value="/borrowBook/bookId/{bookId}/userId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String borrowBook(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId) {
    	
    	String returnMessage = null;
    	
    	log.debug("/borrowBook/bookId/{bookId}/userId/{userId} called with bookId:{}, userId:{}", bookId, userId);
    	
    	boolean result = subscribtionRepository.borrowBook(bookId, userId);
    
    	if (result) {
    		returnMessage = "Borrow Book successful for bookId:" + bookId + ", userId:" + userId;
    	} else {
    		returnMessage = "Borrow Book failed for bookId:" + bookId + ", userId:" + userId;
    	}
    	
    	log.info(returnMessage);
    	
        return returnMessage;
    }
    
    @RequestMapping(value="/borrowBook/bookName/{bookName}/firstName/{firstName}/lastName/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public String borrowBook(@PathVariable("bookName") String bookName, @PathVariable("firstName") String firstName,
    		@PathVariable("lastName") String lastName) {
    	
    	log.debug("/borrowBook/bookName/{bookName}/firstName/{firstName}/lastName/{lastName} called with "
    			+ "bookName:{}, firstName:{}, lastName:{}", bookName, firstName, lastName);
    	
        return "UserId:";
    }
    
    @RequestMapping(value="/returnBook/bookId/{bookId}/userId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String returnBook(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId) {
    	
    	String returnMessage = null;
    	log.debug("/returnBook/bookId/{bookId}/userId/{userId} called with bookId:{}, userId:{}", bookId, userId);
    
    	boolean result = subscribtionRepository.returnBook(bookId, userId);
    	
    	if (result) {
    		returnMessage = "returnBook sucessful for bookId:" + bookId + ", userId:" + userId;
    	} else {
    		returnMessage = "returnBook failed for bookId:" + bookId + ", userId:" + userId;
    	}
    	
    	log.info(returnMessage);
    	
        return returnMessage;
    }
    
    @RequestMapping(value="/returnBook/bookName/{bookName}/firstName/{firstName}/lastName/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public String returnBook(@PathVariable("bookName") String bookName, @PathVariable("firstName") String firstName,
    		@PathVariable("lastName") String lastName) {
    	
    	log.debug("/returnBook/bookName/{bookName}/firstName/{firstName}/lastName/{lastName} called with "
    			+ "bookName:{}, firstName:{}, lastName:{}", bookName, firstName, lastName);
    	
        return "UserId:";
    }
    
    @RequestMapping(value="/getAllBorrowedBooks/userId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubscriptionDetail> getAllBorrowedBooks(@PathVariable("userId") long userId) {
    	
    	log.debug("/getAllBorrowedBooks/userId/{userId} called with userId:{}", userId);
    	
    	List<SubscriptionDetail> subsciptions = subscribtionRepository.getAllSubscriptionsByUserId(userId);
    	
    	log.info("Number of subsciptions for userId:{} are {}", userId, subsciptions.size());
    	
        return subsciptions;
    }
    
    @RequestMapping(value="/getAllBorrowedBooks/firstName/{firstName}/lastName/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubscriptionDetail> getAllBorrowedBooks(@PathVariable("firstName") String firstName,
    		@PathVariable("lastName") String lastName) {
    	
    	log.debug("/getAllBorrowedBooks/firstName/{firstName}/lastName/{lastName} called with firstName:{}, lastName:{}", firstName, lastName);
    	
    	List<SubscriptionDetail> subsciptions = subscribtionRepository.getAllSubscriptionsByUserName(firstName, lastName);
    	
    	log.info("Number of subsciptions for user firstName:{} lastName:{} are {}", firstName, lastName, subsciptions.size());
    	
        return subsciptions;
    }
    
    @RequestMapping(value="/getAllBorrowedBooks/bookId/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubscriptionDetail> getAllBorrowedBooksByBookId(@PathVariable("bookId") long bookId) {
    	
    	log.debug("/getAllBorrowedBooks/bookId/{bookId} called with bookId:{}", bookId);
    	
    	List<SubscriptionDetail> subsciptions = subscribtionRepository.getAllSubscriptionsByBookId(bookId);
    	
    	log.info("Number of subsciptions for bookId:{} are {}", bookId, subsciptions.size());
    	
        return subsciptions;
    }
    
    @RequestMapping(value="/getAllBorrowedBooks/bookName/{bookName}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubscriptionDetail> getAllBorrowedBooksByBookName(@PathVariable("bookName") String bookName) {
    	
    	log.debug("/getAllBorrowedBooks/bookName/{bookName} called with bookName:{}", bookName);
    	
    	List<SubscriptionDetail> subsciptions = subscribtionRepository.getAllSubscriptionsByBookName(bookName);
    	
    	log.info("Number of subsciptions for bookName:{} are {}", bookName, subsciptions.size());
    	
        return subsciptions;
    }

}