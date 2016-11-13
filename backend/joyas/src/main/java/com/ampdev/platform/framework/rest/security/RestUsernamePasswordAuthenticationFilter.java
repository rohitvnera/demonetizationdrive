package com.ampdev.platform.framework.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class RestUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response)
	{
		boolean retVal = false;
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		if (username != null && password != null)
		{
			Authentication authResult = null;
			try
			{
				authResult = attemptAuthentication(request, response);
				if (authResult == null)
				{
					retVal = false;
				}
			}
			catch (AuthenticationException failed)
			{
				try
				{
					unsuccessfulAuthentication(request, response, failed);
				}
				catch (IOException e)
				{
					retVal = false;
				}
				catch (ServletException e)
				{
					retVal = false;
				}
				retVal = false;
			}
			try
			{
				successfulAuthentication(request, response, null, authResult);
			}
			catch (IOException e)
			{
				retVal = false;
			}
			catch (ServletException e)
			{
				retVal = false;
			}
			return false;
		}
		else
		{
			retVal = true;
		}
		return retVal;
	}
}
