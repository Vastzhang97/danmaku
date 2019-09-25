package com.niit.dao.impl;

import com.niit.dao.interfaces.ILikeDao;
import com.niit.entity.TbLikeEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class LikeDaoImpl implements ILikeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int addLike(TbLikeEntity likeEntity) {
        try {
            sessionFactory.getCurrentSession().save(likeEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public TbLikeEntity selectByLid(int lid) {
        try {
            return sessionFactory.getCurrentSession().get(TbLikeEntity.class, lid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param uid
     * @param status -1查询全部
     * @return
     */
    public List<TbLikeEntity> selectByUid(int uid, int status) {
        try {
            Query query = null;
            if (status == -1) {
                query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where uid = ? order by date desc ");
                query.setParameter(0, uid);
            } else {
                query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where uid = ? order by status");
                query.setParameter(0, uid);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param vid
     * @param status -1查询全部
     * @return
     */
    public List<TbLikeEntity> selectByVid(int vid, int status) {
        try {
            Query query = null;
            if (status == -1) {
                query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where vid = ?");
                query.setParameter(0, vid);
            } else {
                query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where vid = ? and status = ?");
                query.setParameter(0, vid);
                query.setParameter(1, status);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param cid
     * @param status -1查询全部
     * @return
     */
    public List<TbLikeEntity> selectByCid(int cid, int status) {
        try {
            Query query = null;
            if (status == -1) {
                String HQL = "from TbLikeEntity where cid = ?";
                query = sessionFactory.getCurrentSession().createQuery(HQL);
                query.setParameter(0, cid);
            } else {
                String HQL = "from TbLikeEntity where cid = ? and status = ?";
                query = sessionFactory.getCurrentSession().createQuery(HQL);
                query.setParameter(0, cid);
                query.setParameter(1, status);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param did
     * @param status -1查询全部
     * @return
     */
    public List<TbLikeEntity> selectByDid(int did, int status) {
        try {
            Query query = null;
            if (status == -1) {
                String HQL = "from TbLikeEntity where did = ?";
                query = sessionFactory.getCurrentSession().createQuery(HQL);
                query.setParameter(0, did);
            } else {
                query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where did = ? and status = ?");
                query.setParameter(0, did);
                query.setParameter(1, status);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterLike(TbLikeEntity tbLikeEntity) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(tbLikeEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    @Override
    public Boolean selectByUidAndVid(int uid, int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from TbLikeEntity where uid = ? and vid = ?");
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

    @Override
    public Boolean selectBySatatus(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from TbLikeEntity where uid = ? and status = 0");
            query.setParameter(0, uid);
            long count = (long) query.uniqueResult();
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
