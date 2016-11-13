package com.ampdev.platform.module.user.config;

import com.ampdev.platform.framework.rest.BaseExecutor;
import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.module.user.IUserBO;
import com.ampdev.platform.module.user.UserBO;
import com.ampdev.platform.module.user.api.*;
import com.ampdev.platform.module.user.authorizer.UserAuthorizer;
import com.ampdev.platform.module.user.dao.IUserDao;
import com.ampdev.platform.module.user.dao.UserDAOImpl;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
public class UserConfiguration
{

	@Autowired
	@Bean(name = "userDAO")
	public IUserDao getUserDao()
	{
		return new UserDAOImpl();
	}

	@Autowired
	@Bean(name = "userBO")
	public IUserBO getUserBO()
	{
		return new UserBO();
	}

	@Autowired
	@Bean(name = "userAuthorizer")
	@Scope(value = "request")
	public IAuthorizer getUserAuthorizer()
	{
		return new UserAuthorizer();
	}

	@Autowired
	@Bean(name = "createUserExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserDetailsData, UserDetailsData> getCreateUserExecutor()
	{
		return new CreateUserExecutor();
	}

	@Autowired
	@Bean(name = "logoutUserExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserData, String> getLogoutUserExector()
	{
		return new LogoutUserExecutor();
	}

	@Autowired
	@Bean(name = "loginExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserData, String> getLoginExecutor()
	{
		return new LoginExecutor();
	}

	@Autowired
	@Bean(name = "getUserExecutor")
	@Scope(value = "request")
	public BaseExecutor<String, List<UserDetailsData>> getUserExecutor()
	{
		return new GetUseDetailsExecutor();
	}

	@Autowired
	@Bean(name = "addUserAddressExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserAddress, UserAddress> addUserAddress()
	{
		return new AddUserAddressExecutor();
	}

	@Autowired
	@Bean(name = "searchAddressExecutor")
	@Scope(value = "request")
	public BaseExecutor<String, UserAddress> getSearchAddressExecutor()
	{
		return new SearchAddressExecutor();
	}

	@Autowired
	@Bean(name = "deleteAddressExecutor")
	@Scope(value = "request")
	public BaseExecutor<String, String> getDeleteAddressExecutor()
	{
		return new DeleteAddressExecutor();
	}

	@Autowired
	@Bean(name = "deleteUserDetailsExecutor")
	@Scope(value = "request")
	public BaseExecutor<String, String> getDeleteUserDetailsExecutor()
	{
		return new DeleteUserDetailsExecutor();
	}

	@Autowired
	@Bean(name = "deleteUserExecutor")
	@Scope(value = "request")
	public BaseExecutor<String, String> getDeleteUserExecutor()
	{
		return new DeleteUserExecutor();
	}

	@Autowired
	@Bean(name = "updateUserExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserData, UserData> getUpdateUserExecutor()
	{
		return new UpdateUserExecutor();
	}

	@Autowired
	@Bean(name = "updateUserDetailsExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserDetailsData, UserDetailsData> getUpdateUserDetailsExecutor()
	{
		return new UpdateUserDetailsExecutor();
	}

	@Autowired
	@Bean(name = "updateUserAddressExecutor")
	@Scope(value = "request")
	public BaseExecutor<UserAddress, UserAddress> getUpdateUserAddressExecutor()
	{
		return new UpdateUserAddressExecutor();
	}

	@Autowired
	@Bean(name = "fbUserLoginExecutor")
	@Scope(value = "request")
	public BaseExecutor<FBUserData, String> getFBUserLoginExecutor()
	{
		return new FBUserLoginExecutor();
	}

	@Autowired
	@Bean(name = "fbUserCreateUpdateExecutor")
	@Scope(value = "request")
	public BaseExecutor<FBUserData, FBUserData> getFBUserCreateUpdateExecutor()
	{
		return new FBUserCreateUpdateExecutor();
	}

}
