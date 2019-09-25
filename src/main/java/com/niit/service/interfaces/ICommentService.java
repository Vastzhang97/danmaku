package com.niit.service.interfaces;

import com.niit.entity.CommentEntity;

public interface ICommentService {

    /**
     * 得到评论
     *
     * @param vid
     * @return
     */
    String getComment(int vid);

    /**
     * 增加评论
     *
     * @param commentEntity
     * @return
     */
    String addComment(CommentEntity commentEntity);

    /**
     * 回复评论
     *
     * @param cid
     * @param commentEntity
     * @return
     */
    int replyComment(int cid, CommentEntity commentEntity);

    /**
     * 举报评论
     *
     * @param cid
     * @return
     */
    int reportComment(int cid);

    /**
     * 得到用户发布评论
     *
     * @param uid
     * @return
     */
    String getMyReply(int uid);

}
