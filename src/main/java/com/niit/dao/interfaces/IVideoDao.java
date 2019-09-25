package com.niit.dao.interfaces;

import com.niit.entity.VideoEntity;

import java.util.List;

public interface IVideoDao {

    VideoEntity selectByVid(int vid);

    List<VideoEntity> selectByUid(int uid);

    List<VideoEntity> selectByCategory(int category);

    List<VideoEntity> selectByPopularitynum(int category);

    List<VideoEntity> selectByDate(int category);

    List<VideoEntity> selectByView(int category);

    List<VideoEntity> selectByDanmakunum(int category);

    List<VideoEntity> selectByTitle(String key, int category, int everyPageNum);

    int selectCountByTitle(String key, int category);

    /**
     * @param category 0-7对应全部，动画，音乐，游戏，鬼畜，舞蹈，体育，科技
     * @param type     8-11对应最高人气，最新发布，最多播放，最多弹幕
     * @return
     */
    List<VideoEntity> selectByCategoryAndType(String key, int category, int type, int firstResult, int everyPageNum);

    /**
     * @param category 0-7对应全部，动画，音乐，游戏，鬼畜，舞蹈，体育，科技
     * @param type     8-11对应最高人气，最新发布，最多播放，最多弹幕
     * @return
     */
    int selectCountByCategoryAndType(String key, int category, int type);

    int addVideo(VideoEntity videoEntity);

    int alterVideo(VideoEntity videoEntity);

    List<VideoEntity> selectByUidAndVid(int uid, int vid);

}
