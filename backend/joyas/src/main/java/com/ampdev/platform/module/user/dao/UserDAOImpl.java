package com.ampdev.platform.module.user.dao;

import com.ampdev.platform.framework.dataaccess.CriteriaObject;
import com.ampdev.platform.framework.dataaccess.CriteriaObject.CRITERIA_TYPES;
import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.module.common.util.FacebookUtil;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.country.dataobject.CountryData;
import com.ampdev.platform.module.user.constants.FBUserConstants;
import com.ampdev.platform.module.user.constants.FacebookUserFriendConstant;
import com.ampdev.platform.module.user.constants.UserConstants;
import com.ampdev.platform.module.user.dataobject.*;
import com.ampdev.platform.module.user.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDAOImpl implements IUserDao
{

	@Autowired
	private IDataAccess dataAccess;

	@Autowired
	private EncryptionUtil encryptionUtil;

	@Override
	public List<UserDetailsData> getUserDetails(Collection<Long> userIds) throws DAOException
	{
		List<UserDetailsData> userDetails = null;

		try
		{
			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject criteria = new CriteriaObject("ids", CRITERIA_TYPES.COLLECTION, userIds);
			list.add(criteria);

			userDetails = dataAccess.getResults(UserConstants.QUERY_GET_USER_DETAILS, list);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is getting user Details", de);
		}
		return userDetails;
	}

	@Override
	public void addAddress(UserAddress userAddress) throws DAOException
	{
		try
		{
			dataAccess.create(userAddress);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is adding user Address", de);
		}
	}

	@Override
	public UserAddress getAddress(long userId) throws DAOException
	{
		UserAddress userAddress = null;
		try
		{
			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject criteria = new CriteriaObject("userId", CRITERIA_TYPES.LONG, userId);
			list.add(criteria);
			userAddress = dataAccess.getSingleResult(UserConstants.QUERY_GET_USER_ADDRESS, list);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is getting user Address", de);
		}
		return userAddress;
	}

	@Override
	public void deleteDetails(long userId) throws DAOException
	{
		try
		{
			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject criteria = new CriteriaObject("userId", CRITERIA_TYPES.LONG, userId);
			list.add(criteria);
			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_ADDRESS, list);

			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_DETAILS, list);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is deleting user Details", de);
		}

	}

	@Override
	public void deleteAddress(long userId) throws DAOException
	{
		try
		{
			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject criteria = new CriteriaObject("userId", CRITERIA_TYPES.LONG, userId);
			list.add(criteria);
			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_ADDRESS, list);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is deleting user Address", de);
		}
	}

	@Override
	public void deleteUser(long userId) throws DAOException
	{
		try
		{
			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject criteria = new CriteriaObject("userId", CRITERIA_TYPES.LONG, userId);
			list.add(criteria);
			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_ADDRESS, list);
			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_DETAILS, list);
			dataAccess.executeUpdate(UserConstants.QUERY_DELETE_USER_DATA, list);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is deleting user ", de);
		}
	}

	@Override
	public void updateUser(UserData userData) throws DAOException
	{
		try
		{
			if (userData != null)
				dataAccess.update(userData);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is updating user", de);
		}
	}

	@Override
	public void updateDetails(UserDetailsData userDetailsData) throws DAOException
	{
		try
		{
			if (userDetailsData != null)
				dataAccess.create(userDetailsData);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is updating user Details", de);
		}
	}

	@Override
	public void updateAddress(UserAddress userAddress) throws DAOException
	{
		try
		{
			if (userAddress != null)
				dataAccess.update(userAddress);
		}
		catch (DataAccessException de)
		{
			throw new DAOException("Error is updating user Address", de);
		}
	}

	@Override
	public void createUser(UserDetailsData userDetails) throws DAOException
	{

		/*
		 * Save UserData and get id
		 */
		UserData userData = new UserData(userDetails.getUserName(), encryptionUtil.encodePassword(userDetails.getPassword()));
		try
		{
			dataAccess.create("user", userData);
			userDetails.setId(userData.getDataId());
		}
		catch (DataAccessException e)
		{
			throw new DAOException("Error creating user", e);
		}
	}

	@Override
	public UserData getUserData(String userName)
	{
		if (Util.isEmpty(userName))
		{
			return null;
		}

		List<CriteriaObject> list = new ArrayList<CriteriaObject>();
		CriteriaObject criteria = new CriteriaObject("userName", CRITERIA_TYPES.STRING, userName);
		list.add(criteria);
		UserData persistedUserData = null;
		try
		{
			persistedUserData = (UserData) dataAccess.getSingleResult(UserConstants.QUERY_GET_USER_DATA, list);
		}
		catch (DataAccessException e)
		{
			e.printStackTrace();
		}
		return persistedUserData;
	}

	@Override
	public void createUpdateFBUser(FBUserData userData) throws DAOException
	{
		if (userData != null)
		{
			FBUserData existingUserData = getFBUserData(userData.getUserName());
			if (existingUserData != null)
			{
				userData.setId(existingUserData.getDataId());
				try
				{
					dataAccess.update(userData);

				}
				catch (DataAccessException e)
				{

					e.printStackTrace();
					throw new DAOException("Error in updating FB user data", e);
				}
			}
			else
			{
				try
				{
					dataAccess.create(userData);

				}
				catch (DataAccessException e)
				{
					e.printStackTrace();
					throw new DAOException("Error in creating FB user data", e);
				}
			}

			UserDeviceData userDeviceData = new UserDeviceData();
			if (!Util.isEmpty(userData.getDeviceId()) || !Util.isEmpty(userData.getGcmRegId()))
			{
				UserDeviceData existinDeviceData = getUserDeviceData(userData.getUserName());

				if (!Util.isEmpty(userData.getDeviceId()))
					userDeviceData.setDeviceId(userData.getDeviceId());

				if (!Util.isEmpty(userData.getGcmRegId()))
					userDeviceData.setGcmRegId(userData.getGcmRegId());

				userDeviceData.setUserName(userData.getUserName());

				try
				{
					if (existinDeviceData == null)
					{
						dataAccess.create(userDeviceData);
					}
					else
					{
						userDeviceData.setDataId(existinDeviceData.getDataId());
						dataAccess.update(userDeviceData);
					}
				}
				catch (DataAccessException e)
				{
					throw new DAOException();
				}
			}
		}
	}

	@Override
	public FBUserData getFBUserData(String userName) throws DAOException

	{
		if (Util.isEmpty(userName))
		{
			return null;
		}

		List<CriteriaObject> list = new ArrayList<CriteriaObject>();
		CriteriaObject criteria = new CriteriaObject(FBUserConstants.USER_NAME, CRITERIA_TYPES.STRING, userName);
		list.add(criteria);
		FBUserData persistedUserData = null;
		try
		{
			persistedUserData = (FBUserData) dataAccess.getSingleResult(UserConstants.QUERY_GET_FB_USER_DATA, list);
		}
		catch (DataAccessException e)
		{
			e.printStackTrace();
		}
		return persistedUserData;
	}

	public UserDeviceData getUserDeviceData(String userName) throws DAOException

	{
		if (Util.isEmpty(userName))
		{
			return null;
		}

		List<CriteriaObject> list = new ArrayList<CriteriaObject>();
		CriteriaObject criteria = new CriteriaObject(UserDeviceData.USER_NAME, CRITERIA_TYPES.STRING, userName);
		list.add(criteria);
		UserDeviceData persistedUserData = null;
		try
		{
			persistedUserData = (UserDeviceData) dataAccess.getSingleResult(UserDeviceData.QUERY_GET_USER_DEVICE_DATA, list);
		}
		catch (DataAccessException e)
		{
			e.printStackTrace();
		}
		return persistedUserData;
	}

	@Override
	public List<FBUserData> getAllUsers() throws DAOException
	{
		List<FBUserData> fbUsers = null;

		String query = UserConstants.QUERY_GET_ALL_FB_USERS;
		try
		{
			fbUsers = dataAccess.getResults(query, null);
			return fbUsers;
		}
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DAOException("Error while getAllFbUsers", e);
		}
	}

	@Override
	public void saveFacebookFriends(FBUserData fbUser, Collection<String> friends) throws DAOException
	{
		if (fbUser == null || Util.isEmpty(fbUser.getUserName()) || Util.isEmpty(friends))
		{
			return;
		}

		for (String friend : friends)
		{

			List<CriteriaObject> list = new ArrayList<CriteriaObject>();
			CriteriaObject userNameCriteria = new CriteriaObject(FacebookUserFriendConstant.COLUMN_USER_NAME,
				CRITERIA_TYPES.STRING, fbUser.getUserName());
			list.add(userNameCriteria);
			CriteriaObject friendUserNameCriteria = new CriteriaObject(FacebookUserFriendConstant.COLUMN_FREIND_USER_NAME,
				CRITERIA_TYPES.STRING, friend);
			list.add(friendUserNameCriteria);
			try
			{
				FacebookUserFriendsData existingUserFriendData = dataAccess.getSingleResult(
					FacebookUserFriendConstant.QUERY_GET_USER_AND_FB_USER, list);

				if (existingUserFriendData == null)
				{
					FacebookUserFriendsData fbFriendData = new FacebookUserFriendsData();
					fbFriendData.setUserName(fbUser.getUserName());
					fbFriendData.setFriendUserName(friend);
					dataAccess.create(fbFriendData);
				}
			}
			catch (DataAccessException e)
			{
				e.printStackTrace();
				throw new DAOException();
			}
		}

	}
}
