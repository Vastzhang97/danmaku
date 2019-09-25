package com.niit.dao.impl;

import com.niit.dao.interfaces.IAdviceDao;
import com.niit.entity.AdviceEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class AdviceDaoImpl implements IAdviceDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int addAdvice(AdviceEntity adviceEntity) {
        try {
            sessionFactory.getCurrentSession().save(adviceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return FAILEDCODE;
        }
        return SUCCEEDCODE;
    }
}
