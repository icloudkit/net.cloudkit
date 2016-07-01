package net.cloudkit.enterprises.infrastructure.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// protected void setSessionAttribute(String key,Object obj){
//     Subject currentUser = SecurityUtils.getSubject();
//     Session session = currentUser.getSession();
//      session.setAttribute(key, obj);
// }
//
// protected Object getSessionAttribute(String key){
//     Subject currentUser = SecurityUtils.getSubject();
//     Session session = currentUser.getSession();
//     return session.getAttribute(key);
// }
public class ShiroRedisSessionDAO extends AbstractSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDAO.class);

    @Resource
    protected RedisTemplate<String, String> redisTemplate;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(final Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key  = redisTemplate.getStringSerializer().serialize(keyPrefix + session.getId());
                JdkSerializationRedisSerializer serializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
                byte[] value = serializer.serialize(session);
                connection.set(key, value);
                // session.setTimeout(60 * 1000);
                connection.expire(key, 60 * 1000);
                return connection.setNX(key, value);
            }
        });
    }

    @Override
    public void delete(final Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(getByteKey(session.getId()));
            }
        });
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(final String key : keys){
                Session session = redisTemplate.execute(new RedisCallback<Session>() {
                    @Override
                    public Session doInRedis(RedisConnection connection) throws DataAccessException {
                        JdkSerializationRedisSerializer serializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
                        return (Session) serializer.deserialize(connection.get(key.getBytes()));
                    }
                });
                sessions.add(session);
            }
        }

        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(final Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }

        Session session = redisTemplate.execute(new RedisCallback<Session>() {
            @Override
            public Session doInRedis(RedisConnection connection) throws DataAccessException {
                JdkSerializationRedisSerializer serializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
                return (Session)serializer.deserialize(connection.get(getByteKey(sessionId)));
            }
        });

        return session;
    }

    /**
     * 获得byte[]型的key
     * @param sessionId
     * @return
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
