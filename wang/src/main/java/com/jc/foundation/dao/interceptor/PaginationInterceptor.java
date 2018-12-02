package com.jc.foundation.dao.interceptor;

import java.sql.Connection;
import java.util.Properties;

//import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.jc.foundation.util.ReflectHelper;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
	/**
     * 默认ObjectFactory
     */
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    /**
     * 默认ObjectWrapperFactory
     */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    /**
     * 默认ReflectorFactory
     */
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		//mybatis 3.3.0之前可以用
//		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
		//mybatis 3.3.0之前后用
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);

		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		
		//拦截语句过滤数据
		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler)metaStatementHandler.getValue("delegate.parameterHandler");
		Object obj = defaultParameterHandler.getParameterObject();
		String dataAccessDynamicSQL = (String)ReflectHelper.getValueByFieldName(obj, "dataAccessDynamicSQL");
		if(dataAccessDynamicSQL != null && !"".equals(dataAccessDynamicSQL)){
			dataAccessDynamicSQL = dataAccessDynamicSQL.replace("t.", "").replace("_","");
			originalSql = "select * from (" + originalSql + ") as dynamicFilterData where 1 = 1 " + dataAccessDynamicSQL;
		}
		
		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");

		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (databaseType == null) {
			throw new RuntimeException(
					"mybatisConfig.xml中方言属性值不确定:" + configuration.getVariables().getProperty("dialect"));
		}
		Dialect dialect = null;
		switch (databaseType) {
			case ORACLE:
				dialect = new OracleDialect();
				break;
			case MYSQL:
				dialect = new MySqlDialect();
				break;
		}
		if(dialect ==null){
			throw new IllegalStateException("没有对应的数据库方言");
		}
		
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties arg0) {

	}

}
