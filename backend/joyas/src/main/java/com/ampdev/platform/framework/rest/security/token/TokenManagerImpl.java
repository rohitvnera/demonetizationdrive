package com.ampdev.platform.framework.rest.security.token;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ampdev.platform.module.tictactoe.dataobject.User;
import org.springframework.security.crypto.codec.Base64;


/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again, older token is invalidated.
 */
public class TokenManagerImpl implements TokenManager
{

	private static TokenManagerImpl instance = new TokenManagerImpl();

	public static TokenManagerImpl getTokenManager()
	{
		return instance;
	}

	private TokenManagerImpl()
	{

	}

	private Map<String, String> validUsers = new HashMap<>();

	/**
	 * This maps system users to tokens because equals/hashCode is delegated to User entity. This can store either one token or
	 * list of them for each user, depending on what you want to do. Here we store single token, which means, that any older
	 * tokens are invalidated.
	 */
	private Map<String, TokenInfo> tokens = new HashMap<>();

	private Map<String, User> userNames = new HashMap<>();

	@Override
	public TokenInfo createNewToken(String userName, String token)
	{

		TokenInfo tokenInfo = new TokenInfo(token, userName);
		removeUserDetails(userName);
		validUsers.put(token, userName);

		tokens.put(userName, tokenInfo);

		return tokenInfo;
	}

	@Override
	public TokenInfo createNewToken(String userDetails)
	{
		String token;
		do
		{
			token = generateToken();
		} while (validUsers.containsKey(token));

		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		removeUserDetails(userDetails);
		String previous = validUsers.put(token, userDetails);
		if (previous != null)
		{
			return null;
		}
		tokens.put(userDetails, tokenInfo);

		return tokenInfo;
	}

	private String generateToken()
	{
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
	}

	@Override
	public void removeUserDetails(String userDetails)
	{
		TokenInfo token = tokens.remove(userDetails);
		if (token != null)
		{
			validUsers.remove(token.getToken());
		}
	}

	@Override
	public String removeToken(String token)
	{
		String userDetails = validUsers.remove(token);
		if (userDetails != null)
		{
			tokens.remove(userDetails);
		}
		return userDetails;
	}

	@Override
	public String getUserDetails(String token)
	{
		return validUsers.get(token);
	}

	@Override
	public Collection<TokenInfo> getUserTokens(String userDetails)
	{
		return Arrays.asList(tokens.get(userDetails));
	}

	@Override
	public Map<String, String> getValidUsers()
	{
		return Collections.unmodifiableMap(validUsers);
	}

	@Override
	public void putUserName(String userId, User User)
	{
		userNames.put(userId, User);
	}

	@Override
	public User getUserName(String userId)
	{
		return userNames.get(userId);
	}

	public Map<String, User> getUserDeviceDataCache()
	{
		return userNames;
	}

}
