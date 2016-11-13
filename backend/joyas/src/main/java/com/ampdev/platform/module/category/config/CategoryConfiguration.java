package com.ampdev.platform.module.category.config;

import com.ampdev.platform.module.category.CategoryBO;
import com.ampdev.platform.module.category.ICategoryBO;
import com.ampdev.platform.module.category.authorizer.CategoryAuthrozier;
import com.ampdev.platform.module.category.dao.CategoryDAO;
import com.ampdev.platform.module.category.dao.ICategoryDao;
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
public class CategoryConfiguration
{

	@Autowired
	@Bean(name = "categoryBO")
	public ICategoryBO getCategoryBO()
	{
		return new CategoryBO();
	}

	@Autowired
	@Bean(name = "categoryDAO")
	public ICategoryDao getCategoryDAO()
	{
		return new CategoryDAO();
	}

	@Autowired
	@Bean(name = "categoryAuthrozier")
	@Scope(value = "request")
	public IAuthorizer getCategoryAuthrozier()
	{
		return new CategoryAuthrozier();
	}

}
