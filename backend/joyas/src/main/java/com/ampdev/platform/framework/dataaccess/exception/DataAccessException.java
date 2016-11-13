package com.ampdev.platform.framework.dataaccess.exception;

public class DataAccessException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4762806837180407094L;
	
	public DataAccessException(String message)
	{
		super(message);
	}
	public DataAccessException()
	{
		super();
	}
	public DataAccessException(Exception e)
	{
		super(e);
	}
	public DataAccessException(String message, Exception e)
	{
		super(message, e);
	}

}
