package com.niit.service.impl;

import com.niit.controller.MainController;
import com.niit.dao.impl.CommentDaoImpl;
import com.niit.dao.impl.UserDaoImpl;
import com.niit.dao.impl.VideoDaoImpl;
import com.niit.entity.CommentEntity;
import com.niit.entity.UserEntity;
import com.niit.entity.VideoEntity;
import com.niit.service.interfaces.ICommentService;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;

@Transactional
@Service
public class CommentServiceImpl implements ICommentService {

    private static Logger logger = Logger.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentDaoImpl commentDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private JSONUtil jsonUtil;


    public String getComment(int vid) {
        try {
            List<CommentEntity> commentEntityList = commentDao.selectByVid(vid);
            List<CommentEntity> jsonList = new ArrayList<>();
            for (CommentEntity commentEntity : commentEntityList) {
                UserEntity userEntity = userDao.selectByUid(commentEntity.getUid());
                commentEntity.setsHeadshot(userEntity.getHeadshot());
                commentEntity.setsUname(userEntity.getUname());
                jsonList.add(commentEntity);
            }
            return jsonUtil.toJSon(jsonList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getComment()异常");
            logger.warn(e);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String addComment(CommentEntity commentEntity) {
        commentEntity.setDate(new Timestamp(System.currentTimeMillis()));
        commentEntity.setLikenum(0);
        commentEntity.setMark(0);
        int cid = commentDao.addComment(commentEntity);
        CommentEntity commentEntity1 = commentDao.selectByCid(cid);
        UserEntity userEntity = userDao.selectByUid(commentEntity1.getUid());
        commentEntity1.setsHeadshot(userEntity.getHeadshot());
        commentEntity1.setsUname(userEntity.getUname());
        return jsonUtil.toJSon(commentEntity1);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int replyComment(int cid, CommentEntity commentEntity) {
        commentEntity.setRcid(cid);
        return commentDao.addComment(commentEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int reportComment(int cid) {
        CommentEntity commentEntity = commentDao.selectByCid(cid);
        commentEntity.setStatus(1);
        return commentDao.alterComment(commentEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getMyReply(int uid) {
        try {
            List<CommentEntity> commentList = new ArrayList<CommentEntity>();
            //查询uid投稿的视频的评论
            List<VideoEntity> videoEntityList = videoDao.selectByUid(uid);
            for (VideoEntity videoEntity : videoEntityList) {
                List<CommentEntity> db_commentEntityList = commentDao.selectByVid(videoEntity.getVid());
                for (CommentEntity commentEntity : db_commentEntityList) {
                    if (commentEntity.getRcid() == null) {
                        commentEntity.setVideoTitle(videoEntity.getTitle());
                        UserEntity userEntity = userDao.selectByUid(commentEntity.getUid());
                        commentEntity.setsUname(userEntity.getUname());
                        commentEntity.setsHeadshot(userEntity.getHeadshot());
//                        commentEntity.setMark(0);
                        commentList.add(commentEntity);
                    }
                }
            }
            //查询回复uid的评论的评论
            List<CommentEntity> commentEntityList = commentDao.selectByRid(uid);
            for (CommentEntity commentEntity : commentEntityList) {
                CommentEntity rCommentEntity = commentDao.selectByCid(commentEntity.getRcid());
                UserEntity userEntity = userDao.selectByUid(commentEntity.getUid());
                commentEntity.setrContent(rCommentEntity.getContent());
                commentEntity.setsUname(userEntity.getUname());
                commentEntity.setsHeadshot(userEntity.getHeadshot());
                commentEntity.setMark(rCommentEntity.getMark());
                commentList.add(commentEntity);
            }

            for (CommentEntity commentEntity : commentList) {
                if (commentEntity.getMark() == 0) {
                    commentEntity.setMark(1);
                    commentDao.alterComment(commentEntity);
                }
            }
            return jsonUtil.toJSon(commentList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getMyReply()异常");
            logger.warn(e);
        }
        return JSONUtil.jsonResult(FAILEDCODE);
    }
}
