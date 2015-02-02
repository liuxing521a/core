package net.itas.core;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Msg implements Rule {
	
	protected Msg() {
	}

	public abstract void toBuffer(ByteBuf buf);

	protected abstract Msg parseBuffer(ByteBuf buf);
	
	protected void writeint8(ByteBuf buf, int data) {
		buf.writeByte(data);
	}
	
	protected void writeint16(ByteBuf buf, int data) {
		buf.writeShort(data);
	}

	protected void writeint(ByteBuf buf, int data) {
		buf.writeInt(data);
	}

	protected void writeint64(ByteBuf buf, long data) {
		buf.writeLong(data);
	}
	
	protected void writestring(ByteBuf buf, String data) { 
		if (data == null) {
			data = "";
		}
		
		byte[] bs = data.getBytes(charset);
		buf.writeShort(bs.length);
		buf.writeBytes(bs);
	}
	
	protected void writeMessage(ByteBuf ByteBuf, Msg msg) {
		msg.toBuffer(ByteBuf);
	}
	
	protected void writeArray(ByteBuf buf, List<?> dataList) { 
		if (dataList == null) {
			dataList = Collections.emptyList();
		}
		
		buf.writeShort(dataList.size());
		
		for(Object data : dataList) { 
			if (data instanceof Byte) {
				writeint8(buf, (Byte)data);
			} else if (data instanceof Short) {
				writeint16(buf, (Short)data);
			} else if (data instanceof Integer) {
				writeint(buf, (Integer)data);
			} else if (data instanceof Long) {
				writeint64(buf, (Long)data);
			} else if (data instanceof String) {
				writestring(buf, (String)data);
			} else if (data instanceof Msg) {
				writeMessage(buf, (Msg)data);
			} else {
				throw new RuntimeException("unkown message Type:" + data.getClass().getName());
			}
		} 
	}

	protected byte readint8(ByteBuf buf) {
		return buf.readByte();
	}
	
	protected short readint16(ByteBuf buf) {
		return buf.readShort();
	}

	protected int readint(ByteBuf buf) {
		return buf.readInt();
	}
	
	protected long readint64(ByteBuf buf) {
		return buf.readLong();
	}
	
	protected String readstring(ByteBuf buf) {
		int len = buf.readShort();
		byte[] bs = new byte[len];
		buf.readBytes(bs);
		
		return new String(bs, charset);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Msg> T readMessage(Class<?> type, ByteBuf ByteBuf) {
		return (T) newBuilder(type).parseBuffer(ByteBuf);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> readArray(ByteBuf buf, Class<?> classType) {
		List<Object> dataList = new LinkedList<Object>();
		
		short len = buf.readShort(); 
		for(int i = 0; i < len; i ++) {
			if (classType == Byte.class) {
				dataList.add(readint8(buf));
			} else if (classType == Short.class) {
				dataList.add(readint16(buf));
			} else if (classType == Integer.class) {
				dataList.add(readint(buf));
			} else if (classType == Long.class) {
				dataList.add(readint64(buf));
			} else if (classType == String.class) {
				dataList.add(readstring(buf));
			} else if (Msg.class.isAssignableFrom(classType)) {
				dataList.add(readMessage(classType, buf));
			} else {
				throw new RuntimeException("unkown message Type:" + classType.getName());
			}
		}
		
		return (List<T>) dataList;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Msg> T newBuilder(Class<?> classType) {
		try {
			Msg msg;
			Constructor<?> constructor = classType.getDeclaredConstructor();
			synchronized (classType) {
				boolean isAccessible = constructor.isAccessible();
				constructor.setAccessible(true);
				msg = (Msg) constructor.newInstance();
				constructor.setAccessible(isAccessible);
			}
			
			return (T) msg;
		} catch (Exception e) {
			throw new RuntimeException("创建实列失败", e);
		}
	}
}
