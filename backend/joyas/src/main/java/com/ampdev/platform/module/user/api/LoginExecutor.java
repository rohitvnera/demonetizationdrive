package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PostExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.framework.rest.security.token.TokenAuthenticationFilter;
import com.ampdev.platform.framework.rest.security.token.TokenInfo;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.UserBO;
import com.ampdev.platform.module.user.dataobject.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class LoginExecutor extends PostExecutor<UserData, String>
{

	@Autowired
	private UserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;


	@Override
	public ResponseEntity<String> executeBusinessLogic(RequestEntity<UserData> requestEntity) throws ExecutorException
	{
		ResponseEntity<String> responseEntity = null;
		UserData userData = requestEntity.getBody();

		try
		{
			if (userBO.authenticate(userData))
			{
				HttpHeaders responseHeaders = new HttpHeaders();
				String userName = userData.getUserName();
				TokenInfo tokenInfo = TokenManagerImpl.getTokenManager().createNewToken(userName);
				responseHeaders.set(TokenAuthenticationFilter.HEADER_TOKEN, tokenInfo.getToken());
				responseEntity = new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
			}
			else
			{
				responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (BOException e)
		{
			e.printStackTrace();
			throw new ExecutorException();
		}

		return responseEntity;
	}


	@Override
	public IAuthorizer getAuthorizer()
	{
		return userAuthorizer;
	}

}
