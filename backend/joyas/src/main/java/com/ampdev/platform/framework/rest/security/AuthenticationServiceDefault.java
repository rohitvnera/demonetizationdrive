package com.ampdev.platform.framework.rest.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ampdev.platform.framework.rest.security.token.TokenInfo;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;

public class AuthenticationServiceDefault implements AuthenticationService
{

	@Autowired
	private ApplicationContext applicationContext;

	public AuthenticationServiceDefault()
	{
	}

	@PostConstruct
	public void init()
	{
	}

	@Override
	public TokenInfo authenticate(String login, String password)
	{

		return null;
	}

	@Override
	public boolean checkToken(String token)
	{

		String userDetails = TokenManagerImpl.getTokenManager().getUserDetails(token);
		if (userDetails == null)
		{
			return false;
		}

		return true;
	}

	@Override
	public void logout(String token)
	{
		String logoutUser = TokenManagerImpl.getTokenManager().removeToken(token);
		SecurityContextHolder.clearContext();
	}

	@Override
	public UserDetails currentUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
		{
			return null;
		}
		return null;
	}
}
