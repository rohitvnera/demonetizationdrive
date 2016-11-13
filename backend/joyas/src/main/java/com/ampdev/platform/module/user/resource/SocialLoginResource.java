package com.ampdev.platform.module.user.resource;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.rest.BaseExecutor;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/ws/social")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SocialLoginResource extends RestBaseResource
{

	@Autowired
	private BaseExecutor<FBUserData, FBUserData> fbUserCreateUpdateExecutor;

	@Autowired
	private BaseExecutor<FBUserData, String> fbUserLoginExecutor;

	@RequestMapping(value = "/fb/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signup(RequestEntity<FBUserData> requestEntity) throws DAOException
	{
		ResponseEntity<FBUserData> responseEntity = performTask(Module.FACEBOOK_LOGIN, requestEntity,
			fbUserCreateUpdateExecutor);
		return responseEntity;
	}

	@RequestMapping(value = "/fb/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(RequestEntity<FBUserData> requestEntity) throws DAOException
	{

		return performTask(Module.FACEBOOK_LOGIN, requestEntity, fbUserLoginExecutor);
	}
}
