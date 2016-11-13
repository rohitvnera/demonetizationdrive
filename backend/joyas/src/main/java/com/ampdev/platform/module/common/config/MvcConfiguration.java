package com.ampdev.platform.module.common.config;

import com.ampdev.platform.framework.dataaccess.config.HibernateAwareObjectMapper;
import com.ampdev.platform.framework.rest.security.AuthenticationService;
import com.ampdev.platform.framework.rest.security.AuthenticationServiceDefault;
import com.ampdev.platform.module.common.dao.UuidGenerator;
import com.ampdev.platform.module.common.factory.AuthorizerFactory;
import com.ampdev.platform.module.common.util.FacebookUtil;
import com.ampdev.platform.module.user.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
@EnableWebMvc
@EnableTransactionManagement
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Autowired
    @Bean(name = "authenticationService")
    public AuthenticationService getAuthencticaionService() {
        return new AuthenticationServiceDefault();
    }

    @Autowired
    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Bean(name = "authorizerFactory")
    @Scope(value = "request")
    public AuthorizerFactory getAuthorizerFactory() {
        return new AuthorizerFactory();
    }

    @Autowired
    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper() {
        return new HibernateAwareObjectMapper();
    }

    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        // Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate4Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("Avinash configure Jackson Object Mapper");
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }

    @Autowired
    @Bean(name = "encrypyionUtil")
    public EncryptionUtil getEncryptionUtil(PasswordEncoder passwordEncoder)
    {
        return new EncryptionUtil(passwordEncoder);
    }
}
