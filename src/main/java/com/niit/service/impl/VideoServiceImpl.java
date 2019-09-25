package com.niit.service.impl;

import com.niit.dao.impl.*;
import com.niit.entity.*;
import com.niit.service.interfaces.IVideoService;
import com.niit.util.JSONUtil;
import com.niit.util.PageEntity;
import com.niit.util.PageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Transactional
@Service
public class VideoServiceImpl implements IVideoService {

    private static Logger logger = Logger.getLogger(VideoServiceImpl.class);
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private LikeDaoImpl likeDao;
    @Autowired
    private DanmakuDaoImpl danmakuDao;
    @Autowired
    private CommentDaoImpl commentDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private FavoriteDaoImpl favoriteDao;
    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private PageUtil pageUtil;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addVideo(VideoEntity videoEntity) {
        return videoDao.addVideo(videoEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int gradeVideo(int vid, int grade) {
        VideoEntity videoEntity = videoDao.selectByVid(vid);
        int num = videoEntity.getGradenum();
        int oldGrade = videoEntity.getGrade();
        int newGrade = (oldGrade + grade) / (num + 1);
        videoEntity.setGrade(newGrade);
        videoEntity.setGradenum(num + 1);
        return videoDao.alterVideo(videoEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int reportVideo(int vid) {
        VideoEntity videoEntity = videoDao.selectByVid(vid);
        videoEntity.setStatus(1);
        return videoDao.alterVideo(videoEntity);
    }

    public String searchVideo(String key, int category) {
        int everyPageNum = 16;
        int currentPage = 1;
        int totalCount = videoDao.selectCountByTitle(key, category);
        PageEntity pageEntity = pageUtil.createPage(everyPageNum, totalCount, currentPage);
        List<VideoEntity> list = videoDao.selectByTitle(key, category, everyPageNum);
        Map map = new HashMap();
        map.put("videoList", list);
        map.put("page", pageEntity);
        return jsonUtil.toJSon(map);
    }

    public String searchVideoByCategoryAndType(String key, int category, int type, int currentPage) {
        int everyPageNum = 16;
        int firstResult = everyPageNum * (currentPage - 1);
        int totalCount = videoDao.selectCountByCategoryAndType(key, category, type);
        PageEntity pageEntity = pageUtil.createPage(everyPageNum, totalCount, currentPage);
        List list = videoDao.selectByCategoryAndType(key, category, type, firstResult, everyPageNum);
        Map map = new HashMap();
        map.put("videoList", list);
        map.put("page", pageEntity);
        return jsonUtil.toJSon(map);
    }

    @Transactional(readOnly = true)
    public String getRecommendVideo() {
        List<VideoEntity> jsonList = new ArrayList<VideoEntity>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                List<VideoEntity> videoEntityList = videoDao.selectByView(i);
                for (VideoEntity videoEntity : videoEntityList) {
                    videoEntity.setCategory(0);
                    jsonList.add(videoEntity);
                }
            } else {
                List<VideoEntity> videoEntityList = videoDao.selectByView(i);
                jsonList.addAll(videoEntityList);
            }
        }
        return jsonUtil.toJSon(jsonList);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int likeVideo(int vid, int uid) {
        try {
            VideoEntity videoEntity = videoDao.selectByVid(vid);
            videoEntity.setLikenum(videoEntity.getLikenum() + 1);
            videoDao.alterVideo(videoEntity);
            TbLikeEntity likeEntity = new TbLikeEntity();
            likeEntity.setVid(vid);
            likeEntity.setUid(uid);
            likeEntity.setDate(getCurrentTime());
            likeDao.addLike(likeEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("likeVideo()异常");
            logger.warn(e);
        }
        return FAILEDCODE;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int likeDanmaku(int did, int uid) {
        try {
            DanmakuEntity danmakuEntity = danmakuDao.selectByDid(did);
            danmakuEntity.setLikenum(danmakuEntity.getLikenum() + 1);
            danmakuDao.alterDanmaku(danmakuEntity);
            TbLikeEntity likeEntity = new TbLikeEntity();
            likeEntity.setDid(did);
            likeEntity.setUid(uid);
            likeEntity.setDate(getCurrentTime());
            likeDao.addLike(likeEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("likeDanmaku()异常");
            logger.warn(e);
        }
        return FAILEDCODE;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int likeComment(int cid, int uid) {
        try {
            CommentEntity commentEntity = commentDao.selectByCid(cid);
            commentEntity.setLikenum(commentEntity.getLikenum() + 1);
            commentDao.alterComment(commentEntity);
            TbLikeEntity likeEntity = new TbLikeEntity();
            likeEntity.setCid(cid);
            likeEntity.setUid(uid);
            likeEntity.setDate(getCurrentTime());
            likeEntity.setStatus(0);
            likeDao.addLike(likeEntity);
            return commentEntity.getLikenum();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("likeComment()异常");
            logger.warn(e);
        }
        return -1;
    }

    public String getMyVideo(int uid) {
        try {
            return jsonUtil.toJSon(videoDao.selectByUid(uid));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getMyVideo()异常");
            logger.warn(e);
        }
        return null;
    }

    @Override
    public String checkStatus(int uid, int vid) {
        List<Integer> commentUserIdList = new ArrayList<>();
        List<Integer> hasFocusUserIdList = new ArrayList<>();
        List<CommentEntity> commentEntityList = commentDao.selectByVid(vid);
        List<TbLikeEntity> tbLikeEntityList1 = likeDao.selectByUid(uid, -1);
        List<Integer> cidList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
//            if (commentEntity.getUid() == uid) {
                for (TbLikeEntity likeEntity : tbLikeEntityList1) {
                    if (likeEntity.getCid() != null) {
                        if (likeEntity.getCid() == commentEntity.getCid()) {
                            cidList.add(likeEntity.getCid());//用户已点赞的评论
                        }
                    }
                }
//            }
            if (commentEntity.getRid() != null) {
                if (!commentUserIdList.contains(commentEntity.getRid())) {
                    commentUserIdList.add(commentEntity.getRid());
                }
            }
            if (!commentUserIdList.contains(commentEntity.getUid())) {
                commentUserIdList.add(commentEntity.getUid());
            }
        }
        for (Integer commentUserId : commentUserIdList) {
            Boolean hasFocusUser = userDao.selectByUidAndFid(commentUserId, uid);
            if (hasFocusUser) {
                hasFocusUserIdList.add(commentUserId);
            }
        }

        VideoEntity videoEntity = videoDao.selectByVid(vid);
        Boolean focesUser = userDao.checkFocus(videoEntity.getUid(), uid);//用户是否关注作者
        Boolean favoriteVideo = favoriteDao.checkFavorite(uid, vid);//用户是否收藏视频
        Boolean hasLikeVideo = likeDao.selectByUidAndVid(uid, vid);

        List<DanmakuEntity> danmakuEntityList = danmakuDao.selectByVid(vid);
        List<Integer> didList = new ArrayList<>();
        for (DanmakuEntity danmakuEntity : danmakuEntityList) {
            List<TbLikeEntity> tbLikeEntityList = likeDao.selectByDid(danmakuEntity.getDid(), -1);
            for (TbLikeEntity likeEntity : tbLikeEntityList) {
                didList.add(likeEntity.getDid());//用户点赞过的弹幕did
            }
        }

        Map jsonMap = new HashMap<>();
        jsonMap.put("cid", cidList);
        jsonMap.put("did", didList);
        jsonMap.put("hasFocuesUserId", hasFocusUserIdList);
        jsonMap.put("focuesAuthor", focesUser);
        jsonMap.put("hasLikeVideo", hasLikeVideo);
        jsonMap.put("favoriteVideo", favoriteVideo);
        return jsonUtil.toJSon(jsonMap);
    }

    public String getVideoInfo(int vid) {
        try {
            VideoEntity videoEntity = videoDao.selectByVid(vid);
            videoEntity.setView(videoEntity.getView() + 1);
            videoDao.alterVideo(videoEntity);
            return jsonUtil.toJSon(videoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getVideoInfo()异常");
            logger.warn(e);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
