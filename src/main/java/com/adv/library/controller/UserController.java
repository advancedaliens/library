package com.adv.library.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adv.library.model.User;
import com.adv.library.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserRepository userRepository;
	
	private static final Logger log = LoggerFactory.getLogger(UserRepository.class);    
    
    @RequestMapping(value="/getUser/id/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser( @PathVariable("userId") long userId) {
    	
    	log.debug("/getUser/id/{userId} called with userId:{}", userId);
    
    	User user = userRepository.findUser(userId);
    	
    	log.info("UserName for userId:{} is {}", userId, user.getUserName());
    	
        return "UserId:" + userId + ", UserName:" +  user.getUserName();
    }
    
    @RequestMapping(value="/getUser/fname/{firstName}/lname/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser( @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    	
    	log.debug("/getUser/fname/{firstName}/lname/{lastName} called with firstName:{}, lastName{}", firstName, lastName);
    	
    	List<User> users = userRepository.findUsers(firstName, lastName);
    	
    	log.info("Number of matched found for user with firstName:{}, lastName{} are {}", firstName, lastName, users.size());
    	
        return "UserName:" + firstName + " " + lastName + ", number of matches found:" + users.size();
    }
    
    @RequestMapping(value="/addUser", method = RequestMethod.POST,  consumes = "application/json")
    @ResponseBody
    public String addUser( @RequestBody User user) {
    	
    	String returnMessage;
    	String userJsonString = null;
    	if (log.isDebugEnabled()) {
        	ObjectMapper mapper = new ObjectMapper();
        	try {
        		userJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    		} catch (JsonProcessingException e) {
    		}
    	}
    	
    	log.debug("/addUser called with user:{}", userJsonString);
    	    	
    	boolean result = userRepository.saveUser(user);
    	
    	if (result) {
    		log.info("User:{} {} added sucessfully", user.getFirstName(), user.getLastName());
    		returnMessage = "User successfully added";
    	} else {
    		log.info("User:{} {} addition failed", user.getFirstName(), user.getLastName());
    		returnMessage = "User not added";
    	}
    	
        return returnMessage;
    }

}