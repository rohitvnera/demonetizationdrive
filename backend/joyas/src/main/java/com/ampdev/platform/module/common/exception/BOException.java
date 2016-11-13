package com.ampdev.platform.module.common.exception;

public class BOException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -739540367974528476L;

	public BOException()
	{
		super();
	}

	public BOException(Exception e)
	{
		super(e);
	}

	public BOException(String message)
	{
		super(message);
	}

	public BOException(String message, Exception e)
	{
		super(message, e);
	}
}
