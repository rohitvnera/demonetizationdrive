package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = UserDeviceData.QUERY_GET_USER_DEVICE_DATA, query = "from UserDeviceData u where u.userName = :user_name"),

	@NamedQuery(name = "getAllUserDeviceData", query = "from UserDeviceData u") })
@Entity
@Table(name = "user_data")
@JsonInclude(Include.NON_NULL)
public class UserDeviceData extends PersistedDataObject
{
	@JsonIgnore
	public static final String QUERY_GET_USER_DEVICE_DATA = "getUserDeviceData";

	@JsonIgnore
	public static final String QUERY_GET_ALL_USER_DEVICE_DATA = "getAllUserDeviceData";

	@JsonIgnore
	public static final String USER_ID = "id";
	@JsonIgnore
	public static final String USER_NAME = "user_name";
	@JsonIgnore
	public static final String USER_FULL_NAME = "user_full_name";
	@JsonIgnore
	public static final String USER_DEVICE_ID = "user_device_id";

	@JsonIgnore
	public static final String GCM_REG_ID = "gcm_reg_id";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162913240023306440L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_full_name")
	private String userFullName;

	@Column(name = "user_device_id")
	private String deviceId;

	@Column(name = GCM_REG_ID)
	private String gcmRegId;

	@Override
	public long getDataId()
	{
		return id;
	}

	public void setDataId(long id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserFullName()
	{
		return userFullName;
	}

	public void setUserFullName(String userFullName)
	{
		this.userFullName = userFullName;
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

	public UserDeviceData withUserName(String userName) {
		this.userName = userName;
		return this;
	}
}
