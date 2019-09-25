package com.niit.dao.impl;

import com.niit.dao.interfaces.IFavoriteDao;
import com.niit.entity.FavoriteEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class FavoriteDaoImpl implements IFavoriteDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int addFavorite(FavoriteEntity favoriteEntity) {
        try {
            sessionFactory.getCurrentSession().save(favoriteEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public int delectFavorite(int uid, int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("delete FavoriteEntity where uid = ? and vid = ?");
            query.setParameter(0, uid);
            query.setParameter(1, vid);
            query.executeUpdate();
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public List<FavoriteEntity> selectByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from FavoriteEntity where uid = ?");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean checkFavorite(int uid, int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from FavoriteEntity where uid = ? and vid = ?");
            query.setParameter(0, uid);
            query.setParameter(1, vid);
            List list = query.list();
            if (list.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
