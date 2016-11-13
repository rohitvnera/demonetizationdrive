package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.GetExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.constants.UserConstants;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class GetUseDetailsExecutor extends GetExecutor<String, List<UserDetailsData>>
{

	@Autowired
	private IUserBO userBO;

	@Autowired
	private IAuthorizer userAuthorizer;

	
	@Override
	public ResponseEntity<List<UserDetailsData>> executeBusinessLogic(RequestEntity<String> requestEntity)
		throws ExecutorException
	{
		String ids = Util.getStringValue(getAttribute(UserConstants.USER_IDS));
		Set<Long> userIds = Util.getLongList(ids, ",");

		ResponseEntity<List<UserDetailsData>> responseEntity = null;
		try
		{
			List<UserDetailsData> userDetailsData = userBO.getUserDetails(userIds);
			responseEntity = new ResponseEntity<List<UserDetailsData>>(userDetailsData, HttpStatus.OK);
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
