package com.ampdev.platform.module.common.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.ampdev.platform.framework.rest.DeleteExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.bo.IGenericBO;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.constants.URIConstants;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.factory.AuthorizerFactory;
import com.ampdev.platform.module.common.util.Util;

public class GenericDeleteExecutor extends DeleteExecutor<String, String>
{

	@Autowired
	private AuthorizerFactory authorizerFactory;

	@Autowired
	private IGenericBO genericBO;

	@Override
	public ResponseEntity<String> executeBusinessLogic(RequestEntity<String> requestEntity) throws ExecutorException
	{
		ResponseEntity<String> responseEntity = null;
		Module module = getModule();

		try
		{
			long countryId = Util.getLongValue(getAttribute(URIConstants.ID));
			genericBO.delete(module, countryId);
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
		return authorizerFactory.getAutorizer(getModule());
	}

}
