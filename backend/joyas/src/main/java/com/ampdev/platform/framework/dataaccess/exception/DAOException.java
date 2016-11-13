package com.ampdev.platform.framework.dataaccess.exception;

public class DAOException extends Exception
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5678155484660337670L;
	
	public DAOException()
	{
		super();
	}
	public DAOException(String message)
	{
		super(message);
	}
	public DAOException(String message, Exception e)
	{
		super(message, e);
	}

}
