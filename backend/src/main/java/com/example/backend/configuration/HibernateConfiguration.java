package com.example.backend.configuration;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Bean("datasource")
  public DataSource getDatasource() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return dataSource;

  }

  @Bean("sessionFactory")
  public SessionFactory getSessionFactory() throws IOException {

    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setPackagesToScan("com.example.backend.entity");

    sessionFactoryBean.setHibernateProperties(getHibernateProperties());
    sessionFactoryBean.setDataSource(getDatasource());
    sessionFactoryBean.afterPropertiesSet();

    return sessionFactoryBean.getObject();
  }

  @Bean("transactionManager")
  public HibernateTransactionManager getTransactionManager() throws IOException {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(getSessionFactory());

    return transactionManager;
  }


  private static Properties getHibernateProperties() {

    Properties hibernateProperties = new Properties();
    hibernateProperties.put("hibernate.show_sql", true);

    return hibernateProperties;
  }
}
