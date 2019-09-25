package com.niit.service.interfaces;

public interface ILikeService {

    /**
     * 得到视频点赞表
     */
    public void getLikeByVideo();
    /**
     * 得到弹幕点赞表
     */
    public void getLikeByDanmaku();
    /**
     * 得到评论点赞表
     */
    public void getLikeByComment();

    /**
     * 增加视频点赞
     */
    public void addLikeByVideo();
    /**
     * 增加弹幕点赞
     */
    public void addLikeByDanmaku();
    /**
     * 增加评论点赞
     */
    public void addLikeByComment();

    /**
     * 删除点赞
     */
    public void deleteLike();

    /**
     * 取消视频点赞
     */
    public void deleteLikeByVideo();
    /**
     * 取消弹幕点赞
     */
    public void deleteLikeByDanmaku();
    /**
     * 取消评论点赞
     */
    public void deleteLikeByComment();
}
