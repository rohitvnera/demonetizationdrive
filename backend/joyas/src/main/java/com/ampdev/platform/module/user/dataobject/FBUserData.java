package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.user.constants.FBUserConstants;
import com.ampdev.platform.module.user.constants.UserConstants;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = UserConstants.QUERY_GET_FB_USER_DATA, query = "from FBUserData u where u.userName = :user_name "),
	@NamedQuery(name = UserConstants.QUERY_GET_ALL_FB_USERS, query = "from FBUserData") })
@Entity
@Table(name = "ampdev_fb_users")
public class FBUserData extends PersistedDataObject
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2756976680492528399L;

	@Id
	@GeneratedValue
	@Column(name = FBUserConstants.USER_ID)
	private long id;

	@Column(name = FBUserConstants.USER_NAME)
	private String userName;

	@Column(name = FBUserConstants.ACCESS_TOKEN)
	private String accessToken;

	@Transient
	private String deviceId;

	@Transient
	private String gcmRegId;

	@Override
	public long getDataId()
	{
		return id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getGcmRegId()
	{
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId)
	{
		this.gcmRegId = gcmRegId;
	}

}
