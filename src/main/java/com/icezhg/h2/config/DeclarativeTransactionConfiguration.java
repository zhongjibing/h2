package com.icezhg.h2.config;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * DataSourceConfiguration:
 *
 * @author zhongjibing 2017-10-08
 * @version 1.0
 */
@Configuration
public class DeclarativeTransactionConfiguration {

    @Bean
    public TransactionAttributeSource declarativeTransactionAttributeSource() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        Properties transactionAttributes = new Properties();

        // Format is PROPAGATION_NAME,ISOLATION_NAME,readOnly,timeout_NNNN,+Exception1,-Exception2.
        transactionAttributes.setProperty("add*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("create*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionAttributes.setProperty("remove*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");

        transactionAttributes.setProperty("get*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");
        transactionAttributes.setProperty("find*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");
        transactionAttributes.setProperty("query*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");
        transactionAttributes.setProperty("select*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");

        transactionAttributes.setProperty("*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT");

        source.setProperties(transactionAttributes);
        return source;
    }

    @Bean
    public TransactionInterceptor declarativeTransactionInterceptor(PlatformTransactionManager transactionManager) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSource(declarativeTransactionAttributeSource());
        return transactionInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(true);
        transactionAutoProxy.setBeanNames("*Service");
        transactionAutoProxy.setInterceptorNames("declarativeTransactionInterceptor");
        return transactionAutoProxy;
    }
}
