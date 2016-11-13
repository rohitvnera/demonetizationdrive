package com.ampdev.platform.module.findbank.config;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.module.findbank.authorizer.BankAuthorizer;
import com.ampdev.platform.module.findbank.bo.BankBO;
import com.ampdev.platform.module.findbank.bo.IBankBO;
import com.ampdev.platform.module.findbank.dao.BankDO;
import com.ampdev.platform.module.findbank.dao.IBankDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
public class BankConfiguration {

    @Autowired
    @Bean(name = "bankBO")
    public IBankBO getBankBO() {
        return new BankBO();
    }

    @Autowired
    @Bean(name = "bankDO")
    public IBankDO getBankDO() {
        return new BankDO();
    }

    @Autowired
    @Bean(name = "bankAuthrozier")
    @Scope(value = "request")
    public IAuthorizer getBankAuthrozier() {
        return new BankAuthorizer();
    }

}
