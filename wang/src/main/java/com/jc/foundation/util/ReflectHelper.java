package com.jc.foundation.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.jc.system.common.util.StringUtil;

/**
 * 反射工具帮助类
 * @title GOA V2.0
 * @version 2014年7月3日
 */
@SuppressWarnings("rawtypes")
public class ReflectHelper {

	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 * @version 1.0 2014年7月3日
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @version 1.0 2014年7月3日
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @version 1.0 2014年7月3日
	 */
	@SuppressWarnings("unchecked")
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		if (obj instanceof java.util.Map) {
			java.util.Map map = (java.util.Map) obj;
			map.put(fieldName, value);
		} else {
			Field field = obj.getClass().getDeclaredField(fieldName);
			if (field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		}
	}
	/**
	 * 
	  * 转义对象中string类型值中的特殊字符
	  * @param obj 需要处理的对象
	  * @version 1.0 2014年7月18日 上午9:10:05
	 */
	public static void escapeSQLSpecialChar(Object obj){
		Class<?> clazz = obj.getClass();
		Method methods[] = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String name  = method.getName();
			if(name.startsWith("get")&&method.getParameterTypes().length==0&&method.getReturnType().getName().equals("java.lang.String")){
				//排序属性中有数据库字段名不能转义
				if(name.equals("getClass")||name.equals("getOrderBy")||name.equals("getDataAccessDynamicSQL")){
					continue;
				}
				try {
					Object value = method.invoke(obj, new Object[]{});
					if(value!=null&&!"".equals(value)){
						String setMethodName = "set"+name.substring(3);
						Method setMethod = clazz.getMethod(setMethodName, new Class[]{String.class});
						
						setMethod.invoke(obj, new Object[]{StringUtil.escapeSQLSpecialChar((String)value)});
					}
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
	}
}