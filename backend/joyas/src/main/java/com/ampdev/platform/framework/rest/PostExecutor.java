package com.ampdev.platform.framework.rest;

import org.springframework.web.bind.annotation.RequestMethod;

public abstract class PostExecutor<K , V> extends BaseExecutor<K , V>
{

	protected final RequestMethod methodType = RequestMethod.POST;

}
