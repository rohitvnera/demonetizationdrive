package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PutExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.dataobject.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class UpdateUserExecutor extends PutExecutor<UserData, UserData>
{
	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<UserData> executeBusinessLogic(RequestEntity<UserData> requestEntity) throws ExecutorException
	{
		ResponseEntity<UserData> responseEntity = null;

		try
		{
			UserData userData = requestEntity.getBody();
			userBO.updateUser(userData);
			responseEntity = new ResponseEntity<>(userData, HttpStatus.OK);
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
