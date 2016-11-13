package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PostExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class AddUserAddressExecutor extends PostExecutor<UserAddress, UserAddress>
{

	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<UserAddress> executeBusinessLogic(RequestEntity<UserAddress> requestEntity) throws ExecutorException
	{

		ResponseEntity<UserAddress> responseEntity = null;
		try
		{
			UserAddress userAddress = requestEntity.getBody();

			userBO.addAddress(userAddress);
			responseEntity = new ResponseEntity<>(userAddress, HttpStatus.OK);
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
