package com.niit.dao.interfaces;

import com.niit.entity.ContributionEntity;

import java.util.List;

public interface IContributionDao {

    int addContribution(ContributionEntity contributionEntity);

    ContributionEntity selectByCid(int cid);

    List<ContributionEntity> selectByUid(int uid);

    int alterContribution(ContributionEntity contributionEntity);

    /**
     *
     * @param status 状态（0正常，1待审核，2禁止访问）
     * @return
     */
    List<ContributionEntity> selectByStatus(int status);
}
