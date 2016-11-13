package com.ampdev.platform.framework.rest;

import org.springframework.web.bind.annotation.RequestMethod;

public abstract class PutExecutor<K , V> extends BaseExecutor<K , V>
{
	protected final RequestMethod methodType = RequestMethod.PUT;
}
