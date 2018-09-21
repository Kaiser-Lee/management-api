package com.management.redis;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisManager {

    @Value("${spring.redis.host}")
    private String host = "122.114.110.171";

    @Value("${spring.redis.port}")
    private int port = 6379;

    @Value("${spring.redis.password}")
    private String password = "";

    @Value("${spring.redis.timeout}")
    private int timeout = 0;

    private static JedisPool jedisPool = null;

    private int expire = 0;

    public void RedisManager(){

    }

    /**
     * 初始化方法
     */
    public void init(){
        if(jedisPool == null) {
            if(password != null && !"".equals(password)){
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
            } else if (timeout != 0 ) {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(),host, port);
            }
        }
    }

    public byte[] get(byte[] key){
        byte[] value = null;
        Jedis jedis = jedisPool.getResource();
        try{
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null ) {
                jedis.close();
            }
        }
        return value;
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(key, value);
            if(this.expire != 0){
                jedis.expire(key, this.expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(key, value);
            if(this.expire != 0){
                jedis.expire(key, this.expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    public void del(byte[] key) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.del(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public void flushDB(){
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.flushDB();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public Long dbSize(){
        Long dbSize = 0L;
        Jedis jedis = jedisPool.getResource();
        try{
            dbSize = jedis.dbSize();
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return dbSize;
    }

    public Set<byte[]> keys(String pattern){
        Set<byte[]> keys = null;
        Jedis jedis = jedisPool.getResource();
        try{
            keys = jedis.keys(pattern.getBytes());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return keys;
    }


    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}