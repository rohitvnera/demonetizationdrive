package com.ampdev.platform.framework.rest.security.token;

import java.util.Date;

public final class TokenInfo
{

	private final long created = System.currentTimeMillis();
	private final String token;
	private final String userDetails;

	// TODO expiration etc

	public TokenInfo(String token, String userDetails)
	{
		this.token = token;
		this.userDetails = userDetails;
	}

	public String getToken()
	{
		return token;
	}

	@Override
	public String toString()
	{
		return "TokenInfo{" + "token='" + token + '\'' + ", userDetails" + userDetails + ", created=" + new Date(created) + '}';
	}
}
