package com.ampdev.platform.module.user.resource;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.rest.BaseExecutor;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.framework.rest.security.token.TokenAuthenticationFilter;
import com.ampdev.platform.framework.rest.security.token.TokenInfo;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/ws")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginResource extends RestBaseResource {

	@Autowired
	private BaseExecutor<UserDetailsData, UserDetailsData> createUserExecutor;

	@Autowired
	private BaseExecutor<UserData, String> logoutUserExecutor;

	@Autowired
	private BaseExecutor<UserData, String> loginExecutor;

	@Autowired
	private HttpServletRequest request;

	private static final String HANDSHAKE_HEADER = "AMPDEV_HANDSHAKE";
	private static final String HANDSHAKE_HEADER_VAL = "AMPDEV_CONNECTED";

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() {
		HttpHeaders responseHeaders = new HttpHeaders();
		TokenInfo tokenInfo = TokenManagerImpl.getTokenManager()
				.createNewToken("test");
		responseHeaders.set(TokenAuthenticationFilter.HEADER_TOKEN,
				tokenInfo.getToken());
		ResponseEntity<String> responseEntity = new ResponseEntity<>(
				responseHeaders, HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value = "/handshake", method = RequestMethod.GET)
	public ResponseEntity<?> handShake() {
		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.set(HANDSHAKE_HEADER, HANDSHAKE_HEADER_VAL);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(
				responseHeaders, HttpStatus.CREATED);
		return responseEntity;
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(RequestEntity<UserData> requestEntity)
			throws DAOException {
		return performTask(Module.LOGIN, requestEntity, loginExecutor);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signup(RequestEntity<UserDetailsData> requestEntity)
			throws DAOException {
		return performTask(Module.LOGIN, requestEntity, createUserExecutor);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.DELETE)
	public ResponseEntity<?> logout(RequestEntity<UserData> requestEntity) {
		return performTask(Module.LOGIN, requestEntity, logoutUserExecutor);
	}
}
