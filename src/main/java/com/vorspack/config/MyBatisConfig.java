package com.vorspack.config;

import com.vorspack.repository.ProductRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory( DataSource eDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(eDataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }


    //单独的获取一个Mapper
    //需要设置一个SqlSessionFactory
    //以及一个Mapper接口
    @Bean
    public MapperFactoryBean<ProductRepository> employeeMapper(SqlSessionFactory sqlSessionFactory){
        MapperFactoryBean<ProductRepository> mapperFactoryBean=new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(ProductRepository.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean;
    }

    //通过扫描的方式设置mapper
    //需要设置一个SqlSessionFactory
    //一个扫描的基础包
    //一个过滤方式
    @Bean
    @Primary
    public MapperScannerConfigurer mapperScanner(){
        MapperScannerConfigurer scannerConfigurer=new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        scannerConfigurer.setAnnotationClass(Repository.class);
        scannerConfigurer.setBasePackage("com.vorspack.repository");
        return scannerConfigurer;
    }


}
