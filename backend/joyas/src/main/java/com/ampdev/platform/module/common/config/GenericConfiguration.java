package com.ampdev.platform.module.common.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ampdev.platform.module.common.api.GenericCreateExecutor;
import com.ampdev.platform.module.common.api.GenericDeleteExecutor;
import com.ampdev.platform.module.common.api.GenericGetExecutor;
import com.ampdev.platform.module.common.api.GenericUpdateExecutor;
import com.ampdev.platform.module.common.bo.GenericBO;
import com.ampdev.platform.module.common.bo.IGenericBO;
import com.ampdev.platform.module.common.dao.GenericDAO;
import com.ampdev.platform.module.common.dao.IGenericDAO;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
public class GenericConfiguration
{

	@Autowired
	@Bean(name = "genericBO")
	public IGenericBO getGenericBO()
	{
		return new GenericBO();
	}

	@Autowired
	@Bean(name = "genericDAO")
	public IGenericDAO getGenericDAO()
	{
		return new GenericDAO();
	}

	@Autowired
	@Bean(name = "createExecutor")
	@Scope(value = "request")
	public <V extends PersistedDataObject> GenericCreateExecutor<V> getCreateExecutor()
	{
		return new GenericCreateExecutor<V>();
	}

	@Autowired
	@Bean(name = "getExecutor")
	@Scope(value = "request")
	public <V extends PersistedDataObject> GenericGetExecutor<V> getExecutor()
	{
		return new GenericGetExecutor<V>();
	}

	@Autowired
	@Bean(name = "updateExecutor")
	@Scope(value = "request")
	public <V extends PersistedDataObject> GenericUpdateExecutor<V> getUpdateExecutor()
	{
		return new GenericUpdateExecutor<V>();
	}

	@Autowired
	@Bean(name = "deleteExecutor")
	@Scope(value = "request")
	public GenericDeleteExecutor getDeleteExecutor()
	{
		return new GenericDeleteExecutor();
	}

	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper dozerBean() {
		List<String> mappingFiles = Arrays.asList("dozer-mappings.xml");
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}
}
