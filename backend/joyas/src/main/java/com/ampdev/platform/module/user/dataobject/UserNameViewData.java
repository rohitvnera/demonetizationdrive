package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.Column;

public class UserNameViewData extends PersistedDataObject
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9043466812366078950L;

	@Column(name = "user_id")
	private long id;

	@Column(name = "user_name")
	private String userName;

	@Override
	public long getDataId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String movieName)
	{
		this.userName = movieName;
	}

}
