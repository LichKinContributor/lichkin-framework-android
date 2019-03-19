package com.lichkin.framework.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import com.lichkin.framework.log.LKLog;
import com.lichkin.framework.log.LKLogFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 类扫描工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKClassScanner {

	/** 日志对象 */
	private static final LKLog LOGGER = LKLogFactory.getLog(LKClassScanner.class);

	/** 包名 */
	private static final String PACKAGE_NAME = "com.lichkin";

	/** 点 */
	private static final char DOT = '.';

	/** 路径分隔符 */
	private static final char SEPARATOR_CHAR = '/';

	/** 类文件后缀名 */
	private static final String CLASS_SUFFIX = ".class";


	/**
	 * 扫描com.lichkin包中的类
	 * @param annotationClassNames 注解类名称
	 * @return 类列表
	 * @throws IOException IOException
	 */
	public static List<Class<?>> scanClasses(String... annotationClassNames) throws IOException {
		return scanClasses(true, annotationClassNames);
	}


	/**
	 * 扫描com.lichkin包中的类
	 * @param inJar 扫描JAR包
	 * @param annotationClassNames 注解类名称
	 * @return 类列表
	 * @throws IOException IOException
	 */
	public static List<Class<?>> scanClasses(boolean inJar, String... annotationClassNames) throws IOException {
		return scanClasses(PACKAGE_NAME, inJar, true, annotationClassNames);
	}


	/**
	 * 扫描类
	 * @param packageName 包名
	 * @param inJar 扫描JAR包
	 * @param recursive 是否递归扫描
	 * @param annotationClassNames 注解类名称
	 * @return 类列表
	 * @throws IOException IOException
	 */
	public static List<Class<?>> scanClasses(String packageName, boolean inJar, boolean recursive, String... annotationClassNames) throws IOException {
		List<Class<?>> classes = new ArrayList<>();
		for (Enumeration<URL> iterator = LKClassScanner.class.getClassLoader().getResources(packageName.replace(DOT, SEPARATOR_CHAR)); iterator.hasMoreElements();) {
			URL url = iterator.nextElement();
			switch (url.getProtocol()) {
				case "file":
					try {
						scanClassesInFilePath(Paths.get(url.toURI()), classes, packageName, recursive, annotationClassNames);
					} catch (URISyntaxException e) {
						e.printStackTrace();// ignore this
					}
				break;
				case "jar":
					if (!inJar) {
						break;
					}
					scanClassesInJar(((JarURLConnection) url.openConnection()).getJarFile(), classes, packageName, recursive, annotationClassNames);
				break;
			}
		}
		return classes;
	}


	/**
	 * 扫描类
	 * @param packageName 包名
	 * @param recursive 是否递归扫描
	 * @param annotationClassNames 注解类名称
	 * @return 类列表
	 * @throws IOException IOException
	 */
	public static List<Class<?>> scanClasses(String packageName, boolean recursive, String... annotationClassNames) throws IOException {
		return scanClasses(packageName, true, recursive, annotationClassNames);
	}


	/**
	 * 在路径中扫描
	 * @param path 路径
	 * @param classes 类型列表
	 * @param packageName 包名
	 * @param recursive 是否递归扫描
	 * @param annotationClassNames 注解类名称
	 * @throws IOException IOException
	 */
	public static void scanClassesInFilePath(Path path, List<Class<?>> classes, String packageName, boolean recursive, String... annotationClassNames) throws IOException {
		if (Files.isDirectory(path)) {
			if (recursive) {
				@SuppressWarnings("resource")
				Stream<Path> stream = Files.list(path);
				for (Iterator<Path> iterator = stream.iterator(); iterator.hasNext();) {
					scanClassesInFilePath(iterator.next(), classes, packageName, recursive, annotationClassNames);
				}
			}
		} else {
			checkToAddClass(path.toRealPath().toString().replace(File.separatorChar, DOT), classes, packageName, recursive, annotationClassNames);
		}
	}


	/**
	 * 在JAR包中扫描
	 * @param jarFile JAR包
	 * @param classes 类型列表
	 * @param packageName 包名
	 * @param recursive 是否递归扫描
	 * @param annotationClassNames 注解类名称
	 * @throws IOException IOException
	 */
	private static void scanClassesInJar(JarFile jarFile, List<Class<?>> classes, String packageName, boolean recursive, String... annotationClassNames) throws IOException {
		for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry jarEntry = entries.nextElement();
			if (jarEntry.isDirectory()) {
				continue;
			}
			checkToAddClass(jarEntry.getName().replace(SEPARATOR_CHAR, DOT), classes, packageName, recursive, annotationClassNames);
		}
	}


	/**
	 * 校验是否增加该类型
	 * @param className 类名
	 * @param classes 类型列表
	 * @param packageName 包名
	 * @param recursive 是否递归扫描
	 * @param annotationClassNames 注解类名称
	 */
	private static void checkToAddClass(String className, List<Class<?>> classes, String packageName, boolean recursive, String... annotationClassNames) {
		if (!className.endsWith(CLASS_SUFFIX)) {
			return;
		}

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("checkToAddClass -> %s", className);
		}

		int end = className.lastIndexOf(CLASS_SUFFIX);
		if (end == -1) {
			return;
		}

		int start = className.lastIndexOf(packageName);
		if (start == -1) {
			return;
		}

		className = className.substring(start, end);

		if (!recursive && className.contains(".")) {
			return;
		}

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Class -> %s", className);
		}

		try {
			Class<?> clazz = Class.forName(className);
			if (ArrayUtils.isEmpty(annotationClassNames)) {
				classes.add(clazz);
			} else {
				for (String annotationClassName : annotationClassNames) {
					if (LKClassUtils.containsAnnotation(clazz, annotationClassName)) {
						classes.add(clazz);
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error(e);
		}
	}

}
