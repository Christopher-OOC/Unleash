package com.example.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.model.entity.User;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -5363945485861629206L;
	
	private User user;
	
	public UserPrincipal(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		Collection<String> role_Authorities = new HashSet<>();
		
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			
			role.getAuthorities().forEach(authority -> {
				role_Authorities.add(authority.getName());
			});
		});
		
		role_Authorities.forEach(authorityName -> {
			authorities.add(new SimpleGrantedAuthority(authorityName));
		});
		
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return this.user.isEmailVerificationStatus();
	}
}
