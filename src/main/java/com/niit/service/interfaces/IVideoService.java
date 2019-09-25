package com.niit.service.interfaces;

import com.niit.entity.VideoEntity;

public interface IVideoService {

    /**
     * 增加新视频
     *
     * @param videoEntity
     * @return
     */
    int addVideo(VideoEntity videoEntity);

    /**
     * 对视频评分
     *
     * @param vid
     * @param grade
     * @return
     */
    int gradeVideo(int vid, int grade);

    /**
     * 举报视频
     *
     * @param vid
     * @return
     */
    int reportVideo(int vid);

    /**
     * 搜索视频
     *
     * @param key
     * @param category
     * @return
     */
    String searchVideo(String key, int category);

    /**
     * 根据分类搜索对应最高的视频
     *
     * @param category 0-7对应全部，动画，音乐，游戏，鬼畜，舞蹈，体育，科技
     * @param type     8-11对应最高人气，最新发布，最多播放，最多弹幕
     * @return
     */
    String searchVideoByCategoryAndType(String key, int category, int type,int currentPage);

    /**
     * 得到推荐视频
     *
     * @return
     */
    String getRecommendVideo();

    /**
     * 点赞视频
     *
     * @param vid
     * @param uid
     * @return
     */
    int likeVideo(int vid, int uid);

    /**
     * 点赞弹幕
     *
     * @param did
     * @param uid
     * @return 当前点赞数
     */
    int likeDanmaku(int did, int uid);

    /**
     * 点赞评论
     *
     * @param cid
     * @param uid
     * @return
     */
    int likeComment(int cid, int uid);

    /**
     * 得到用户发布的视频
     *
     * @param uid
     * @return
     */
    String getMyVideo(int uid);

    /**
     * 检查用户是否有收藏过该视频,关注作者，点赞视频，点赞弹幕
     *
     * @param uid
     * @param vid
     * @return
     */
    String checkStatus(int uid, int vid);

    /**
     * 获得视频信息
     * @param vid
     * @return
     */
    String getVideoInfo(int vid);

}
