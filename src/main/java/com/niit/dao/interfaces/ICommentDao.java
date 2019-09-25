package com.niit.dao.interfaces;

import com.niit.entity.CommentEntity;

import java.util.List;

public interface ICommentDao {

    /**
     * 增加评论
     *
     * @param commentEntity
     * @return
     */
    int addComment(CommentEntity commentEntity);

    /**
     * 根据cid查询评论
     *
     * @param cid
     * @return
     */
    CommentEntity selectByCid(int cid);

    /**
     * 根据vid查询评论
     *
     * @param vid
     * @return
     */
    List<CommentEntity> selectByVid(int vid);

    /**
     * 根据rid查询评论
     *
     * @param rid
     * @return
     */
    List<CommentEntity> selectByRid(int rid);

    /**
     * 根据uid查询评论
     *
     * @param uid
     * @return
     */
    List<CommentEntity> selectByUid(int uid);

    /**
     * @param status 状态（0正常，1待审核，2禁止访问）
     * @return
     */
    List<CommentEntity> selectByStatus(int status);

    /**
     * 修改评论
     *
     * @param commentEntity
     * @return
     */
    int alterComment(CommentEntity commentEntity);

    /**
     * 分页查询
     *
     * @param maxResults 每一页的最大结果数
     * @param targetPage 期望的第几页
     * @param vid        视频vid
     * @return
     */
    String selectByPage(int maxResults, int targetPage, int vid);

    /**
     * 查询是否有未读消息
     * @param uid
     * @return true 有未读消息
     */
    boolean selectByMark(int uid);
}
