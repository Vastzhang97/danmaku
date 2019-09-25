package com.niit.service.impl;

import com.niit.dao.impl.AdviceDaoImpl;
import com.niit.dao.interfaces.IAdviceDao;
import com.niit.entity.AdviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdviceServiceImpl implements IAdviceDao {

    @Autowired
    AdviceDaoImpl adviceDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addAdvice(AdviceEntity adviceEntity) {
        return adviceDao.addAdvice(adviceEntity);
    }
}
