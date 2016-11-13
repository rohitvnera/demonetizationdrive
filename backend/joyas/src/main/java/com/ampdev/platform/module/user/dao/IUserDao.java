package com.ampdev.platform.module.user.dao;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;

import java.util.Collection;
import java.util.List;

public interface IUserDao
{
	/**
	 * Create user
	 * 
	 * @param userDetails
	 * 
	 * @throws DAOException
	 */
	public void createUser(UserDetailsData userDetails) throws DAOException;

	public UserData getUserData(String userName);

	public List<UserDetailsData> getUserDetails(Collection<Long> userIds) throws DAOException;

	public void updateDetails(UserDetailsData userDetailsData) throws DAOException;

	public void addAddress(UserAddress userAddress) throws DAOException;

	public UserAddress getAddress(long userId) throws DAOException;

	public void updateUser(UserData userData) throws DAOException;

	public void updateAddress(UserAddress userAddress) throws DAOException;

	public void deleteDetails(long userId) throws DAOException;

	public void deleteAddress(long userId) throws DAOException;

	public void deleteUser(long userId) throws DAOException;

	public void createUpdateFBUser(FBUserData userData) throws DAOException;

	public FBUserData getFBUserData(String userName) throws DAOException;

	public List<FBUserData> getAllUsers() throws DAOException;

	public void saveFacebookFriends(FBUserData fbUser, Collection<String> friends) throws DAOException;

}
