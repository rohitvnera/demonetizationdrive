package com.ampdev.platform.module.common.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PostExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.bo.IGenericBO;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.factory.AuthorizerFactory;

public class GenericCreateExecutor<V extends PersistedDataObject> extends PostExecutor<V, V>
{

	@Autowired
	private AuthorizerFactory authorizerFactory;

	@Autowired
	private IGenericBO genericBO;

	@Override
	public IAuthorizer getAuthorizer()
	{
		return authorizerFactory.getAutorizer(getModule());
	}

	@Override
	public ResponseEntity<V> executeBusinessLogic(RequestEntity<V> requestEntity) throws ExecutorException
	{
		ResponseEntity<V> responseEntity = null;
		Module module = getModule();
		try
		{
			V v = requestEntity.getBody();

			genericBO.add(module, v);

			responseEntity = new ResponseEntity<>(v, HttpStatus.OK);
		}
		catch (BOException e)
		{
			e.printStackTrace();
			throw new ExecutorException();
		}
		return responseEntity;
	}
}
