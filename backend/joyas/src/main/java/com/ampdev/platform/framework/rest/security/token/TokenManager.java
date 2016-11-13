package com.ampdev.platform.framework.rest.security.token;

import java.util.Collection;
import java.util.Map;

import com.ampdev.platform.module.tictactoe.dataobject.User;

public interface TokenManager
{

	/**
	 * Creates a new token for the user and returns its {@link TokenInfo}. It may add it to the token list or replace the
	 * previous one for the user. Never returns {@code null}.
	 */
	TokenInfo createNewToken(String userName);

	/** Removes all tokens for user. */
	void removeUserDetails(String userName);

	/** Removes a single token. */
	String removeToken(String token);

	/** Returns user details for a token. */
	String getUserDetails(String token);

	/** Returns a collection with token information for a particular user. */
	Collection<TokenInfo> getUserTokens(String userName);

	/** Returns a map from valid tokens to users. */
	Map<String, String> getValidUsers();

	TokenInfo createNewToken(String userDetails, String token);

	public User getUserName(String userId);

	public void putUserName(String userId, User userDeviceData);
}
