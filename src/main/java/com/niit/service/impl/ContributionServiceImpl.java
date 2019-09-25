package com.niit.service.impl;

import com.niit.dao.impl.ContributionDaoImpl;
import com.niit.dao.impl.UserDaoImpl;
import com.niit.dao.impl.VideoDaoImpl;
import com.niit.entity.CommentEntity;
import com.niit.entity.ContributionEntity;
import com.niit.entity.VideoEntity;
import com.niit.service.interfaces.ICommentService;
import com.niit.service.interfaces.IContributionService;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.ref.PhantomReference;
import java.sql.Timestamp;

@Transactional
@Service
public class ContributionServiceImpl implements IContributionService {

    @Autowired
    private ContributionDaoImpl contributionDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private JSONUtil jsonUtil;

    public String getMyContribution(int uid) {
//        System.out.println(" getMyContribution "+contributionDao.selectByUid(uid).toString());
        return jsonUtil.toJSon(contributionDao.selectByUid(uid));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addContribution(ContributionEntity contributionEntity) {
        contributionEntity.setStatus(0);
        return contributionDao.addContribution(contributionEntity);
    }

}
