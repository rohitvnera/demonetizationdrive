package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.GetExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.constants.UserConstants;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class SearchAddressExecutor extends GetExecutor<String, UserAddress>
{

	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<UserAddress> executeBusinessLogic(RequestEntity<String> requestEntity) throws ExecutorException
	{
		ResponseEntity<UserAddress> responseEntity = null;
		try
		{
			long userId = Util.getLongValue(getAttribute(UserConstants.USER_ID));
			UserAddress userAddress = userBO.getAddress(userId);
			if (userAddress == null)
			{
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				responseEntity = new ResponseEntity<UserAddress>(userAddress, HttpStatus.OK);
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
