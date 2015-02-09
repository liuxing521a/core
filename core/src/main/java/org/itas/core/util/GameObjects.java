package org.itas.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.itas.core.Simple;
import org.itas.core.enums.EByte;
import org.itas.core.enums.EInt;
import org.itas.core.enums.EString;
import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.Objects;

import net.itas.core.resource.Resource;

/**
 * 对象基类的提供处理集合的方法
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年1月27日下午3:04:09
 */
public final class GameObjects {
	
	/**
	 * 把集合解析成可存储字串
	 * @param c 集合列表
	 * @return
	 */
	public static String toString(Collection<?> c) {
		if (Objects.isEmpty(c)) {
			return "";
		}
		
		StringBuffer dataBuf = new StringBuffer(32*c.size());
		
		Class<?> clazz;
		for (Object o : c) {
			if ((clazz = o.getClass()) == Simple.class) {
				dataBuf.append(((Simple<?>)o).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, Resource.class)) {
				dataBuf.append(((Resource)o).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, EByte.class)) {
				dataBuf.append(((EByte)o).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EInt.class)) {
				dataBuf.append(((EInt)o).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EString.class)) {
				dataBuf.append(((EString)o).key()).append('|');
			} else if (clazz == String.class) {
				dataBuf.append(textEncode((String)o)).append('|');
			} else {
				dataBuf.append(o).append('|');
			}
		}
				
		return dataBuf.toString();
	}
	
	/**
	 * 把字串解析成字串列表
	 * @param str
	 * @return
	 */
	public static List<String> parseList(String str) {
		if (Objects.isEmpty(str)) {
			return Collections.emptyList();
		}
		
		List<String> dataList = new ArrayList<String>();
		StringBuffer dataBuf = new StringBuffer();

		str = textDecode(str);
		for (char ch : str.toCharArray()) {
			if (ch == '|') {
				dataList.add(dataBuf.toString());
				dataBuf.setLength(0);
				continue;
			}
			
			dataBuf.append(ch);
		}
		
		return dataList;
	}
	
	/**
	 * 把map解析成可存储字串
	 * @param m
	 * @return
	 */
	public static String toString(Map<?, ?> m) {
		if (Objects.isEmpty(m)) {
			return "";
		}
		
		StringBuilder dataBuf = new StringBuilder(64*m.size());
		
		Class<?> clazz;
		for (Entry<?, ?> data : m.entrySet()) {
			if ((clazz = data.getKey().getClass()) == Simple.class) {
				dataBuf.append(((Simple<?>)data.getKey()).getId()).append(',');
			} else if (ClassUtils.isExtends(clazz, Resource.class)) {
				dataBuf.append(((Resource)data.getKey()).getId()).append(',');
			} else if (ClassUtils.isExtends(clazz, EByte.class)) {
				dataBuf.append(((EByte)data.getKey()).key()).append(',');
			} else if (ClassUtils.isExtends(clazz, EInt.class)) {
				dataBuf.append(((EInt)data.getKey()).key()).append(',');
			} else if (ClassUtils.isExtends(clazz, EString.class)) {
				dataBuf.append(((EString)data.getKey()).key()).append(',');
			} else if (clazz == String.class) {
				dataBuf.append(textEncode((String)data.getKey())).append(',');
			} else {	
				dataBuf.append(data.getKey()).append(',');
			}
			
			if ((clazz = data.getValue().getClass()) == Simple.class) {
				dataBuf.append(((Simple<?>)data.getValue()).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, Resource.class)) {
				dataBuf.append(((Resource)data.getValue()).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, EByte.class)) {
				dataBuf.append(((EByte)data.getValue()).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EInt.class)) {
				dataBuf.append(((EInt)data.getValue()).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EString.class)) {
				dataBuf.append(((EString)data.getValue()).key()).append('|');
			} else if (clazz == String.class) {
				dataBuf.append(textEncode((String)data.getValue())).append('|');
			} else {
				dataBuf.append(data.getValue()).append('|');
			}
		}

		return dataBuf.toString();
	}
	
	public static Map<String, String> parseMap(String str) {
		if (Objects.isEmpty(str)) {
			return Collections.emptyMap();
		}
		
		Map<String, String> dataMap = new HashMap<String, String>();
		StringBuffer k = new StringBuffer();
		StringBuffer v = new StringBuffer();
		
		StringBuffer c = k;
		for (char ch : str.toCharArray()) {
			if (ch == ',') {
				c = v;
				continue;
			} 
			
			if (ch == '|') {
				dataMap.put(k.toString(), v.toString());
				k.setLength(0);
				v.setLength(0);
				c = k;
				continue;
			}
			
			c.append(ch);
		}
		
		return dataMap;
	}
	
