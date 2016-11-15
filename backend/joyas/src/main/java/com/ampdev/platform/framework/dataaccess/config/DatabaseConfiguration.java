package com.ampdev.platform.framework.dataaccess.config;

import com.ampdev.platform.framework.dataaccess.DataAccessHibImpl;
import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.jolbox.bonecp.BoneCPDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.ampdev")
@EnableJpaRepositories(basePackages = "com.ampdev.platform.module")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean(name = "boneCPDataSource")
    public DataSource getBoneCPDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/findbank");
        dataSource.setUsername("root");
        dataSource.setPassword("ampdev");
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(240);
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(1);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);

        return dataSource;
    }

    private Properties getHibernateBoneCPProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("persistenceProviderClassName", "org.hibernate.ejb.HibernatePersistenceProvider");
        return properties;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource boneCPDataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(boneCPDataSource);
        sessionBuilder.addProperties(getHibernateBoneCPProperties());
        sessionBuilder.scanPackages("com.ampdev.platform.module");
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "dataAccess")
    public IDataAccess getDataAccess(SessionFactory sessionFactory) {
        DataAccessHibImpl dataAccess = new DataAccessHibImpl(sessionFactory);
        return dataAccess;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    /**
     * <beans:property name="persistenceXmlLocation" value="classpath:persistence.xml" />
     * <beans:property name="persistenceUnitName" value="blogTutorialPU" />
     *
     * @return
     */
    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(getBoneCPDataSource());
        entityManagerFactory.setJpaProperties(getHibernateBoneCPProperties());
        entityManagerFactory.setPackagesToScan("com.ampdev.platform.module");
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        //    entityManagerFactory.setPersistenceUnitName("ampdevPersistence");
        //  entityManagerFactory.setPersistenceXmlLocation("/persistence.xml");
        entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManagerFactory;
    }

    /**
     * <property name="generateDdl" value="true" />
     * <property name="showSql" value="false"/>
     * <property name="databasePlatform" value="org.hibernate.dialect.MySQLDialect"/>
     * <property name="database" value="MYSQL"/>
     *
     * @return
     */
    @Autowired
    @Bean(name = "jpaVendorAdapter")
    public HibernateJpaVendorAdapter getJpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("MYSQL");
        return jpaVendorAdapter;
    }

}
