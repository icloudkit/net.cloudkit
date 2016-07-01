package net.cloudkit.enterprises.infrastructure.utilities;

/**
 * ObjectFactory
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2011-10-8 下午12:31:44
 */
public class ObjectFactory {

	private static Class<?> clazz;

	public static Object getObject(Class<?> clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		// 获得当前类模板
		clazz = Class.forName(clazz.getName());
		// 产生了一个新的当前类对象
		return clazz.newInstance();
	}

	public static Object getObject(String classPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		// 获得当前类模板
		clazz = Class.forName(classPath);
		// 产生了一个新的当前类对象
		return clazz.newInstance();
	}
}
