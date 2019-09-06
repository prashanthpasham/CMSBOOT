package com.prashanth.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
			String hql="from Users u where u.userName='"+login.get("username").toString().trim()+ "'"
					+ " and u.password='"+login.get("password").toString()+"'";
			List<Users> data=entityManager.createQuery(hql).getResultList();
			if(!data.isEmpty()) {
				Users user=data.get(0);
				if(user.getStatus().equalsIgnoreCase("active")) {
					Role role=user.getRoleId();
					response.put("username", user.getUserName());
					response.put("role", role.getRole());
					response.put("ownerid", user.getOwnerId());
					JSONArray menus= new JSONArray();
					Map<Integer,JSONObject> topMenus = new HashMap<Integer,JSONObject>();
					for(RoleMenuMap rmp:role.getRoleMenus()) {
						MenuItem mi=rmp.getMenuItem();
						JSONObject topMenu=null;
						if (mi.getParentMenuId() == 0) {
							if (!topMenus.containsKey(mi.getMenuItemTitle())) {
								topMenu = new JSONObject();
								topMenu.put("menutitle", mi.getMenuItemTitle());
								topMenu.put("menuid", mi.getMenuItemId());
								topMenu.put("menuurl", mi.getMenuItemUrl());
								topMenu.put("menus", null);
								topMenus.put(mi.getMenuItemId(), topMenu);

							}

						} else {
							topMenu = topMenus.get(mi.getMenuItemId());
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
							topMenus.put(mi.getMenuItemId(), topMenu);

						} 
						
					}
					JSONArray topArray = new JSONArray();
					for(Map.Entry<Integer, JSONObject> me:topMenus.entrySet()) {
						topArray.add(me.getValue());
					}
					response.put("links",topArray);
					response.put("error", "");
				}else {
					response.put("error", "User is inactive");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
