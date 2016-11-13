package com.ampdev.platform.module.common.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.module.common.constants.Module;

public class AuthorizerFactory
{

	@Autowired
	private IAuthorizer countryAuthrozier;


	@Autowired
	private IAuthorizer userAuthorizer;

	@Autowired
	private IAuthorizer newsAuthrozier;

	@Autowired
	private IAuthorizer ticTacToeAuthrozier;

	@Autowired
	private IAuthorizer categoryAuthrozier;

	public IAuthorizer getAutorizer(Module moduleEnum)
	{
		IAuthorizer iAuthorizer = null;

		switch (moduleEnum)
		{
			case FACEBOOK_LOGIN:
				iAuthorizer = userAuthorizer;
				break;

			case COUNTRY:
				iAuthorizer = countryAuthrozier;
				break;

			case USER:
				iAuthorizer = userAuthorizer;
				break;

			case NEWS:
				iAuthorizer = newsAuthrozier;
				break;

			case TICTACTOE:
				iAuthorizer = ticTacToeAuthrozier;
				break;

			default:
				break;
		}

		return iAuthorizer;
	}
}
