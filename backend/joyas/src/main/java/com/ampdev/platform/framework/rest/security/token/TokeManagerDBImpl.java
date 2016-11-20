package com.ampdev.platform.framework.rest.security.token;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ampdev.platform.module.tictactoe.dataobject.User;
import org.springframework.security.crypto.codec.Base64;


public class TokeManagerDBImpl implements TokenManager
{

	private Map<String, String> validUsers = new HashMap<>();
	private Map<String, TokenInfo> tokens = new HashMap<>();

	@Override
	public TokenInfo createNewToken(String userName)
	{
		String token;
		do
		{
			token = generateToken();
		} while (validUsers.containsKey(token));

		TokenInfo tokenInfo = new TokenInfo(token, userName);
		removeUserDetails(userName);
		String previous = validUsers.put(token, userName);
		if (previous != null)
		{
			return null;
		}
		tokens.put(userName, tokenInfo);
		persistInDB(userName, tokenInfo);
		return tokenInfo;
	}

	private void persistInDB(String userName, TokenInfo tokenInfo)
	{

	}

	private String generateToken()
	{
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
	}

	@Override
	public void removeUserDetails(String userName)
	{
		TokenInfo token = tokens.remove(userName);
		if (token != null)
		{
			validUsers.remove(token.getToken());
		}

	}

	@Override
	public String removeToken(String token)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserDetails(String token)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TokenInfo> getUserTokens(String userName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getValidUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TokenInfo createNewToken(String userDetails, String token)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserName(String userId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putUserName(String userId, User userDeviceData)
	{
		// TODO Auto-generated method stub

	}

}
