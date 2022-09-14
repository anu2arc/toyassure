package com.increff.spring;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
public class DbConfig {

	public static final String PACKAGE_POJO = "com.increff.pojo";

	@Value("${jdbc.driverClassName}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddl;

	@Value(("${hibernate.physical_naming_strategy}"))
	private String hibernatephysicalnamingstrategy;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		System.out.println("jdbcDriver: " + jdbcDriver + ", jdbcUrl: " + jdbcUrl + ", jdbcUsername: " + jdbcUsername);
		BasicDataSource bean = new BasicDataSource();
		bean.setDriverClassName(jdbcDriver);
		bean.setUrl(jdbcUrl);
		bean.setUsername(jdbcUsername);
		bean.setPassword(jdbcPassword);
		bean.setInitialSize(2);
		bean.setDefaultAutoCommit(false);
		//bean.setMaxTotal(10);
		bean.setMinIdle(2);
		bean.setValidationQuery("Select 1");
		bean.setTestWhileIdle(true);
		bean.setTimeBetweenEvictionRunsMillis(10 * 60 * 100);
		return bean;
	}

	@Bean(name = "entityManagerFactory")
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		//logger.info("hibernateDialect: " + jdbcDriver + ", hibernateHbm2ddl: " + hibernateHbm2ddl);
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setPackagesToScan(PACKAGE_POJO);
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(jpaAdapter);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", hibernateDialect);
		jpaProperties.put("hibernate.show_sql", hibernateShowSql);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
		jpaProperties.put("hibernate.physical_naming_strategy",hibernatephysicalnamingstrategy);
		bean.setJpaProperties(jpaProperties);
		return bean;
	}

	@Bean(name = "transactionManager")
	@Autowired
	public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
		JpaTransactionManager bean = new JpaTransactionManager();
		bean.setEntityManagerFactory(emf.getObject());
		return bean;
	}
}
