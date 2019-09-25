package com.niit.dao.interfaces;

import com.niit.entity.TbLikeEntity;

import java.util.List;

public interface ILikeDao {
    /**
     * 增加点赞
     *
     * @param likeEntity
     * @return
     */
    int addLike(TbLikeEntity likeEntity);

    /**
     * 根据lid查询
     *
     * @param lid
     * @return
     */
    TbLikeEntity selectByLid(int lid);

    /**
     * 根据lid查询
     *
     * @param uid
     * @return
     */
    List<TbLikeEntity> selectByUid(int uid, int status);

    /**
     * 根据lid查询
     *
     * @param vid * @param status
     * @return
     */
    List<TbLikeEntity> selectByVid(int vid, int status);

    /**
     * 根据lid查询
     *
     * @param cid * @param status
     * @return
     */
    List<TbLikeEntity> selectByCid(int cid, int status);

    /**
     * 根据lid查询
     *
     * @param did * @param status
     * @return
     */
    List<TbLikeEntity> selectByDid(int did, int status);

    /**
     * 修改Like表
     *
     * @param likeEntity
     * @return
     */
    int alterLike(TbLikeEntity likeEntity);

    /**
     * @param uid
     * @param vid
     * @return
     */
    Boolean selectByUidAndVid(int uid, int vid);

    /**
     *
     * @param uid
     * @return true有未读like
     */
    Boolean selectBySatatus(int uid);
}
