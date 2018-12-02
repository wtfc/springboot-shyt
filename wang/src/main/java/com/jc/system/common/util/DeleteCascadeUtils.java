/**
 * 
 */
package com.jc.system.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.jc.system.CustomException;
import com.jc.system.common.domain.CascadeBusinessSetting;
import com.jc.system.common.domain.CascadeSetting;

/**
 * @title GOA V2.0
 * @version 2014年7月21日上午9:11:47
 */
public class DeleteCascadeUtils {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);
	private static JdbcTemplate jdbcTemplate;
	private static Jaxb2Marshaller unmarshaller;
	private static CascadeBusinessSetting deleteCascade;

	/**
	 * 初始化JDBC和OXM
	 */
	static {
		// 初始化JDBC
		jdbcTemplate = new JdbcTemplate(
				(DataSource) SpringContextHolder.getBean("dataSource"));
		// 加载配置文件
		InputStream fis = null;
		try {
			fis = DeleteCascadeUtils.class.getClassLoader()
					.getResourceAsStream("delete-cascade.xml");
			unmarshaller = new Jaxb2Marshaller();
			unmarshaller.setClassesToBeBound(CascadeBusinessSetting.class);
			deleteCascade = (CascadeBusinessSetting) unmarshaller
					.unmarshal(new StreamSource(fis));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 
	 */
	public DeleteCascadeUtils() {
	}

	/**
	 * 方法描述：判断指定表指定记录是否可删除
	 * 
	 * @param tableName
	 *            待删除记录的表名
	 * @param columnName
	 *            待删除记录的列名
	 * @param columnValue
	 *            待删除记录的值
	 * @return true 为可删除，false 不可删除
	 * @author zhangligang
	 * @version 2014年7月22日下午3:46:43
	 * @see
	 */
	public static boolean canDelete(String id, String columnValue) {
		boolean inuse = false;
		StringBuffer sql = new StringBuffer();
		// 在级联删除配置中查找匹配的Function
		for (CascadeSetting setting : deleteCascade.getFunction()) {
			if (setting.getId().equals(id)) {
				// 在匹配的Function中，查询关联表是否有未删除的记录关联了主表这条记录
				for (CascadeSetting refTable : setting.getRefTable()) {
					if (sql.length() > 0)
						sql.delete(0, sql.length());
					sql.append("select count(id) from ")
							.append(refTable.getTableName())
							.append(" where delete_flag='0' and ")
							.append(refTable.getColumnName()).append("='")
							.append(columnValue).append("'");
					int count = jdbcTemplate.queryForObject(sql.toString(),Integer.class);
					if (count > 0) {
						inuse |= true;
						break;
					}
				}

				if (inuse == true)
					break;
			}

		}
		return !inuse;
	}

	/**
	 * 方法描述：判断指定表指定记录是否可删除
	 * 
	 * @param tableName
	 *            待删除记录的表名
	 * @param columnName
	 *            待删除记录的列名
	 * @param columnValue
	 *            待删除记录的值
	 * @return true 为可删除，false 不可删除
	 * @author zhangligang
	 * @version 2014年7月22日下午3:46:43
	 * @see
	 */
	public static boolean canBatchDelete(String id, String columnValue) {
		if (StringUtil.isEmpty((String) columnValue)) {
			return true;
		}
		String value[] = ((String) columnValue).split(",");

		boolean inuse = false;
		StringBuffer sql = new StringBuffer();
		// 在级联删除配置中查找匹配的Function
		for (CascadeSetting setting : deleteCascade.getFunction()) {
			if (setting.getId().equals(id)) {
				// 在匹配的Function中，查询关联表是否有未删除的记录关联了主表这条记录
				for (CascadeSetting refTable : setting.getRefTable()) {
					if (sql.length() > 0)
						sql.delete(0, sql.length() - 1);
					sql.append("select count(id) from ")
							.append(refTable.getTableName())
							.append(" where delete_flag='0' and (1=2 ");
					for (String v : value) {
						sql.append(" or ").append(refTable.getColumnName())
								.append("='").append(v).append("'");
					}
					sql.append(")");
					int count = jdbcTemplate.queryForObject(sql.toString(),Integer.class);
					if (count > 0) {
						inuse |= true;
						break;
					}
				}

				if (inuse == true)
					break;
			}

		}
		return !inuse;
	}


	/**
	 * 方法描述：删除数据(工作流)
	 * 
	 * @param tableName
	 *            待删除记录的表名
	 * @param columnName
	 *            待删除记录的列名
	 * @param columnValue
	 *            待删除记录的值
	 * @return true 为可删除，false 不可删除
	 * @version 2014年7月22日下午3:46:43
	 * @see
	 */
	public static void deleteData(String tableName, String columnName,
			Object columnValue) {
		for (CascadeSetting setting : deleteCascade.getFunction()) {
			if (setting.getTableName().equals(tableName)) {
				// 查询对应的数据
				String queryTable = "select * from " + tableName + " where "
						+ columnName + " = '" + columnValue
						+ "' and delete_flag='0'";
				List<Map<String, Object>> result = jdbcTemplate
						.queryForList(queryTable);
				for (Map<String, Object> item : result) {
					// 对关联表进行递归删除
					for (CascadeSetting refTable : setting.getRefTable()) {
						deleteData(refTable.getTableName(),
								refTable.getColumnName(), item.get(columnValue));
					}
				}
			}
		}
		String deleteSql = "update " + tableName
				+ " set delete_flag = 1 where " + columnName + " = '"
				+ columnValue + "'";
		jdbcTemplate.update(deleteSql);
	}

	/**
	 * 方法描述：检查指定值的记录是否存在
	 * @param id
	 * @param columnValue
	 * @return
	 * @author zhangligang
	 * @version  2014年8月19日下午1:59:28
	 * @see
	 */
	public static boolean checkExist(String functionId, String columnValue) {
		StringBuffer sql = new StringBuffer();
		// 在级联配置中查找匹配的Function
		for (CascadeSetting setting : deleteCascade.getFunction()) {
			if (setting.getId().equals(functionId)) {
				if (sql.length() > 0)
					sql.delete(0, sql.length() - 1);
				sql.append("select count(id) from ")
						.append(setting.getTableName())
						.append(" where delete_flag='0' and ")
						.append(setting.getColumnName()).append("='")
						.append(columnValue).append("'");
				int count = jdbcTemplate.queryForObject(sql.toString(),Integer.class);
				if (count > 0) {
					return true;
				}
			}
		}
		return false;
	}
}
