package com.niit.dao.interfaces;

import com.niit.entity.FavoriteEntity;

import java.util.List;

public interface IFavoriteDao {
    /**
     * 增加收藏
     *
     * @param favoriteEntity
     * @return
     */
    int addFavorite(FavoriteEntity favoriteEntity);

    /**
     * 删除收藏
     *
     * @param uid
     * @param vid
     * @return
     */
    int delectFavorite(int uid, int vid);

    /**
     * 根据uid查询收藏
     *
     * @param uid
     * @return
     */
    List<FavoriteEntity> selectByUid(int uid);

    Boolean checkFavorite(int uid,int vid);
}
