package com.ampdev.platform.framework.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ampdev.platform.framework.rest.exception.AutorizationException;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.constants.Module;

public abstract class BaseExecutor<K, V>
{

	public RequestMethod methodType = null;

	private ResponseEntity<V> UNAUTHORIZED_RESPONSE = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	private Map<String, Object> attributeMap = null;

	private Module moduleEnum;

	public BaseExecutor()
	{
		attributeMap = new HashMap<>();
	}

	public void setModule(Module moduleEnum)
	{
		this.moduleEnum = moduleEnum;
	}

	public Module getModule()
	{
		return this.moduleEnum;
	}

	public final Object getAttribute(String key)
	{
		return attributeMap.get(key);
	}

	public final void setAttribute(String key, Object value)
	{
		attributeMap.put(key, value);
	}

	public final ResponseEntity<V> execute(RequestEntity<K> requestEntity) throws ExecutorException
	{
		try
		{
			IAuthorizer authorizer = getAuthorizer();
			authorizer.authorize();
		}
		catch (AutorizationException e)
		{
			return UNAUTHORIZED_RESPONSE;
		}

		ResponseEntity<V> responseEntity = executeBusinessLogic(requestEntity);

		return responseEntity;
	}

	public abstract ResponseEntity<V> executeBusinessLogic(RequestEntity<K> requestEntity) throws ExecutorException;

	public abstract IAuthorizer getAuthorizer();
}
