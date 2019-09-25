package com.niit.service.interfaces;

import com.niit.entity.DanmakuEntity;

public interface IDanmakuService {

    /**
     * 得到视频的弹幕表
     * @param vid
     * @return
     */
    String getDanmakuByVid(int vid);

    /**
     * 增加新弹幕
     * @param danmakuEntity
     * @return
     */
    int addDanmaku(DanmakuEntity danmakuEntity);

    /**
     * 举报弹幕
     * @param did
     * @return
     */
    int reportDanmaku(int did);

    /**
     * 删除弹幕
     */
    void deleteDanmaku();
}
