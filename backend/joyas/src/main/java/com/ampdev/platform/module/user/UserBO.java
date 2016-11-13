package com.ampdev.platform.module.user;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;
import com.ampdev.platform.module.common.exception.BOException;
import com.ampdev.platform.module.user.dao.IUserDao;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import com.ampdev.platform.module.user.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public final class UserBO implements IUserBO
{

	@Autowired
	private IUserDao userDAO;

	@Autowired
	private EncryptionUtil encryptionUtil;

	@Override
	public void createUser(UserDetailsData userDetails) throws BOException
	{
		try
		{
			userDAO.createUser(userDetails);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException();
		}

	}

	@Override
	public boolean authenticate(UserData userData) throws BOException
	{
		if (userData == null)
		{
			return false;
		}

		UserData dbData = userDAO.getUserData(userData.getUserName());
		String persistedPassword = dbData.getPassword();
		boolean isAuthnecticated = encryptionUtil.matchPassword(userData.getPassword(), persistedPassword);

		return isAuthnecticated;
	}

	@Override
	public void logout(String userName, String token)
	{
		TokenManagerImpl.getTokenManager().removeToken(token);

	}

	@Override
	public List<UserDetailsData> getUserDetails(Collection<Long> userIds) throws BOException
	{
		try
		{
			return userDAO.getUserDetails(userIds);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void updateUserDetails(UserDetailsData userDetailsData) throws BOException
	{
		try
		{
			userDAO.updateDetails(userDetailsData);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void addAddress(UserAddress userAddress) throws BOException
	{
		try
		{
			userDAO.addAddress(userAddress);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public UserAddress getAddress(long userId) throws BOException
	{
		try
		{
			return userDAO.getAddress(userId);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void updateUser(UserData userData) throws BOException
	{
		try
		{
			userDAO.updateUser(userData);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}

	}

	@Override
	public void updateAddress(UserAddress userAddress) throws BOException
	{
		try
		{
			userDAO.updateAddress(userAddress);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void deleteDetails(long userId) throws BOException
	{
		try
		{
			userDAO.deleteDetails(userId);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void deleteAddress(long userId) throws BOException
	{
		try
		{
			userDAO.deleteAddress(userId);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void deleteUser(long userId) throws BOException
	{
		try
		{
			userDAO.deleteUser(userId);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public void createUpdateFBUser(FBUserData userData) throws BOException
	{
		try
		{
			userDAO.createUpdateFBUser(userData);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException();
		}
	}

	@Override
	public List<FBUserData> getAllUsers() throws BOException
	{
		try
		{
			return userDAO.getAllUsers();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException("Error getting fb users", e);
		}
	}

	@Override
	public void saveFacebookFriends(FBUserData fbUser, Collection<String> friends) throws BOException
	{

		try
		{
			userDAO.saveFacebookFriends(fbUser, friends);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException("Error getting fb users", e);
		}

	}

}
