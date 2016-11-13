package com.ampdev.platform.module.user.session.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.*;

@Entity
@Table(name = "user_session")
public class UserSessionData extends PersistedDataObject
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3466432678923813606L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "session_id")
	private String sessionId;

	@Override
	public long getDataId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
}
