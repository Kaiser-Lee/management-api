package com.management.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RedisCache<K, V> implements Cache<K, V> {

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private RedisManager cache;

    private String keyPrefix = "shiro_redis_session:";


    /**
     * 通过一个JedisManager实例构造一个RedisCache
     * @param cache
     */
    public RedisCache(RedisManager cache){
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null");
        }
        this.cache = cache;
    }

    /**
     * 获取byte[] 类型的key
     * @param key
     * @return
     */
    public byte[] getByteKey(K key){
        if(key instanceof String){
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        }else {
            return SerializeUtils.serialize(key);
        }
    }


    public RedisCache(RedisManager cache, String keyPrefix){
        this(cache);
        this.keyPrefix = keyPrefix;
    }

    @Override
    public V get(K key) throws CacheException {
        logger.debug("根据key从Redis对象中获取key["+ key + "]");
        try{
            if (key == null) {
                return null;
            }
            byte[] rawValue = cache.get(getByteKey(key));
            V value = (V)SerializeUtils.deserialize(rawValue);
            return value;
        }catch (Throwable e){
            throw new CacheException(e);
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("根据key 存储 key[" + k + "]");
        try{
            cache.set(getByteKey(k),SerializeUtils.serialize(v));
            return v;
        }catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V remove(K k) throws CacheException {
        logger.debug("从redis中删除 key[" + k + "]");
        try{
            V previous = get(k);
            cache.del(getByteKey(k));
            return previous;
        }catch (Throwable e){
            throw new CacheException(e);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        try{
            cache.flushDB();
        }catch (Throwable e){
            throw new CacheException(e);
        }
    }

    @Override
    public int size() {
        logger.debug("redis的大小");
        try{
            Long longSize = (Long)cache.dbSize();
            return longSize.intValue();
        }catch (Throwable e){
            throw new CacheException(e);
        }
    }

    @Override
    public Set<K> keys() {
        try{
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if(CollectionUtils.isEmpty(keys)){
                return Collections.emptySet();
            }else {
                Set<K> newKeys = new HashSet<K>();
                for (byte[] key: keys){
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        }catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public Collection<V> values() {
        try{
            Set<byte[]> keys = cache.keys(this.keyPrefix+ "*");
            if(!CollectionUtils.isEmpty(keys)){
                List<V> values = new ArrayList<V>(keys.size());
                for (byte[] key : keys) {
                    V value = get((K)key);
                    if (value != null){
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            }else {
                return Collections.emptyList();
            }
        }catch (Throwable e){
            throw new CacheException(e);
        }
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
