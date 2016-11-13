package com.ampdev.platform.module.tictactoe.config;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.module.tictactoe.authorizer.TicTacToeAuthrozier;
import com.ampdev.platform.module.tictactoe.dao.ITicTacToeDAO;
import com.ampdev.platform.module.tictactoe.dao.TicTacDoeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
public class TicTacToeConfiguration
{

	@Autowired
	@Bean(name = "ticTacDAO")
	public ITicTacToeDAO getCountryDAO()
	{
		return new TicTacDoeDAO();
	}

	@Autowired
	@Bean(name = "ticTacToeAuthrozier")
	@Scope(value = "request")
	public IAuthorizer getTicTacToeAuthrizer()
	{
		return new TicTacToeAuthrozier();
	}

}
