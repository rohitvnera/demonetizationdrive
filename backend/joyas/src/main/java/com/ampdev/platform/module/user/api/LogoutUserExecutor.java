package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.DeleteExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.framework.rest.security.token.TokenAuthenticationFilter;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.user.UserBO;
import com.ampdev.platform.module.user.dataobject.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class LogoutUserExecutor extends DeleteExecutor<UserData, String>
{

	@Autowired
	private UserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;


	@Override
	public ResponseEntity<String> executeBusinessLogic(RequestEntity<UserData> requestEntity) throws ExecutorException
	{
		List<String> tokens = requestEntity.getHeaders().get(TokenAuthenticationFilter.HEADER_TOKEN);
		System.out.println("Tokens: " + tokens);
		ResponseEntity<String> responseEntity = null;
		if (Util.isEmpty(tokens))
		{
			responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else
		{
			UserData userData = requestEntity.getBody();
			String token = tokens.get(0);
			userBO.logout(userData.getUserName(), token);
			responseEntity = new ResponseEntity<>(userData.getUserName(), HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}


	@Override
	public IAuthorizer getAuthorizer()
	{
		return userAuthorizer;
	}

}
