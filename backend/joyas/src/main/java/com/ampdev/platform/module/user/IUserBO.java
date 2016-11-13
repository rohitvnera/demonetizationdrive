package com.ampdev.platform.module.user;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;

import java.util.Collection;
import java.util.List;

public interface IUserBO
{
	/**
	 * Create user
	 * 
	 * @param userDetails
	 * 
	 * @throws DAOException
	 */
	public void createUser(UserDetailsData userDetails) throws BOException;

	/**
	 * Authenicate user
	 * 
	 * @param userData
	 * @return
	 * @throws DAOException
	 */
	public boolean authenticate(UserData userData) throws BOException;

	/**
	 * 
	 * @param userName
	 * @param token
	 */
	public void logout(String userName, String token) throws BOException;

	public List<UserDetailsData> getUserDetails(Collection<Long> userIds) throws BOException;

	public void updateUserDetails(UserDetailsData userDetailsData) throws BOException;

	public void addAddress(UserAddress userAddress) throws BOException;

	public UserAddress getAddress(long userId) throws BOException;

	public void updateUser(UserData userData) throws BOException;

	public void updateAddress(UserAddress userAddress) throws BOException;

	public void deleteDetails(long userId) throws BOException;

	public void deleteAddress(long userId) throws BOException;

	public void deleteUser(long userId) throws BOException;

	public void createUpdateFBUser(FBUserData userData) throws BOException;

	public List<FBUserData> getAllUsers() throws BOException;

	public void saveFacebookFriends(FBUserData fbUser, Collection<String> friends) throws BOException;
}
