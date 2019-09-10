package com.prashanth.restcontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.model.Address;
import com.prashanth.model.MangementInfoDetails;
import com.prashanth.service.LoginServiceIntf;
import com.prashanth.service.ManagementInfoService;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	@Autowired
	private ManagementInfoService managementInfoService;
	@Autowired
	private LoginServiceIntf loginServiceIntf;

	@RequestMapping(value = "/test",produces = "application/json")
	public @ResponseBody String test() {
		JSONObject object = new JSONObject();
		object.put("user", "test");
		return object.toJSONString();
	}
	@RequestMapping(value = "/management-info", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody String addManagementInfo(@RequestBody String management) {
		JSONObject response = new JSONObject();
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
					if (object.get("ownerId") != null)
						mid.setOwnerId(Integer.parseInt(object.get("ownerId").toString()));
					Set<Address> addressSet = new HashSet<Address>();
					if (object.get("address") != null) {
						JSONArray addresses = (JSONArray) parser.parse(object.get("address").toString());
						if (addresses.size() > 0) {
							for (int j = 0; j < addresses.size(); j++) {
								JSONObject addr = (JSONObject) addresses.get(j);
								Address ad1 = new Address();
								if (addr.get("hno") != null)
									ad1.sethNo(addr.get("hno").toString());
								if (addr.get("street") != null)
									ad1.setStreet(addr.get("street").toString());
								if (addr.get("town") != null)
									ad1.setTown(addr.get("town").toString());
								if (addr.get("district") != null)
									ad1.setDistrict(addr.get("district").toString());
								if (addr.get("state") != null)
									ad1.setState(addr.get("state").toString());
								if (addr.get("pincode") != null)
									ad1.setPinCode(Integer.parseInt(addr.get("pincode").toString()));

								ad1.setMangementInfoDetails(mid);
								addressSet.add(ad1);

							}
							mid.setAddressSet(addressSet);
							String id = managementInfoService.saveManagementInfoDetails(mid);
							response.put("result", "success");
							response.put("message", "");
						}
					}

				}
			}

		} catch (Exception e) {
			response.put("result", "failed");
			response.put("message", e.getMessage());
			e.printStackTrace();
		}
		return response.toJSONString();
	}
	@RequestMapping(value = "/validate", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody String loginValidation(@RequestBody String obj) {
		JSONObject result =new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObj=(JSONObject)parser.parse(obj);
			if(jsonObj!=null) {
				result= loginServiceIntf.validateLogin(jsonObj);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}

	public ManagementInfoService getManagementInfoService() {
		return managementInfoService;
	}

	public void setManagementInfoService(ManagementInfoService managementInfoService) {
		this.managementInfoService = managementInfoService;
	}

}
