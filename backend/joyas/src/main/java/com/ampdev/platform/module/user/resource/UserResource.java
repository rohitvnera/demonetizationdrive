package com.ampdev.platform.module.user.resource;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.rest.BaseExecutor;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.user.constants.UserConstants;
import com.ampdev.platform.module.user.dataobject.UserAddress;
import com.ampdev.platform.module.user.dataobject.UserData;
import com.ampdev.platform.module.user.dataobject.UserDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/ws/user")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserResource extends RestBaseResource
{
	@Autowired
	private BaseExecutor<String, List<UserDetailsData>> getUserExecutor;

	@Autowired
	private BaseExecutor<UserAddress, UserAddress> addUserAddressExecutor;

	@Autowired
	private BaseExecutor<String, UserAddress> searchAddressExecutor;

	@Autowired
	private BaseExecutor<String, String> deleteAddressExecutor;

	@Autowired
	private BaseExecutor<String, String> deleteUserDetailsExecutor;

	@Autowired
	private BaseExecutor<String, String> deleteUserExecutor;

	@Autowired
	private BaseExecutor<UserData, UserData> updateUserExecutor;

	@Autowired
	private BaseExecutor<UserDetailsData, UserDetailsData> updateUserDetailsExecutor;

	@Autowired
	private BaseExecutor<UserAddress, UserAddress> updateUserAddressExecutor;

	@RequestMapping(value = UserConstants.GET_USER, method = RequestMethod.GET)
	public ResponseEntity<List<UserDetailsData>> getUser(@PathVariable(value = "ids") String ids) throws DAOException
	{
		getUserExecutor.setAttribute(UserConstants.USER_IDS, ids);
		return performTask(Module.USER,null, getUserExecutor);
	}

	@RequestMapping(value = UserConstants.ADD_ADDRESS, method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<UserAddress> addAddress(RequestEntity<UserAddress> requestEntity) throws DAOException
	{

		return performTask(Module.USER,requestEntity, addUserAddressExecutor);
	}

	@RequestMapping(value = UserConstants.SEARCH_ADDRESS, method = RequestMethod.GET)
	public ResponseEntity<?> searchAddress(@RequestParam(value = "userId") long userId) throws DAOException
	{
		searchAddressExecutor.setAttribute(UserConstants.USER_ID, userId);
		return performTask(Module.USER,null, searchAddressExecutor);

	}

	@RequestMapping(value = UserConstants.DELETE_USER, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String userId) throws NumberFormatException, DAOException
	{
		deleteUserExecutor.setAttribute(UserConstants.USER_ID, userId);
		return performTask(Module.USER,null, deleteUserExecutor);

	}

	@RequestMapping(value = UserConstants.DELETE_DETAILS, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDetails(@PathVariable(value = "id") String userId) throws NumberFormatException,
			DAOException
	{
		deleteUserDetailsExecutor.setAttribute(UserConstants.USER_ID, userId);
		return performTask(Module.USER,null, deleteUserDetailsExecutor);

	}

	@RequestMapping(value = UserConstants.DELETE_ADDRESS, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAddress(@PathVariable(value = "id") String userId) throws NumberFormatException,
			DAOException
	{
		deleteAddressExecutor.setAttribute(UserConstants.USER_ID, userId);
		return performTask(Module.USER,null, deleteAddressExecutor);

	}

	@RequestMapping(value = UserConstants.UPDATE_USER, method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(RequestEntity<UserData> requestEntity) throws DAOException
	{
		return performTask(Module.USER,requestEntity, updateUserExecutor);
	}

	@RequestMapping(value = UserConstants.UPDATE_DETAILS, method = RequestMethod.PUT)
	public ResponseEntity<?> updateDetails(RequestEntity<UserDetailsData> requestEntity) throws DAOException
	{
		return performTask(Module.USER,requestEntity, updateUserDetailsExecutor);
	}

	@RequestMapping(value = UserConstants.UPDATE_ADDRESS, method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddress(RequestEntity<UserAddress> requestEntity) throws DAOException
	{
		return performTask(Module.USER,requestEntity, updateUserAddressExecutor);

	}

	@RequestMapping(value = "/ampdev/test", method = RequestMethod.GET)
	@ResponseBody
	public String ampdevTest()
	{
		return "AMPDEVS ROCK";
	}
}
