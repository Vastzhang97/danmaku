package com.niit.dao.impl;

import com.niit.dao.interfaces.ICommentDao;
import com.niit.entity.CommentEntity;
import com.niit.util.JSONUtil;
import com.niit.util.PageEntity;
import com.niit.util.PageUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class CommentDaoImpl implements ICommentDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PageUtil pageUtil;
    @Autowired
    private JSONUtil jsonUtil;

    public int addComment(CommentEntity commentEntity) {
        try {
            Serializable cid = sessionFactory.getCurrentSession().save(commentEntity);
            return (Integer) cid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public CommentEntity selectByCid(int cid) {
        try {
            return sessionFactory.getCurrentSession().get(CommentEntity.class, cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CommentEntity> selectByVid(int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from CommentEntity where vid = ? and status = 0");
            query.setParameter(0, vid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CommentEntity> selectByRid(int rid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from CommentEntity where rid = ? and status = 0");
            query.setParameter(0, rid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CommentEntity> selectByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from CommentEntity where uid = ?");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CommentEntity> selectByStatus(int status) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from CommentEntity where status = ?");
            query.setParameter(0, status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterComment(CommentEntity commentEntity) {
        try {
            sessionFactory.getCurrentSession().update(commentEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    @Override
    public String selectByPage(int maxResults, int targetPage, int vid) {
        Query query = sessionFactory.getCurrentSession().createQuery("from CommentEntity  where vid = ?");
        //设置参数
        query.setParameter(0, vid);
        PageEntity page = pageUtil.createPage(maxResults, query.list().size(), targetPage);
        //设置每页显示多少个，设置多大结果。
        query.setMaxResults(page.getEveryPageNum());
        //设置起点
        query.setFirstResult(page.getBeginPage());
        List<CommentEntity> commentEntityList = query.list();
        Map jsonMap = new HashMap<>();
        jsonMap.put("currentPage", targetPage);
        jsonMap.put("totalPage", page.getTotalPage());
        String json1 = jsonUtil.toJSon(jsonMap);
        String json2 = jsonUtil.toJSon(commentEntityList);
        String jsonResult = "[" + json1 + "," + json2.substring(1);
        return jsonResult;
    }

    @Override
    public boolean selectByMark(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from CommentEntity where uid = ? and mark = 0");
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
