package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.user.constants.FacebookUserFriendConstant;

import javax.persistence.*;

@NamedQueries({ @NamedQuery(name = FacebookUserFriendConstant.QUERY_GET_USER_AND_FB_USER, query = "FROM FacebookUserFriendsData a where a.userName = :user_name AND a.friendUserName = :friend_user_name") })
@Entity
@Table(name = "fb_user_friends")
public class FacebookUserFriendsData extends PersistedDataObject
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2607837323650503433L;

	@Id
	@GeneratedValue
	@Column(name = FacebookUserFriendConstant.COLUMN_ID)
	private long id;

	@Column(name = FacebookUserFriendConstant.COLUMN_USER_NAME)
	private String userName;

	@Column(name = FacebookUserFriendConstant.COLUMN_FREIND_USER_NAME)
	private String friendUserName;

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

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getFriendUserName()
	{
		return friendUserName;
	}

	public void setFriendUserName(String friendUserName)
	{
		this.friendUserName = friendUserName;
	}

}
