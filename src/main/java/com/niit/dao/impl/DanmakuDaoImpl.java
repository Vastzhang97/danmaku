package com.niit.dao.impl;

import com.niit.dao.interfaces.IDanmakuDao;
import com.niit.entity.DanmakuEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class DanmakuDaoImpl implements IDanmakuDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int addDanmaku(DanmakuEntity danmakuEntity) {
        try {
            sessionFactory.getCurrentSession().save(danmakuEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public DanmakuEntity selectByDid(int did) {
        try {
            return sessionFactory.getCurrentSession().get(DanmakuEntity.class, did);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DanmakuEntity> selectByVid(int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from DanmakuEntity where vid = ? and status = 0");
            query.setParameter(0, vid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DanmakuEntity> selectByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from DanmakuEntity where uid = ?");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DanmakuEntity> selectByStatus(int status) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from DanmakuEntity where status = ?");
            query.setParameter(0, status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterDanmaku(DanmakuEntity danmakuEntity) {
        try {
            sessionFactory.getCurrentSession().update(danmakuEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }
}
