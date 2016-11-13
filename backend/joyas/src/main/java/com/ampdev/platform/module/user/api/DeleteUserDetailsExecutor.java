package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.DeleteExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class DeleteUserDetailsExecutor extends DeleteExecutor<String, String>
{

	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	@Override
	public ResponseEntity<String> executeBusinessLogic(RequestEntity<String> requestEntity) throws ExecutorException
	{
		long userId = Util.getLongValue(getAttribute(UserConstants.USER_ID));
		ResponseEntity<String> responseEntity = null;
		try
		{
			userBO.deleteDetails(userId);
			responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
		// TODO Auto-generated method stub
		return userAuthorizer;
	}

}
