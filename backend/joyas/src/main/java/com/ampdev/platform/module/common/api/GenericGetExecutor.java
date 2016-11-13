package com.ampdev.platform.module.common.api;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.ampdev.platform.framework.rest.GetExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.common.bo.IGenericBO;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.constants.URIConstants;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.common.factory.AuthorizerFactory;
import com.ampdev.platform.module.common.util.Util;

public class GenericGetExecutor<V extends PersistedDataObject> extends GetExecutor<String, List<V>>
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
	public ResponseEntity<List<V>> executeBusinessLogic(RequestEntity<String> requestEntity) throws ExecutorException
	{
		ResponseEntity<List<V>> responseEntity = null;
		Module module = getModule();

		try
		{
			String ids = Util.getStringValue(getAttribute(URIConstants.IDS));
			List<V> countries = null;

			if (Util.isEmpty(ids))
			{
				countries = genericBO.getAll(module);
			}
			else
			{
				Set<Long> uniqueIds = Util.getLongList(ids, ",");
				countries = genericBO.get(module, uniqueIds);
			}

			responseEntity = new ResponseEntity<>(countries, HttpStatus.OK);
		}
		catch (BOException e)
		{
			e.printStackTrace();
			throw new ExecutorException();
		}
		return responseEntity;
	}
}
