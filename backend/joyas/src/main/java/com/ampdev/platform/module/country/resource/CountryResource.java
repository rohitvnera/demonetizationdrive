package com.ampdev.platform.module.country.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.module.common.api.GenericCreateExecutor;
import com.ampdev.platform.module.common.api.GenericDeleteExecutor;
import com.ampdev.platform.module.common.api.GenericGetExecutor;
import com.ampdev.platform.module.common.api.GenericUpdateExecutor;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.constants.URIConstants;
import com.ampdev.platform.module.country.dataobject.CountryData;

@Controller
@RequestMapping(value = "/ws/country")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CountryResource extends RestBaseResource
{

	@Autowired
	private GenericCreateExecutor<CountryData> createExecutor;

	@Autowired
	private GenericUpdateExecutor<CountryData> updateExecutor;

	@Autowired
	private GenericGetExecutor<CountryData> getExecutor;

	@Autowired
	private GenericDeleteExecutor deleteExecutor;

	@RequestMapping(value = URIConstants.GET_ALL, method = RequestMethod.GET)
	public ResponseEntity<?> getAllCountry()
	{
		return performTask(Module.COUNTRY, null, getExecutor);
	}

	@RequestMapping(value = URIConstants.GET_IDS, method = RequestMethod.GET)
	public ResponseEntity<?> getCountryData(@PathVariable(value = "ids") String countryIds)
	{
		getExecutor.setAttribute(URIConstants.IDS, countryIds);
		return performTask(Module.COUNTRY, null, getExecutor);
	}

	@RequestMapping(value = URIConstants.ADD, method = RequestMethod.POST)
	public ResponseEntity<?> addCountry(RequestEntity<CountryData> requestEntity)
	{
		return performTask(Module.COUNTRY, requestEntity, createExecutor);
	}

	@RequestMapping(value = URIConstants.UPDATE, method = RequestMethod.PUT)
	public ResponseEntity<?> updateCountry(RequestEntity<CountryData> requestEntity)
	{
		return performTask(Module.COUNTRY, requestEntity, updateExecutor);
	}

	@RequestMapping(value = URIConstants.DELETE_ID, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCountry(@PathVariable(value = "id") String countryId)
	{
		deleteExecutor.setAttribute(URIConstants.ID, countryId);
		return performTask(Module.COUNTRY, null, deleteExecutor);
	}
}
