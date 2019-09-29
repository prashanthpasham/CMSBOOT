package com.prashanth.restcontroller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.model.Address;
import com.prashanth.model.MangementInfoDetails;
import com.prashanth.model.MenuItem;
import com.prashanth.model.Role;
import com.prashanth.model.RoleMenuMap;
import com.prashanth.service.LoginServiceIntf;
import com.prashanth.service.ManagementInfoService;
import com.prashanth.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginRestController {
	@Autowired
	private ManagementInfoService managementInfoService;
	@Autowired
	private LoginServiceIntf loginServiceIntf;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	private JSONParser parser = new JSONParser();

	@RequestMapping(value = "/management-info", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json", method = RequestMethod.POST)
	public JSONObject addManagementInfo(@RequestBody String management) {
		JSONObject response = new JSONObject();
		try {
			if (management != null) {
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
		return response;
	}

	@RequestMapping(value = "/menus-user", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public JSONObject findMenusByUserName(@RequestParam("username") String userName) {
		JSONObject result = new JSONObject();
		try {
			// JSONParser parser = new JSONParser();
			// JSONObject jsonObj = (JSONObject) parser.parse(obj);
			if (userName != null) {
				result = loginServiceIntf.menusByUserName(userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PostMapping(value = "/authenticate", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject authenticateUser(@RequestBody String obj) {
		JSONObject result = new JSONObject();
		try {

			JSONObject jsonObj = (JSONObject) parser.parse(obj);
			if (jsonObj != null) {
				try {
					JSONObject result2 = loginServiceIntf.validateLogin(jsonObj);

					if (result2 != null) {
						result = result2;
						UserDetails user = userDetailsService.loadUserByUsername(jsonObj.get("username").toString());
						if (user != null) {
							/*
							 * authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							 * jsonObj.get("username").toString(), jsonObj.get("password").toString()));
							 */
							String token = jwtTokenUtil.generateToken(user);
							result.put("error", "");
							result.put("token", token);
						} else {
							result.put("token", "");
							result.put("error", "UserName not found");
						}

					} else {
						result.put("error", "Invalid Credentials");
						result.put("token", "");
					}

				} catch (UsernameNotFoundException e) {
					result.put("error", "UserName not found");
				}
			}
		} catch (Exception e) {
			result.put("error", "");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/add-role", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JSONObject addRole(@RequestBody String role) {
		JSONObject result = new JSONObject();
		try {
			try {
				JSONObject jsonObj = (JSONObject) parser.parse(role);
				if (jsonObj != null) {
					Role r = new Role();
					if (jsonObj.get("role") != null)
						r.setRole(jsonObj.get("role").toString().trim());
					if (jsonObj.get("description") != null)
						r.setRoleDescription(jsonObj.get("role").toString().trim());
					// if(jsonObj.get("createdDate")!=null)
					r.setCreatedDateTime(new Date());
					if (jsonObj.get("createdUser") != null)
						r.setCreatedUserName(jsonObj.get("createdUser").toString());
					r.setModifiedDateTime(new Date());
					if (jsonObj.get("modifiedUser") != null)
						r.setModifiedUserName(jsonObj.get("modifiedUser").toString());
					if (jsonObj.get("ownerId") != null)
						r.setOwnerId(Integer.parseInt(jsonObj.get("ownerId").toString()));
					JSONArray menus = (JSONArray) jsonObj.get("menus");
					Set<RoleMenuMap> roleMenus = new HashSet<RoleMenuMap>();
					for (int i = 0; i < menus.size(); i++) {
						JSONObject menu = (JSONObject) menus.get(i);
						RoleMenuMap rmm = new RoleMenuMap();
						rmm.setMenuItem(new MenuItem(Integer.parseInt(menu.get("menuid").toString())));
						rmm.setAddOperation(Integer.parseInt(menu.get("add").toString()));
						rmm.setEditOperation(Integer.parseInt(menu.get("edit").toString()));
						rmm.setDeleteOperation(Integer.parseInt(menu.get("delete").toString()));
						roleMenus.add(rmm);
					}
					r.setRoleMenus(roleMenus);
					result = loginServiceIntf.persistRole(r);

				}
			} catch (ParseException e) {
				result.put("result", "fail");
				result.put("msg", "Invalid JSON");
				e.printStackTrace();
			}
		} catch (Exception e) {
			result.put("result", "fail");
			result.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public ManagementInfoService getManagementInfoService() {
		return managementInfoService;
	}

	public void setManagementInfoService(ManagementInfoService managementInfoService) {
		this.managementInfoService = managementInfoService;
	}

	public LoginServiceIntf getLoginServiceIntf() {
		return loginServiceIntf;
	}

	public void setLoginServiceIntf(LoginServiceIntf loginServiceIntf) {
		this.loginServiceIntf = loginServiceIntf;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public JwtTokenUtil getJwtTokenUtil() {
		return jwtTokenUtil;
	}

	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public UserDetailsServiceImpl getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
