package com.niit.dao.interfaces;

import com.niit.entity.DanmakuEntity;

import java.util.List;

public interface IDanmakuDao {

    int addDanmaku(DanmakuEntity danmakuEntity);

    DanmakuEntity selectByDid(int did);

    List<DanmakuEntity> selectByVid(int vid);

    List<DanmakuEntity> selectByUid(int uid);

    /**
     * @param status （0正常，1待审核，2禁止访问）
     * @return
     */
    List<DanmakuEntity> selectByStatus(int status);

    int alterDanmaku(DanmakuEntity danmakuEntity);

}
