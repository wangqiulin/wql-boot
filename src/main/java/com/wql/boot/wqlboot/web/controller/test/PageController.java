package com.wql.boot.wqlboot.web.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/404")
	public String toPage404(){
		return "404";
	}
	
	
}
