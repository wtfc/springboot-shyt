package com;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.jc.system.SystemListener;
import com.jc.system.common.util.SpringContextHolder;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@SpringBootApplication(scanBasePackages = {"com.jc.**.service","com.jc.**.facade","com.jc.**.dao","com.jc.**.web"})
@SpringBootApplication
//@ServletComponentScan(basePackages = "com.jc.*")
//@Configuration("config/applicationContext-*.xml")
public class WangApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangApplication.class, args);
	}
	
	
	

	/**
	 * 注册listener
	 */
//	@Bean
//	public void setApplicationContext(ApplicationContext applicationContext){
//		SpringContextHolder springContextHolder = new SpringContextHolder();
//		springContextHolder.setApplicationContext(applicationContext);
//	}
	
	/**
	 * 注册listener
	 */
//	@Bean
//	public ServletListenerRegistrationBean<SystemListener> getServletListenerRegistrationBean(){
//		ServletListenerRegistrationBean<SystemListener> bean= new ServletListenerRegistrationBean<SystemListener>(new SystemListener());
//		
//		return bean;
//	}
	
//	@Bean//视图路径配置
//    public ApplicationContext springContextHolder(ApplicationContext applicationContext) {
//		
//        return applicationContext.;
//    }
	
//	@Bean(name = "viewResolver")//视图路径配置
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver view = new InternalResourceViewResolver();
//        view.setPrefix("/WEB-INF/web/");//放页面的路径
//        view.setSuffix(".jsp");
//        view.setViewClass(JstlView.class);
//        view.setContentType("text/html");
//        return view;
//    }

//    @Bean(name = "mybatisProdDataSource")//数据源配置
//    @ConfigurationProperties(prefix = "spring.datasource")//xxx要和server.context-path配置的名称一样
//    @Primary
//    public DataSource mybatisDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "mybatisSqlSessionFactory")//SqlSessionFactory配置
//    @Primary
//    public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("mybatisProdDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatisConfig.xml"));//po对象别名的xml文件
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:com/jc/**/domain/map/*.xml"));//mapper的xml文件
//        return bean.getObject();
//    }
}
