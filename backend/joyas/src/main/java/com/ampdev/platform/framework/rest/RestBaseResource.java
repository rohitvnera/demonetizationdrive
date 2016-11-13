package com.ampdev.platform.framework.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.constants.Module;

public class RestBaseResource
{

	protected final <K, V> ResponseEntity<V> performTask(Module module, RequestEntity<K> requestEntity,
		BaseExecutor<K, V> baseExecutor)
	{
		// No authentication required as spring-security is taking care of it

		ResponseEntity<V> responseEntity = null;
		try
		{
			baseExecutor.setModule(module);
			responseEntity = baseExecutor.execute(requestEntity);
		}
		catch (ExecutorException e)
		{
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
}
