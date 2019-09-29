package com.prashanth.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.prashanth.model.MenuItem;
import com.prashanth.model.Role;
import com.prashanth.model.RoleMenuMap;
import com.prashanth.model.Users;
import com.prashanth.restcontroller.AESEncoder;

@Repository
public class LoginDaoImpl implements LoginDaoIntf {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JSONObject validateLogin(JSONObject login) {
		JSONObject response = null;
		try {
			AESEncoder.init("t7GcYbbdbKxZtV2ge6qpeQ==");
			String pwd = AESEncoder.getInstance().encode(
					login.get("username").toString().toLowerCase().trim() + "#" + login.get("password").toString());
			String hql = "from Users u where lower(u.userName)='"
					+ login.get("username").toString().trim().toLowerCase() + "'" + " and u.password='" + pwd + "'";
			List<Users> data = entityManager.createQuery(hql).getResultList();
			if (!data.isEmpty()) {
				response = new JSONObject();
				Users user = data.get(0);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						user.getUserName().toLowerCase(), user.getPassword(), null);
				SecurityContextHolder.getContext().setAuthentication(token);
				response.put("token", token.getName());
				response.put("username", user.getUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Users findUserByName(String userName) {
		Users user = null;
		try {
			List<Users> usersList = entityManager
					.createQuery("from Users u where lower(u.userName)='" + userName.trim().toLowerCase() + "'")
					.getResultList();
			if (usersList.size() > 0) {
				user = usersList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public JSONObject menusByUserName(String userName) {
		JSONObject response = new JSONObject();
		Users user = findUserByName(userName);
		if (user != null) {
			if (user.getStatus().equalsIgnoreCase("active")) {

				Role role = user.getRoleId();
				response.put("username", user.getUserName());
				response.put("role", role.getRole());
				response.put("ownerid", user.getOwnerId());
				// JSONArray menus = new JSONArray();
				System.out.println("role.getRoleMenus()>>" + role.getRoleMenus().size());
				Map<Integer, JSONObject> topMenus = new HashMap<Integer, JSONObject>();
				for (RoleMenuMap rmp : role.getRoleMenus()) {
					MenuItem mi = rmp.getMenuItem();
					if (mi.getParentMenuId() == 0) {
						if (!topMenus.containsKey(mi.getMenuItemTitle())) {
							JSONObject topMenu = new JSONObject();
							topMenu.put("menutitle", mi.getMenuItemTitle());
							//topMenu.put("menuid", mi.getMenuItemId());
							//topMenu.put("menuurl", mi.getMenuItemUrl());
							topMenu.put("menus", new JSONArray());
							topMenus.put(mi.getMenuItemId(), topMenu);

						}

					}
				}
				System.out.println("topMenus>>" + topMenus);
				for (RoleMenuMap rmp : role.getRoleMenus()) {
					MenuItem mi = rmp.getMenuItem();
					// System.out.println("mi>>"+mi.getMenuItemId());
					if (mi != null && mi.getParentMenuId() > 0) {
						JSONObject topMenu = topMenus.get(mi.getParentMenuId());
						JSONArray array = (JSONArray) topMenu.get("menus");
						JSONObject menu = new JSONObject();
						menu.put("menutitle", mi.getMenuItemTitle());
						array.add(menu);
						topMenu.put("menus", array);
						topMenus.put(mi.getParentMenuId(), topMenu);
					}

				}

				JSONArray topArray = new JSONArray();
				for (Map.Entry<Integer, JSONObject> me : topMenus.entrySet()) {
					topArray.add(me.getValue());
				}
				response.put("links", topArray);
				response.put("error", "");

			} else {
				response.put("error", "User is inactive");
			}
		} else {
			response.put("error", "User not found");
		}
		System.out.println("response>>"+response.toJSONString());
		return response;
	}

	@Override
	public JSONObject persistRole(Role r) {
		JSONObject response = new JSONObject();
		try {
			List<Role> roles = entityManager.createQuery("from Role r where lower(r.role)='"
					+ r.getRole().toString().trim().toLowerCase() + "' and r.owner_id=" + r.getOwnerId())
					.getResultList();
			if (roles.isEmpty()) {
				entityManager.persist(r);
				for (RoleMenuMap rmm : r.getRoleMenus()) {
					rmm.setRole(r);
					entityManager.persist(rmm);
				}
				response.put("result", "success");
				response.put("msg", "Role Created");
			} else {
				response.put("result", "fail");
				response.put("msg", "Same Role exist");
			}
		} catch (Exception e) {
			response.put("result", "fail");
			response.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

}
