package com.niit.dao.impl;

import com.niit.dao.interfaces.IContributionDao;
import com.niit.entity.ContributionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class ContributionDaoImpl implements IContributionDao {
    @Autowired
    private SessionFactory sessionFactory;

    public int addContribution(ContributionEntity contributionEntity) {
        try {
            sessionFactory.getCurrentSession().save(contributionEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public ContributionEntity selectByCid(int cid) {
        try {
            return sessionFactory.getCurrentSession().get(ContributionEntity.class, cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContributionEntity> selectByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from ContributionEntity where uid = ? and status != 1");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int alterContribution(ContributionEntity contributionEntity) {
        try {
            sessionFactory.getCurrentSession().update(contributionEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public List<ContributionEntity> selectByStatus(int status) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from ContributionEntity where status = ?");
            query.setParameter(0, status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
