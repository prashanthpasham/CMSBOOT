package com.prashanth.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.prashanth.model.Department;
import com.prashanth.model.Employee;
import com.prashanth.model.EmployeeDetails;
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
					List<Object[]> ls = entityManager.createNativeQuery(
							"select m.MENU_ITEM_TITLE,m.MENU_ITEM_ID,(select m1.MENU_ITEM_TITLE||'@'||m1.MENU_ITEM_ID from MENU_ITEM m1 where  m1.MENU_ITEM_ID=m.PARENT_MENU_ID) from MENU_ITEM m,ROLE_MENU_MAP rm where m.MENU_ITEM_ID=rm.MENU_ITEM_ID"
									+ " and  rm.ROLE_ID=" + role.getRoleId() + " and m.PARENT_MENU_ID>0")
							.getResultList();
					if (!ls.isEmpty()) {
						for (Object[] obj : ls) {
							JSONObject top = null;
							JSONArray menus = null;
							String s2[] = obj[2].toString().split("@");
							if (topMenus.containsKey(s2[0].toString())) {
								top = topMenus.get(s2[0].toString());
								menus = (JSONArray) top.get("menus");
							} else {
								top = new JSONObject();
								top.put("menutitle", s2[0].toString());
								top.put("menuid", s2[1].toString());
								menus = new JSONArray();
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
		} catch (Exception e) {
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
				int i = entityManager.createNativeQuery("delete from ORGANIZATION_STRUCTURE where OWNER_ID=" + ownerId)
						.executeUpdate();
				System.out.println(i + " deleted successfully!");
			}
			// entityManager.getTransaction().begin();
			for (OrganizationStructure org : orgChart) {
				org.setOwnerId(ownerId);
				entityManager.merge(org);
			}
			// entityManager.getTransaction().commit();
			// entityManager.close();
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

	public void addNode(JSONObject node, String id, String label, String pid) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getDesignations(int ownerId) {

		JSONObject response = new JSONObject();
		JSONArray array = new JSONArray();
		try {

			List<Object[]> ls = entityManager.createNativeQuery(
					"select ORGANIZATION_ID,HIERACHY,NAME,PARENT_HIERARCHY,PARENT_NAMES from ORGANIZATION_STRUCTURE where OWNER_ID="
							+ ownerId + " order by HIERACHY")
					.getResultList();
			if (!ls.isEmpty()) {
				for (Object[] obj : ls) {
					JSONObject node = new JSONObject();
					node.put("id", obj[0].toString());
					node.put("hierarchy", obj[1].toString());
					node.put("label", obj[2].toString());
					node.put("parentNames", obj[3] != null ? obj[3].toString() : "NA");
					array.add(node);
				}
			}

			response.put("msg", "");
		} catch (Exception e) {
			response.put("msg", e.getMessage());
			e.printStackTrace();
		}
		response.put("hierarchy", array);
		return response;

	}

	@Override
	public JSONObject saveDepartment(JSONObject deptObj) {
		JSONObject response = new JSONObject();
		try {

			Department dept = new Department();
			dept.setDepartmentName(deptObj.get("deptName").toString());
			dept.setDeptCode(deptObj.get("deptCode").toString());
			dept.setOwnerId(Integer.parseInt(deptObj.get("ownerId").toString()));
			int deptId = Integer.parseInt(deptObj.get("deptId").toString());
			if (deptId > 0) {
				dept.setDepartmentId(deptId);
				entityManager.merge(dept);
			} else
				entityManager.persist(dept);
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "fail");
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public JSONObject departmentList(int ownerId) {
		JSONObject response = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			List<Department> deptList = entityManager.createQuery("from Department where ownerId=" + ownerId)
					.getResultList();
			if (!deptList.isEmpty()) {
				for (Department dept : deptList) {
					JSONObject obj = new JSONObject();
					obj.put("deptId", dept.getDepartmentId());
					obj.put("deptName", dept.getDepartmentName());
					obj.put("deptCode", dept.getDeptCode());
					array.add(obj);
				}
			}
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "fail");
			e.printStackTrace();
		}
		response.put("department", array);
		return response;
	}

	@Override
	public JSONObject deleteDepartment(int deptId) {
		JSONObject response = new JSONObject();
		try {
			entityManager.createNativeQuery("delete from department where DEPARTMENT_ID=" + deptId).executeUpdate();
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "fail");
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public JSONObject saveEmployee(JSONObject empObj) {
		JSONObject response = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Employee emp = new Employee();
			 if(empObj.get("employeeId")!=null)
			 emp.setEmployeeId(Integer.parseInt(empObj.get("employeeId").toString()));
			emp.setEmployeeCode(empObj.get("employeeCode").toString().trim());
			emp.setEmployeeName(empObj.get("employeeName").toString().trim());
			emp.setCreatedDate(new Date());
			emp.setCreatedUser(empObj.get("createdUserName").toString().trim());
			emp.setDepartment(new Department(Integer.parseInt(empObj.get("deptId").toString())));
			emp.setDesignation(Integer.parseInt(empObj.get("designationId").toString()));
			emp.setOwnerId(Integer.parseInt(empObj.get("ownerId").toString()));
			emp.setStatus(empObj.get("status").toString());
			if (empObj.get("reportingTo") != null)
				emp.setReportingTo(Integer.parseInt(empObj.get("reportingTo").toString()));
			if (empObj.get("accessHr") != null)
				emp.setAccessHierarchy(empObj.get("accessHr").toString());
			if (empObj.get("joinedDate") != null && empObj.get("joinedDate").toString().trim().length() > 0)
				emp.setJoinedDate(sdf.parse(empObj.get("joinedDate").toString()));
			if (empObj.get("photo") != null && empObj.get("photo").toString().trim().length() > 0)
				emp.setPhoto(new String(empObj.get("photo").toString().trim()).getBytes());
			Set<EmployeeDetails> empDetails = new HashSet<EmployeeDetails>();
			if(emp.getEmployeeId()>0) {
				entityManager.createQuery("delete from EmployeeDetails s where s.employee.employeeId="+emp.getEmployeeId()).executeUpdate();
			}
			JSONArray expList = (JSONArray) empObj.get("experienceDetails");
			System.out.println("expList>>"+expList.toJSONString());
			for (int k = 0; k < expList.size(); k++) {
				JSONObject empData = (JSONObject) expList.get(k);
				EmployeeDetails ed = new EmployeeDetails();
				ed.setInstitute(empData.get("institute").toString().trim());
				ed.setStartYear(sdf.parse(empData.get("startYear").toString()));
				ed.setEndYear(sdf.parse(empData.get("endYear").toString()));
				empDetails.add(ed);
			}
			emp.setEmployeeDetailsSet(empDetails);
			entityManager.merge(emp);
			if (emp.getAccessHierarchy() != null) {
				emp.setAccessHierarchy(emp.getAccessHierarchy() + emp.getEmployeeId() + "@");
				System.out.println("empid>>" + (emp.getAccessHierarchy() + emp.getEmployeeId() + "@"));
			}
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "fail");
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public JSONObject employeeList(JSONObject empObj) {
		JSONObject response = new JSONObject();
		JSONArray employees = new JSONArray();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
			String hql = " from Employee e where e.ownerId=" + Integer.parseInt(empObj.get("ownerId").toString());
			if (empObj.get("employeeCode") != null && empObj.get("employeeCode").toString().trim().length() > 0) {
				hql += " and e.employeeCode='" + empObj.get("employeeCode").toString().trim() + "'";
			}
			if (empObj.get("employeeName") != null && empObj.get("employeeName").toString().trim().length() > 0) {
				hql += " and e.employeeName='" + empObj.get("employeeName").toString().trim() + "'";
			}
			if (empObj.get("status") != null && empObj.get("status").toString().trim().length() > 0) {
				hql += " and e.status='" + empObj.get("status").toString().trim() + "'";
			}
			if (empObj.get("designation") != null && empObj.get("designation").toString().trim().length() > 0) {
				hql += " and e.designation=" + Integer.parseInt(empObj.get("designation").toString().trim());
			}
			if (empObj.get("reportingTo") != null && empObj.get("reportingTo").toString().trim().length() > 0) {
				hql += " and e.reportingTo=" + Integer.parseInt(empObj.get("reportingTo").toString().trim());
			}
			if (empObj.get("department") != null && empObj.get("department").toString().trim().length() > 0) {
				hql += " and e.department.departmentId=" + Integer.parseInt(empObj.get("department").toString().trim());
			}
			hql += " order by e.employeeId";
			Query qry = entityManager.createQuery(hql);
			if (empObj.get("records") != null && empObj.get("records").toString().trim().length() > 0) {
				qry.setMaxResults(Integer.parseInt(empObj.get("records").toString().trim()));
			}
			List<Employee> empList = qry.getResultList();
			if (!empList.isEmpty()) {
				for (Employee emp : empList) {
					JSONObject obj = new JSONObject();
					obj.put("employeeId", emp.getEmployeeId());
					obj.put("employeeCode", emp.getEmployeeCode());
					obj.put("employeeName", emp.getEmployeeName());
					obj.put("status", emp.getStatus());
					obj.put("designationId", emp.getDesignation());
					obj.put("deptId", emp.getDepartment().getDepartmentId());
					obj.put("deptName", emp.getDepartment().getDepartmentName());
					obj.put("joinedDate", emp.getJoinedDate() != null ? sdf.format(emp.getJoinedDate()) : "");
					obj.put("photo", emp.getPhoto() != null ? new String(emp.getPhoto()) : "");
					obj.put("createdOn", sdf.format(emp.getCreatedDate()));
					obj.put("createdBy", emp.getCreatedUser());
					obj.put("reportingTo", emp.getReportingTo());
					obj.put("accessHr", emp.getAccessHierarchy() != null ? emp.getAccessHierarchy() : "");
					JSONArray expList = new JSONArray();
					for (EmployeeDetails edt : emp.getEmployeeDetailsSet()) {
						JSONObject ob = new JSONObject();
						ob.put("empDetailsId", edt.getEmpDetailsId());
						ob.put("institute", edt.getInstitute());
						ob.put("startYear", sdf.format(edt.getStartYear()));
						ob.put("endYear", sdf.format(edt.getEndYear()));
						expList.add(ob);
					}
					obj.put("workExperience", expList);
					employees.add(obj);
				}
			}
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "fail");
			e.printStackTrace();
		}
		response.put("employees", employees);
		return response;
	}

}
