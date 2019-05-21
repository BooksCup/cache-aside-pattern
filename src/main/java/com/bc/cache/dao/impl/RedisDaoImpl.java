package com.bc.cache.dao.impl;

import com.bc.cache.dao.RedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {
    @Resource
    private Jedis jedis;

    @Override
    public void set(String key, String value) {
        jedis.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }

    @Override
    public void delete(String key) {
        jedis.del(key);
    }

}
