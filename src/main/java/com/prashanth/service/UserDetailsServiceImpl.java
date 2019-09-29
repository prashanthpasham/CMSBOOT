package com.prashanth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.prashanth.dao.LoginDaoIntf;
import com.prashanth.model.Users;
import com.prashanth.restcontroller.AESEncoder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private LoginDaoIntf loginDaoIntf;
	/*
	 * @Autowired private BCryptPasswordEncoder bcryptPasswordEncoder;
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = loginDaoIntf.findUserByName(username);
		/*
		 * Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		 * grantedAuthorities.add(new
		 * SimpleGrantedAuthority(user.getRoleId().getRole()));
		 */
		if (user != null) {
			//return new User(user.getUserName(), bcryptPasswordEncoder.encode(user.getPassword()), grantedAuthorities);
			      UserBuilder users = User.builder();
			/*
			 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
			 * manager.createUser(users.username(user.getUserName()).password(bcryptPasswordEncoder.encode(user.getPassword())).roles(user.getRoleId().getRole()).build());
			 */
			         try {
						AESEncoder.init("t7GcYbbdbKxZtV2ge6qpeQ==");
						String pwd = AESEncoder.getInstance().decode(user.getPassword()).split("#")[1];
						 return users.username(user.getUserName()).password(new BCryptPasswordEncoder().encode(pwd)).roles(user.getRoleId().getRole()).build();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		  
		} else {
			throw new UsernameNotFoundException(username);
		}
		// return null;
		return null;
	}

	

}
