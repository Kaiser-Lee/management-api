package com.management.redis;

import com.management.utils.SerializeUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private RedisManager redisManager;

    private String keyPrefix = "shiro_redis_session";

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
        this.redisManager.init();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null ) {
            logger.debug("session is null or session id is null");
            return;
        }
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtils.serialize(session);
        session.setTimeout(redisManager.getExpire()*100);
        this.redisManager.set(key, value, redisManager.getExpire());
    }

    private byte[] getByteKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }
        Session s = (Session) SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
        return s;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.debug("session is null or session id is null");
            return;
        }
        redisManager.del(this.getByteKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> session = new HashSet<Session>();
        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if(keys != null && keys.size() > 0){
            for (byte[] key : keys){
                Session s = (Session) SerializeUtils.deserialize(redisManager.get(key));
                session.add(s);
            }
        }
        return session;
    }
}













