package com.dx.springlearn.handlers.utils.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;


/**
 * 序列化工具类
 * 
 *
 */
public abstract class SerializeUtils {

	private static Logger log = Logger.getLogger(SerializeUtils.class);

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		}
		catch (Exception e) {
			log.error("序列化时发生异常", e);
		}
		finally {
			try {
				oos.close();
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("序列化close时发生异常", e);
			}
			
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			log.error("反序列化时发生异常", e);
		}
		finally {
			try {
				ois.close();
				bais.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("序列化close时发生异常", e);
			}
		}
		return null;
	}

}