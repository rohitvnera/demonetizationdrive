package com.ampdev.platform.framework.dataaccess;

public class CriteriaObject
{

	public enum CRITERIA_TYPES
	{
		LONG, INT, STRING, COLLECTION, LIMIT, DATE, OFFSET
	};

	private String objectName;

	private CRITERIA_TYPES objectType;

	private Object objectValue;

	public CriteriaObject(String objectName, CRITERIA_TYPES objectType, Object objectValue)
	{
		this.objectName = objectName;
		this.objectType = objectType;
		this.objectValue = objectValue;
	}

	public String getObjectName()
	{
		return objectName;
	}

	public void setObjectName(String objectName)
	{
		this.objectName = objectName;
	}

	public CRITERIA_TYPES getObjectType()
	{
		return objectType;
	}

	public void setObjectType(CRITERIA_TYPES objectType)
	{
		this.objectType = objectType;
	}

	public Object getObjectValue()
	{
		return objectValue;
	}

	public void setObjectValue(Object objectValue)
	{
		this.objectValue = objectValue;
	}

}
