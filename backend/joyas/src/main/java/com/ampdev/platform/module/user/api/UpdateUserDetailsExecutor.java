package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PutExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class UpdateUserDetailsExecutor extends PutExecutor<UserDetailsData, UserDetailsData>
{

	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<UserDetailsData> executeBusinessLogic(RequestEntity<UserDetailsData> requestEntity)
		throws ExecutorException
	{
		ResponseEntity<UserDetailsData> responseEntity = null;

		try
		{
			UserDetailsData userDetailsData = requestEntity.getBody();
			userBO.updateUserDetails(userDetailsData);
			responseEntity = new ResponseEntity<>(userDetailsData, HttpStatus.OK);
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
