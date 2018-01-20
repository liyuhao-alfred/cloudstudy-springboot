package com.cloudstudy.druid;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@MapperScan("com.cloudstudy.mapper") // mapper 接口类扫描包配置
public class SessionFactoryConfig implements TransactionManagementConfigurer {
	/** * mybatis 配置路径 */
	@SuppressWarnings("unused")
	private static String MYBATIS_CONFIG = "mybatis-config.xml";

	@Autowired
	private DataSource dataSource;

	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasPackage;

	/**
	 * 创建sqlSessionFactoryBean 实例 并且设置configtion 如驼峰命名.等等 设置mapper 映射路径
	 * 设置datasource数据源
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		/** 设置mybatis configuration 扫描路径 */
		// sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
		/** 设置datasource */
		sqlSessionFactoryBean.setDataSource(dataSource);
		/** 设置mapper.xml文件路径 */
		sqlSessionFactoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		/** 设置typeAlias 包扫描路径 */
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
		return sqlSessionFactoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}