package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PostExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.framework.rest.security.token.TokenAuthenticationFilter;
import com.ampdev.platform.framework.rest.security.token.TokenInfo;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;
import com.ampdev.platform.module.common.util.FacebookUtil;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import com.ampdev.platform.module.user.dataobject.UserDeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class FBUserCreateUpdateExecutor extends PostExecutor<FBUserData, FBUserData>
{
	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<FBUserData> executeBusinessLogic(RequestEntity<FBUserData> requestEntity) throws ExecutorException
	{

		ResponseEntity<FBUserData> responseEntity = null;
		FBUserData userData = requestEntity.getBody();

		try
		{

			HttpHeaders responseHeaders = new HttpHeaders();
			String userName = userData.getUserName();
			String accessToken = userData.getAccessToken();


		}
		catch (Exception e)
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
