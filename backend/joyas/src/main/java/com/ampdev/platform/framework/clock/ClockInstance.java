package com.ampdev.platform.framework.clock;

public class ClockInstance
{

	private static ClockInstance instance;

	private ClockInstance()
	{

	}

	public static ClockInstance getInstance()
	{
		if (instance == null)
		{
			synchronized (ClockInstance.class)
			{
				if (instance == null)
				{
					instance = new ClockInstance();
				}
			}
		}
		return instance;
	}

	public long getCurrentTimeMillis()
	{
		return System.currentTimeMillis();
	}
}
