package com.niit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.dao.impl.*;
import com.niit.entity.*;
import com.niit.service.interfaces.IHistoryService;
import com.niit.util.CompareHistoryDateUtil;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.niit.util.Constant.FAILEDCODE;

@Transactional
@Service
public class HistoryServiceImpl implements IHistoryService {

    private static Logger logger = Logger.getLogger(FavoriteServiceImpl.class);
    @Autowired
    private HistoryDaoImpl historyDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private MessageDaoImpl messageDao;
    @Autowired
    private CommentDaoImpl commentDao;
    @Autowired
    private LikeDaoImpl likeDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private JSONUtil jsonUtil;

    public String getNavigationBar(int uid) {
        List<Integer> dbVidList = historyDao.selectByUid(uid);
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        ObjectMapper objectMapper = jsonUtil.objectMapper;
        objectMapper.setDateFormat((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));//解决Jackson Time格式乱码
        for (Integer vid : dbVidList) {
            List<HistoryEntity> historyEntityList1 = historyDao.selectByUidAndVid(uid, vid);
            HistoryEntity historyEntity = historyEntityList1.get(0);
            VideoEntity videoEntity = videoDao.selectByVid(historyEntity.getVid());
            historyEntity.setWebpage(videoEntity.getWebpage());
            historyEntity.setTitle(videoEntity.getTitle());
            historyEntityList.add(historyEntity);
        }

        Boolean hasUnReadMessage = false;

        if (messageDao.selectCountBySidOrRid(uid, uid) > 0) {//检查消息
            hasUnReadMessage = true;
        }

        CompareHistoryDateUtil dateUtil = new CompareHistoryDateUtil();//对历史记录按照时间进行排序
        Collections.sort(historyEntityList, dateUtil);

        boolean hasUnreadReply = checkUnreadReply(uid);//检查评论
        if (hasUnreadReply) {
            hasUnReadMessage = true;
        }

        boolean checkUnreadLike = likeDao.selectBySatatus(uid);//检查点赞
        if (checkUnreadLike) {
            hasUnReadMessage = true;
        }
        Map map = new HashMap();
        map.put("historyList", historyEntityList);
        map.put("hasUnread", hasUnReadMessage);
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addHistroy(HistoryEntity historyEntity) {
        historyEntity.setDate(new Timestamp(System.currentTimeMillis()));
        return historyDao.addHistory(historyEntity);
    }

    /**
     * @param uid
     * @return true 有未读回复
     */
    private Boolean checkUnreadReply(int uid) {
        try {
            List<CommentEntity> commentList = new ArrayList<CommentEntity>();
            //查询uid投稿的视频的评论
            List<VideoEntity> videoEntityList = videoDao.selectByUid(uid);
            for (VideoEntity videoEntity : videoEntityList) {
                List<CommentEntity> commentEntityList = commentDao.selectByVid(videoEntity.getVid());
                for (CommentEntity commentEntity : commentEntityList) {
                    if (commentEntity.getRcid() == null) {
                        commentEntity.setVideoTitle(videoEntity.getTitle());
                        UserEntity userEntity = userDao.selectByUid(commentEntity.getUid());
                        commentEntity.setsUname(userEntity.getUname());
                        commentEntity.setsHeadshot(userEntity.getHeadshot());
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
                commentList.add(commentEntity);
            }

            for (CommentEntity commentEntity : commentEntityList) {
                if (commentEntity.getMark() == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("checkUnreadReply()异常");
            logger.warn(e);
        }
        return false;
    }
}
