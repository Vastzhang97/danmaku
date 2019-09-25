package com.niit.dao.impl;

import com.niit.dao.interfaces.IMessageDao;
import com.niit.entity.MessageEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class MessageDaoImpl implements IMessageDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int addMessage(MessageEntity messageEntity) {
        try {
            sessionFactory.getCurrentSession().save(messageEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public MessageEntity selectByMid(int mid) {
        try {
            return sessionFactory.getCurrentSession().get(MessageEntity.class, mid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MessageEntity> selectBySid(int rid, int sid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where rid = ? and sid = ? order by date desc");
            query.setParameter(0, rid);
            query.setParameter(1, sid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<MessageEntity> selectBySid(int sid, int status) {
//        try {
//            Query query = null;
//            if (status == -1) {
//                query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where sid = ?");
//                query.setParameter(0, sid);
//            } else {
//                query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where sid = ? and status = ?");
//                query.setParameter(0, sid);
//                query.setParameter(1, status);
//            }
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public List<MessageEntity> selectByRid(int rid, int status) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where rid = ? and status = ?");
            query.setParameter(0, rid);
            query.setParameter(1, status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterMessage(MessageEntity messageEntity) {
        try {
            sessionFactory.getCurrentSession().update(messageEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    @Override
    public List<MessageEntity> selectBySidOrRid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where rid =? or sid = ? order by date desc ");
            query.setParameter(0, uid);
            query.setParameter(1, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MessageEntity> selectBySidAndRid(int sid, int rid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from MessageEntity where rid =? and sid = ?");
            query.setParameter(0, rid);
            query.setParameter(1, sid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param sid
     * @param rid 废弃
     * @return
     */
    @Override
    public int selectCountBySidOrRid(int sid, int rid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from MessageEntity where rid =? and status=0");
            query.setParameter(0, rid);
            long count = (long) query.uniqueResult();
            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
