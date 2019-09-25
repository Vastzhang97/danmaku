package com.niit.service.impl;

import com.niit.controller.MainController;
import com.niit.dao.impl.FavoriteDaoImpl;
import com.niit.dao.impl.UserDaoImpl;
import com.niit.dao.impl.VideoDaoImpl;
import com.niit.entity.FavoriteEntity;
import com.niit.entity.VideoEntity;
import com.niit.service.interfaces.IFavoriteService;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FavoriteServiceImpl implements IFavoriteService {

    private static Logger logger = Logger.getLogger(FavoriteServiceImpl.class);
    @Autowired
    private FavoriteDaoImpl favoriteDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private JSONUtil jsonUtil;

    public String getMyFavorite(int uid) {
        try {
            List<FavoriteEntity> flist = favoriteDao.selectByUid(uid);
            List<VideoEntity> vlist = new ArrayList<VideoEntity>();
            for (FavoriteEntity favoriteEntity : flist) {
                VideoEntity videoEntity = videoDao.selectByVid(favoriteEntity.getVid());
                videoEntity.setAuthor(userDao.selectByUid(videoEntity.getUid()).getUname());
                vlist.add(videoEntity);
            }
            return jsonUtil.toJSon(vlist);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getMyFavorite()异常");
            logger.warn(e);
        }
        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addFavorite(FavoriteEntity favoriteEntity) {
        favoriteEntity.setDate(new Timestamp(System.currentTimeMillis()));
        return favoriteDao.addFavorite(favoriteEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteFavorite(int uid, int vid) {
        return favoriteDao.delectFavorite(uid, vid);
    }
}
