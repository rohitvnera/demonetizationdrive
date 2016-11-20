package com.ampdev.platform.module.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;

@Controller
@RequestMapping(value = "/ws/service")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceResource
{

	ServiceManager manager = null;

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String start()
	{
		Set<Service> services = new HashSet<>();
		manager = new ServiceManager(services);
		manager.startAsync();

		return "Start";
	}

	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public String stop()
	{
		if (manager != null)
		{
			manager.stopAsync();
		}
		return "stop";
	}
}
