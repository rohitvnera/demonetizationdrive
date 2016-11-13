package com.ampdev.platform.module.country.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.module.country.CountryBO;
import com.ampdev.platform.module.country.ICountryBO;
import com.ampdev.platform.module.country.authorizer.CountryAuthrozier;
import com.ampdev.platform.module.country.dao.CountryDAO;
import com.ampdev.platform.module.country.dao.ICountryDao;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
public class CountryConfiguration
{

	@Autowired
	@Bean(name = "countryBO")
	public ICountryBO getCountryBO()
	{
		return new CountryBO();
	}

	@Autowired
	@Bean(name = "countryDAO")
	public ICountryDao getCountryDAO()
	{
		return new CountryDAO();
	}

	@Autowired
	@Bean(name = "countryAuthrozier")
	@Scope(value = "request")
	public IAuthorizer getCountryAuthrozier()
	{
		return new CountryAuthrozier();
	}

}
