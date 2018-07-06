package com.dfans.common;

import org.apache.commons.lang.StringUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * <p>
 * ehcache 缓存工具类
 * </p>
 * <p>
 * cacheName在ehcache.xml中配置
 * </p>
 * 
 */
public class EhcacheHelper {

	public static CacheManager manager = CacheManager.create();

	public static Object get(String cacheName, Object key) {
		Cache cache = manager.getCache(cacheName);
		if (cache != null) {
			Element element = cache.get(key);
			if (element != null) {
				return element.getObjectValue();
			}
		}
		return null;
	}

	public static void put(String cacheName, Object key, Object value) {
		Cache cache = manager.getCache(cacheName);
		if (cache != null) {
			cache.put(new Element(key, value));
		}
	}

	public static boolean remove(String cacheName, Object key) {
		Cache cache = manager.getCache(cacheName);
		if (cache != null) {
			return cache.remove(key);
		}
		return false;
	}

	public static void main(String[] args) {
		String key = "key";
		String value = (String) EhcacheHelper.get("mytest", key);

		for (int i = 0; i < 2; i++) {
			value = (String) EhcacheHelper.get("mytest", key);
			if (StringUtils.isNotBlank(value)) {
				System.out.println("存在mytest" + value);
			} else {
				System.out.println("不存在mytest");
				EhcacheHelper.put("mytest", key, "hello");
				System.out.println("设置mytest" + EhcacheHelper.get("mytest", key));
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("-------------");
		
		key = "key2";
		value = (String) EhcacheHelper.get("mytest", key);
		EhcacheHelper.put("mytest", key, "~~~hello~~~");
		System.out.println("设置"+key+"值 为" + EhcacheHelper.get("mytest", key));
		
		System.out.println("设置完KEY2 然后继续查看key1=="+EhcacheHelper.get("mytest", "key"));

		// System.out.println(EhcacheHelper.get("mytest", key));
	}

}
