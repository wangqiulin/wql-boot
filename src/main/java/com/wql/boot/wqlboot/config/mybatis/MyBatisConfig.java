package com.wql.boot.wqlboot.config.mybatis;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 * @author wangqiulin
 * @date 2018年3月31日
*/
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.wql.boot.wqlboot.mapper")
public class MyBatisConfig implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.wql.boot.wqlboot.model.domain");
        
        //分页插件设置
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "false"); //分页参数合理化
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加分页插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        // ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //基于xml配置写法
        //bean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        return bean.getObject();
    }

	//事务
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
}
