package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@NamedQueries({ @NamedQuery(name = "getUserData", query = "from UserData u where u.userName = :userName "),
	@NamedQuery(name = "deleteUser", query = "delete from UserData where id= :userId") })
@Entity
@Table(name = "USERS")
@JsonInclude(Include.NON_NULL)
public class UserData extends PersistedDataObject
{

	public UserData()
	{

	}

	public UserData(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1804456237025759839L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setUser(UserData userData)
	{
		if (userData.getUserName() != null)
		{
			setUserName(userData.getUserName());
		}

		if (userData.getPassword() != null)
		{
			setPassword(userData.getPassword());
		}
	}

}
