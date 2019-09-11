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

@Repository
public class LoginDaoImpl implements LoginDaoIntf {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JSONObject validateLogin(JSONObject login) {
		JSONObject response = new JSONObject();
		try {
			String hql = "from Users u where u.userName='" + login.get("username").toString().trim() + "'"
					+ " and u.password='" + login.get("password").toString() + "'";
			List<Users> data = entityManager.createQuery(hql).getResultList();
			if (!data.isEmpty()) {
				Users user = data.get(0);
				if (user.getStatus().equalsIgnoreCase("active")) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), null);
					SecurityContextHolder.getContext().setAuthentication(token);
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
								topMenu.put("menuid", mi.getMenuItemId());
								topMenu.put("menuurl", mi.getMenuItemUrl());
								topMenu.put("menus", null);
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
							if (array == null) {
								array = new JSONArray();
							}
							JSONObject menu = new JSONObject();
							menu.put("menutitle", mi.getMenuItemTitle());
							menu.put("menuid", mi.getMenuItemId());
							menu.put("menuurl", mi.getMenuItemUrl());
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
					response.put("token", token.getName());
				} else {
					response.put("token","");
					response.put("error", "User is inactive");
				}
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
			List<Users> usersList = entityManager.createQuery("from Users u where u.userName='" + userName.trim() + "'")
					.getResultList();
			if (usersList.size() > 0) {
				user = usersList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
