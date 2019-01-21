package com.hfut.shopping.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {

	final String lockId = UUID.randomUUID().toString();

	@Autowired
	StringRedisTemplate redis;

	public boolean Lock(String key, Long time) {
		while (!redis.opsForValue().setIfAbsent(key, lockId, time, TimeUnit.SECONDS)) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean unLock(String key) {
		String lockId = redis.opsForValue().get(key);
		if(lockId!=null&&lockId.equals(this.lockId)) {
			redis.opsForValue().getOperations().delete(key);
			return true;
		}
		return false;
	}
}
