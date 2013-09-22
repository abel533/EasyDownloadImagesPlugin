package com.isea.basic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class LoadUtils {
	/**
	 * 获取项目绝对路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getProjectPath(Class clasz) throws Exception{
		String temp = URLDecoder.decode(clasz.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		return temp.substring(0, temp.lastIndexOf('/'));  
	}
	
	/**
	 * 动态载入jar包
	 * @param folderPath
	 * @return
	 * @throws Exception
	 */
	public static URLClassLoader loadJars(String folderPath) throws Exception{
		File pluginFile = new File(folderPath);
		File[] pluginJars = pluginFile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File jarFile) {
				if(jarFile.getName().toUpperCase().endsWith(".JAR")){
					return true;
				}
				return false;
			}
		});
		URL[] jarUrls = new URL[pluginJars.length];
		for(int i=0;i<pluginJars.length;i++){
			jarUrls[i] = pluginJars[i].toURI().toURL();
		}
		return new URLClassLoader(jarUrls);
	}
	
	/**
	 * 载入ini配置信息
	 * @param folderPath
	 * @return
	 * @throws Exception
	 */
	public static Set<Entry<Object, Object>> loadInis(String folderPath) throws Exception{
		File pluginIni = new File(folderPath);
		File[] pluginInis = pluginIni.listFiles(new FileFilter() {
			@Override
			public boolean accept(File iniFile) {
				if(iniFile.getName().toUpperCase().endsWith(".INI")){
					return true;
				}
				return false;
			}
		});
		Set<Entry<Object, Object>> sets = new HashSet<Entry<Object,Object>>();
		Properties properties = new Properties();
		
		for(int i=0;i<pluginInis.length;i++){
			properties.load(new FileReader(pluginInis[i].getAbsolutePath()));
			sets.addAll(properties.entrySet());
		}
		return sets;
	}
}
