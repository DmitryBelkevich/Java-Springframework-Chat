package com.hard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/long_polling")
public class LongPollingController {
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String main() {
		return "long_polling/main";
	}
}