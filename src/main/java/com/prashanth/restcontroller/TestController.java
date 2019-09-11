package com.prashanth.restcontroller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class TestController {
	@RequestMapping(value = "/test",produces = "application/json")
	public @ResponseBody String test() {
		JSONObject object = new JSONObject();
		object.put("user", "test");
		return object.toJSONString();
	}
}