	/**
	 * 把数组解析成可存储字串
	 * @param c 集合列表
	 * @return
	 */
	public static String toString(Object[] array) {
		if (Objects.isNull(array)) {
			return "";
		}
		
		StringBuffer dataBuf = new StringBuffer(32*array.length);
		
		Class<?> clazz;
		for (Object o : array) {
			if ((clazz = o.getClass()) == Simple.class) {
				dataBuf.append(((Simple<?>)o).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, Resource.class)) {
				dataBuf.append(((Resource)o).getId()).append('|');
			} else if (ClassUtils.isExtends(clazz, EByte.class)) {
				dataBuf.append(((EByte)o).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EInt.class)) {
				dataBuf.append(((EInt)o).key()).append('|');
			} else if (ClassUtils.isExtends(clazz, EString.class)) {
				dataBuf.append(((EString)o).key()).append('|');
			} else if (clazz == String.class) {
				dataBuf.append(textEncode((String)o)).append('|');
			} else {
				dataBuf.append(o).append('|');
			}
		}
				
		return dataBuf.toString();
	}
	
	/**
	 * 把数组解析成可存储字串
	 * @param c 集合列表
	 * @return
	 */
	public static String toString(Object[][] array) {
		if (Objects.isNull(array)) {
			return "";
		}
		
		StringBuffer dataBuf = new StringBuffer(32*array.length);
		dataBuf.append('[').append(array.length).append(',');
		dataBuf.append(array[0].length).append(']');
		
		Class<?> clazz;
		for (Object[] os : array) {
			for (Object o : os) {
				if ((clazz = o.getClass()) == Simple.class) {
					dataBuf.append(((Simple<?>)o).getId()).append('|');
				} else if (ClassUtils.isExtends(clazz, Resource.class)) {
					dataBuf.append(((Resource)o).getId()).append('|');
				} else if (ClassUtils.isExtends(clazz, EByte.class)) {
					dataBuf.append(((EByte)o).key()).append('|');
				} else if (ClassUtils.isExtends(clazz, EInt.class)) {
					dataBuf.append(((EInt)o).key()).append('|');
				} else if (ClassUtils.isExtends(clazz, EString.class)) {
					dataBuf.append(((EString)o).key()).append('|');
				} else if (clazz == String.class) {
					dataBuf.append(textEncode((String)o)).append('|');
				} else {
					dataBuf.append(o).append('|');
				}
			}
		}
				
		return dataBuf.toString();
	}
	
	
    private static String textEncode(String text) {
        text = noNull(text, "");

        StringBuffer str = new StringBuffer();
        for (char ch : text.toCharArray()) {
            switch (ch) {
            case '|':  
            	str.append("&lwx;"); 
            	break;
            case ',': 
            	str.append("&lyx;"); 
            	break;
            default:
                str.append(ch);
                break;
            }
        }

        return str.toString();
    }
	
	private static String textDecode(String content) {
		StringBuilder textBuf = new StringBuilder();

		StringBuilder signBuf = null;
		for (char ch : content.toCharArray()) {
			/*begin 判断是转义符开始,创建转义缓冲*/
			if (ch == '&') {
				signBuf = new StringBuilder('&');
				continue;
			}/*end*/
			
			/*begin 转义缓冲为空，加入字符缓冲*/
			if (Objects.isNull(signBuf)) {
				textBuf.append(ch);
				continue;
			}/*end*/
			
			signBuf.append(ch);
			
			/*begin 超过转义串长度， 非转义，添加字符缓冲*/
			if (signBuf.length() > 5) {
				textBuf.append(signBuf.toString());
				signBuf = null;
				continue;
			} /*end*/
			
			/*begin 小于转义串长度， 继续添加转义缓冲*/
			if (signBuf.length() < 5) {
				continue;
			}/*end*/

			/*begin 竖线[|]转义，替换*/
			if ("&lwx;".equals(signBuf.toString())) {
				textBuf.append('|');
				signBuf = null;
				continue;
			} /*end*/
			
			/*begin 逗号[,]转义，替换*/
			if ("&lyx;".equals(signBuf.toString())) {
				textBuf.append(',');
				signBuf = null;
				continue;
			} /*end*/
		}
		
		if (Objects.nonNull(signBuf)) {
			 textBuf.append(signBuf.toString());
		}
		
		return textBuf.toString();
	}
	
	private static String noNull(String str, String defaultStr) {
        return Objects.isNull(str) ? str : defaultStr;
    }
	
	private GameObjects() {
		throw new RuntimeException("can not new instance");
	}

}
