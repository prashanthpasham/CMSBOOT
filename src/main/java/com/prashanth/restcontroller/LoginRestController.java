package com.prashanth.restcontroller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.model.MangementInfoDetails;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	@RequestMapping(name = "/managementInfo", consumes = "application/json", method = RequestMethod.POST)
	public void addManagementInfo(@RequestBody String management) {
		try {
			if (management != null) {
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(management);
				if (object != null) {
					MangementInfoDetails mid = new MangementInfoDetails();
					if (object.get("name") != null) {
						mid.setName(object.get("name").toString());
					}
					if (object.get("description") != null) {
						mid.setDescription(object.get("description").toString());
					}
					if (object.get("logo") != null) {
						mid.setLogo(object.get("logo").toString().getBytes());
					}
					if (object.get("address") != null) {
						JSONArray addresses = (JSONArray) parser.parse(object.get("address").toString());
						if (addresses.size() > 0) {
                          
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
