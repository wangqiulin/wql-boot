package com.wql.boot.wqlboot.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wangqiulin
 * @date 2018年5月12日
 */
@Controller
public class TestErrorController {

	@RequestMapping("/test/error")
	private String testMvc() {
//		int i = 1/0;
		return "testMvc";
	}
	
}
