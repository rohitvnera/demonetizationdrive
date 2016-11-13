package com.ampdev.platform.framework.rest;

import com.ampdev.platform.framework.rest.exception.AutorizationException;

public interface IAuthorizer
{

	void authorize() throws AutorizationException;

}
