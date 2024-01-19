package com.logiclegends.MerryMeal.service;


import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.logiclegends.MerryMeal.entities.UserRole;

public class UserPrincipal implements UserDetails{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	private UserRole role;
	
	
	public UserPrincipal(long id, String email, String password, Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> attributes, UserRole role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.attributes = attributes;
		this.role = role;
	}
	

	public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities,
			UserRole role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.role = role;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
}
