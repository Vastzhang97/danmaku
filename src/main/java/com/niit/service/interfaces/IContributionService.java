package com.niit.service.interfaces;

import com.niit.entity.ContributionEntity;

public interface IContributionService {

    /**
     * 得到投稿表
     *
     * @param uid
     * @return
     */
    String getMyContribution(int uid);

    /**
     * 投稿新视频
     *
     * @param contributionEntity
     * @return
     */
    int addContribution(ContributionEntity contributionEntity);

}
