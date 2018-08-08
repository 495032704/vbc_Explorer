package com.dx.springlearn.handlers.utils.redis;

import com.dx.springlearn.handlers.utils.BooleanUtils;
import com.dx.springlearn.handlers.utils.RedisPropertiesUtils;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.EXPX;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.NXXX;

import org.apache.commons.lang.math.NumberUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jedis工具类
 * 
 *
 */
public abstract class JedisUtils {

	private static JedisPool pool;

	static {
		// 设置配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(NumberUtils.toInt(RedisPropertiesUtils.getProperties("max.total")));
		config.setMaxIdle(NumberUtils.toInt(RedisPropertiesUtils.getProperties("max.idle")));
		config.setMinIdle(NumberUtils.toInt(RedisPropertiesUtils.getProperties("min.idle")));
		config.setMaxWaitMillis(NumberUtils.toInt(RedisPropertiesUtils.getProperties("max.wait.millis")));
		config.setTestOnBorrow(org.apache.commons.lang.BooleanUtils
				.toBoolean(RedisPropertiesUtils.getProperties("test.on.borrow")));
		config.setBlockWhenExhausted(org.apache.commons.lang.BooleanUtils
				.toBoolean(RedisPropertiesUtils.getProperties("block.when.exhausted")));

		// 初始化jedis池
		String host = RedisPropertiesUtils.getProperties("host");
		int port = NumberUtils.toInt(RedisPropertiesUtils.getProperties("port"));
		int timeout = NumberUtils.toInt(RedisPropertiesUtils.getProperties("connection.timeout"));
		String password = RedisPropertiesUtils.getProperties("password");
		if (!BooleanUtils.isBlank(password)) {
			pool = new JedisPool(config, host, port, timeout, password);
		}
		else {
			pool = new JedisPool(config, host, port, timeout);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.get(key);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getObject(String key) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}

		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			byte[] data = jedis.get(key.getBytes());
			return (V) SerializeUtils.unserialize(data);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取全部指定键的值
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key) {
		return getList(key, 0, -1);
	}

	/**
	 * 获取指定键的值 List startIndex to endIndex
	 * 
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public static List<String> getList(String key, long start, long end) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.lrange(key, start, end);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> getSet(String key) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.smembers(key);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> getSortedSet(String key) {
		return getSortedSet(key, 0, -1);
	}

	/**
	 * 获取指定键的值 set startIndex to endIndex
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> getSortedSet(String key, long start, long end) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.zrange(key, start, end);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> getMap(String key) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.hgetAll(key);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String getMap(String key, String field) {
		if (BooleanUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.hget(key, field);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 是否包含指定的键
	 * 
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		if (BooleanUtils.isBlank(key)) {
			return false;
		}
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		if (BooleanUtils.isBlank(key) || value == null) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param value
	 * @param nxxx NX 当key不存在时设置，XX当Key存在是设置
	 * @param expx EX=秒，PX=毫秒
	 * @param time 缓存时间
	 */
	public static void setString(String key, String value, NXXX nxxx, EXPX expx, long time) {
		if (BooleanUtils.isBlank(key) || value == null) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value, nxxx.getValue(), expx.getValue(), time);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值 setList
	 * 
	 * @param key
	 * @param value
	 */
	public static void setList(String key, String... value) {
		if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.lpush(key, value);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值 set
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSet(String key, String... value) {
		if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.sadd(key, value);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param value
	 * @param score
	 */
	public static void setSortedSet(String key, String value, double score) {
		if (BooleanUtils.isBlank(key) || value == null) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.zadd(key, score, value);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSortedSet(String key, Map<String, Double> value) {
		if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.zadd(key, value);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void setMap(String key, String field, String value) {
		if (BooleanUtils.isBlank(key) || BooleanUtils.isBlank(field)) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.hset(key, field, value);
		}
		finally {
			close(jedis);
		}
	}


	/**
	 * 缓存指定的键值
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 * @param expx
	 * @param time
	 */
	public static void setObject(String key, Object value, NXXX nxxx, EXPX expx, long time) {
		if (BooleanUtils.isBlank(key) || value == null) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.set(key.getBytes(), SerializeUtils.serialize(value), nxxx.getValue().getBytes(),
					expx.getValue().getBytes(), time);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 增加指定的值
	 * 
	 * @param key
	 * @param value
	 */
	public static long incrementBy(String key, int value) {
		if (BooleanUtils.isBlank(key) || value == 0) {
			return 0l;
		}
		Jedis jedis = getJedis();
		try {
		return 	jedis.incrBy(key, value);
		}catch (Exception e){
			return 0l;
		}
		finally {
			close(jedis);
		}

	}

	/**
	 * 给指定的键设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public static void expire(String key, int seconds) {
		if (BooleanUtils.isBlank(key) || seconds == 0) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.expire(key, seconds);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 删除指定的键
	 * 
	 * @param key
	 */
	public static void remove(String... key) {
		if (BooleanUtils.isEmpty(key)) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
		}
		finally {
			close(jedis);
		}
	}

	/**
	 * 获取以指定格式的键
	 * 
	 * @param pattern
	 * @return
	 */
	public static Set<String> keySet(String pattern) {
		if (BooleanUtils.isBlank(pattern)) {
			return null;
		}
		Set<String> result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.keys(pattern);
		}
		finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * 获取jedis对象
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		return pool.getResource();
	}

	/**
	 * 关闭jedis对象
	 * 
	 * @param jedis
	 */
	public static void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public static boolean getString(boolean equals) {
		// TODO Auto-generated method stub
		return false;
	}
}