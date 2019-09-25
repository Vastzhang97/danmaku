package com.niit.service.interfaces;

import com.niit.entity.FavoriteEntity;

public interface IFavoriteService {

    /**
     * 得到收藏表
     *
     * @param uid
     * @return
     */
    String getMyFavorite(int uid);

    /**
     * 增加新收藏
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
    int deleteFavorite(int uid, int vid);
}
