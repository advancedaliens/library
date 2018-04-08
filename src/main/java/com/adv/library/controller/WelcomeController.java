package com.adv.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {
	
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
	
	@RequestMapping("/")
    public String index() {   	
		
		log.debug("Entered index method in welcome controller");
		
    	return "index";
    }
}
