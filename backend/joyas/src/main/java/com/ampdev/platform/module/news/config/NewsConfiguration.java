package com.ampdev.platform.module.news.config;

import com.ampdev.platform.module.news.INewsBO;
import com.ampdev.platform.module.news.NewsBO;
import com.ampdev.platform.module.news.authorizer.NewsAuthrozier;
import com.ampdev.platform.module.news.dao.INewsDao;
import com.ampdev.platform.module.news.dao.NewsDAO;
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
public class NewsConfiguration
{

	@Autowired
	@Bean(name = "newsBO")
	public INewsBO getNewsBO()
	{
		return new NewsBO();
	}

	@Autowired
	@Bean(name = "newsDAO")
	public INewsDao getNewsDAO()
	{
		return new NewsDAO();
	}

	@Autowired
	@Bean(name = "newsAuthrozier")
	@Scope(value = "request")
	public IAuthorizer getNewsAuthrozier()
	{
		return new NewsAuthrozier();
	}

}
