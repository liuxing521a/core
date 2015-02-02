package net.itas.core;

import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * 编码解码字符集
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2014-4-29
 */
public interface Rule {

	/** buffer最大字节数*/
	static final int max_buffer_size = 8192;
	
	/** 附带KEY*/
	static final AttributeKey<User> key = AttributeKey.valueOf("user"); 
	
	/** 默认字符编码*/
	static final Charset charset = Charset.forName("UTF-8");
	
}
