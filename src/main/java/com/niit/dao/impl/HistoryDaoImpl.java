package com.niit.dao.impl;

import com.niit.dao.interfaces.IHistoryDao;
import com.niit.entity.HistoryEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class HistoryDaoImpl implements IHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public int addHistory(HistoryEntity historyEntity) {
        try {
            sessionFactory.getCurrentSession().save(historyEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public HistoryEntity selectByHid(int hid) {
        try {
            return sessionFactory.getCurrentSession().get(HistoryEntity.class, hid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> selectByUid(int uid) {
        try {
            String SQL = "select DISTINCT vid from history where uid = " + uid + " limit 5";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(SQL);
//            Query query = sessionFactory.getCurrentSession().createQuery("from HistoryEntity where uid = ? order by date desc");
//            query.setMaxResults(5);
            List list = query.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterHistory(HistoryEntity historyEntity) {
        try {
            sessionFactory.getCurrentSession().update(historyEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    @Override
    public List<HistoryEntity> selectByUidAndVid(int uid, int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from HistoryEntity where uid = ? and vid = ? order by date desc");
            query.setParameter(0, uid);
            query.setParameter(1, vid);
            query.setMaxResults(1);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}