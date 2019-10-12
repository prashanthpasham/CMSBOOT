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
import com.prashanth.model.OrganizationStructure;
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
		try {
		Users user = findUserByName(userName);
		if (user != null) {
			if (user.getStatus().equalsIgnoreCase("active")) {

				Role role = user.getRoleId();
				response.put("username", user.getUserName());
				response.put("role", role.getRole());
				response.put("ownerid", user.getOwnerId());
				// JSONArray menus = new JSONArray();
				System.out.println("role.getRoleMenus()>>" + role.getRoleMenus().size());
				Map<String, JSONObject> topMenus = new HashMap<String, JSONObject>();
				List<Object[]> ls=entityManager.createNativeQuery("select m.MENU_ITEM_TITLE,m.MENU_ITEM_ID,(select m1.MENU_ITEM_TITLE||'@'||m1.MENU_ITEM_ID from MENU_ITEM m1 where  m1.MENU_ITEM_ID=m.PARENT_MENU_ID) from MENU_ITEM m,ROLE_MENU_MAP rm where m.MENU_ITEM_ID=rm.MENU_ITEM_ID"
						+" and  rm.ROLE_ID="+role.getRoleId()+" and m.PARENT_MENU_ID>0").getResultList();
				if (!ls.isEmpty()) {
					for (Object[] obj : ls) {
						JSONObject top = null;
						JSONArray menus = null;
						String s2[]=obj[2].toString().split("@");
						if (topMenus.containsKey(s2[0].toString())) {
							top = topMenus.get(s2[0].toString());
							menus=(JSONArray)top.get("menus");
						} else {
							top = new JSONObject();
							top.put("menutitle", s2[0].toString());
							top.put("menuid", s2[1].toString());
							menus=new JSONArray();
						}
						JSONObject menu = new JSONObject();
						menu.put("menutitle", obj[0].toString());
						menu.put("menuid", obj[1].toString());
						menus.add(menu);
						top.put("menus", menus);
						topMenus.put(s2[0].toString(), top);
					}
				}
				

				JSONArray topArray = new JSONArray();
				for (Map.Entry<String, JSONObject> me : topMenus.entrySet()) {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public JSONObject saveOrgChart(List<OrganizationStructure> orgChart, int ownerId) {
		JSONObject response = new JSONObject();
		try {
			List<Object> ls1 = entityManager
					.createNativeQuery(
							"select NAME from ORGANIZATION_STRUCTURE where OWNER_ID=" + ownerId + " order by HIERACHY")
					.getResultList();
			if (!ls1.isEmpty()) {
				int i=entityManager.createNativeQuery("delete from ORGANIZATION_STRUCTURE where OWNER_ID=" + ownerId)
						.executeUpdate();
				System.out.println(i+" deleted successfully!");
			}
			//entityManager.getTransaction().begin();
			for(OrganizationStructure org:orgChart) {
				org.setOwnerId(ownerId);
				entityManager.merge(org);
			}
			//entityManager.getTransaction().commit();
			//entityManager.close();
			response.put("result", "success");
			response.put("msg", "");
		} catch (Exception e) {
			response.put("result", "fail");
			response.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public JSONObject fetchOrgChart(int ownerId) {
		JSONObject response = new JSONObject();
		JSONObject node = new JSONObject();
		try {
			
				List<Object[]> ls = entityManager.createNativeQuery(
						"select HIERACHY,NAME,PARENT_HIERARCHY,PARENT_NAME from ORGANIZATION_STRUCTURE where OWNER_ID="
								+ ownerId + " order by HIERACHY")
						.getResultList();
				if (!ls.isEmpty()) {
					for (Object[] obj : ls) {
						if (node.isEmpty()) {
							node.put("id", obj[0].toString());
							node.put("label", obj[1].toString());
							node.put("children", new JSONArray());
							node.put("expanded", true);
						} else {
							addNode(node, obj[0].toString(), obj[1].toString(), obj[2].toString());
						}

					}
				}
		
			response.put("msg", "");
		} catch (Exception e) {
			response.put("msg", e.getMessage());
			e.printStackTrace();
		}
		response.put("hierarchy", node);
		return response;
	}
	public void  addNode(JSONObject node,String id,String label,String pid) {
		try {
			JSONObject sub = new JSONObject();
			sub.put("id", id);
			sub.put("label", label);
			sub.put("children", new JSONArray());
			sub.put("expanded", true);
			JSONArray child = (JSONArray) node.get("children");
			if (node.get("id").toString().equals(pid)) {
				child.add(sub);
			} else {
				for (int k = 0; k < child.size(); k++) {
					JSONObject sub1 = (JSONObject) child.get(k);
					if (sub1.get("id").toString().equals(pid)) {
						((JSONArray) sub1.get("children")).add(sub);
					} else {
						addNode(sub1, id, label, pid);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
