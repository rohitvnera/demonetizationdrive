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
		System.out.println(" *** AuthenticationServiceImpl.init with: " + applicationContext);
	}

	@Override
	public TokenInfo authenticate(String login, String password)
	{
		System.out.println(" *** AuthenticationServiceImpl.authenticate");

		return null;
	}

	@Override
	public boolean checkToken(String token)
	{
		System.out.println(" *** AuthenticationServiceImpl.checkToken");

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
		System.out.println(" *** AuthenticationServiceImpl.logout: " + logoutUser);
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
