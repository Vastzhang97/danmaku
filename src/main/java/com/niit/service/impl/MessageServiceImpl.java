package com.niit.service.impl;

import com.niit.dao.impl.*;
import com.niit.entity.*;
import com.niit.service.interfaces.IMessageService;
import com.niit.util.CompareMessageDateUtil;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Transactional
@Service
public class MessageServiceImpl implements IMessageService {

    private static Logger logger = Logger.getLogger(MessageServiceImpl.class);
    @Autowired
    private MessageDaoImpl messageDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private LikeDaoImpl likeDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private DanmakuDaoImpl danmakuDao;
    @Autowired
    private CommentDaoImpl commentDao;
    @Autowired
    private JSONUtil jsonUtil;

    public String getMyMessage(int rid) {
        try {
            List<MessageEntity> messageList = messageDao.selectByRid(rid, 0);
            return jsonUtil.toJSon(messageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getMyMessage()异常");
            logger.warn(e);
        }
        return JSONUtil.jsonResult(FAILEDCODE);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getNotification(int rid) {
        try {
            List<UserEntity> adminList = userDao.selectByPrivilege(0);
            List<MessageEntity> messageList = new ArrayList<MessageEntity>();
            for (UserEntity admin : adminList) {
                List<MessageEntity> dbMessageList = messageDao.selectBySid(rid, admin.getUid());
                messageList.addAll(dbMessageList);
            }

            for (MessageEntity messageEntity : messageList) {
                if (messageEntity.getStatus() == 0) {
                    messageEntity.setStatus(1);
                    messageDao.alterMessage(messageEntity);
                }
            }
            return jsonUtil.toJSon(messageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getNotification()异常");
            logger.warn(e);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    public int replyMessage(MessageEntity messageEntity) {
        return 0;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int markReadMessage(int mid) {
        try {
            MessageEntity messageEntity = messageDao.selectByMid(mid);
            messageEntity.setStatus(1);
            messageDao.alterMessage(messageEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("markReadMessage()异常");
            logger.warn(e);
        }
        return FAILEDCODE;
    }

    public String getMylike(int uid) {
        try {
            List<TbLikeEntity> dbLikeList = likeDao.selectByUid(uid, -1);
            List<TbLikeEntity> likeResultList = new ArrayList<>();
            for (TbLikeEntity likeEntity : dbLikeList) {
                if (likeEntity.getVid() != null) {
                    VideoEntity videoEntity = videoDao.selectByVid(likeEntity.getVid());
                    likeEntity.setType("视频");
                    likeEntity.setContext(videoEntity.getTitle());
                    likeEntity.setStatus(0);
                }
                if (likeEntity.getCid() != null) {
                    CommentEntity commentEntity = commentDao.selectByCid(likeEntity.getCid());
                    likeEntity.setType("评论");
                    likeEntity.setContext(commentEntity.getContent());
                    likeEntity.setStatus(0);
                }
                if (likeEntity.getDid() != null) {
                    DanmakuEntity danmakuEntity = danmakuDao.selectByDid(likeEntity.getDid());
                    likeEntity.setType("弹幕");
                    likeEntity.setContext(danmakuEntity.getContent());
                    likeEntity.setStatus(0);
                }
                likeEntity.setLikeuname(userDao.selectByUid(likeEntity.getUid()).getUname());
                likeResultList.add(likeEntity);
            }
            for (TbLikeEntity likeEntity : likeResultList) {
                if (likeEntity.getStatus() == 0) {
                    likeEntity.setStatus(1);
                    likeDao.alterLike(likeEntity);
                }
            }
            return jsonUtil.toJSon(likeResultList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getMylike()异常");
            logger.warn(e);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int markReadLike(int lid) {
        try {
            TbLikeEntity tbLikeEntity = likeDao.selectByLid(lid);
            tbLikeEntity.setStatus(1);
            likeDao.alterLike(tbLikeEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("markReadLike()异常");
            logger.warn(e);
        }
        return FAILEDCODE;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addMessage(MessageEntity messageEntity) {
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        return messageDao.addMessage(messageEntity);
    }

    @Override
    public String getPrivateMsgUser(int uid) {
        List<MessageEntity> messageEntityList = messageDao.selectBySidOrRid(uid);//查询出两人所有消息
        List<UserEntity> userEntityList = new ArrayList<>();
        List<Integer> userList = new ArrayList<>();
        List<UserEntity> adminList = userDao.selectByPrivilege(0);
        boolean hasUnReadMsg = false;//用户有未读消息
        for (MessageEntity messageEntity : messageEntityList) {

            for (int i = 0; i < adminList.size(); i++) {
                UserEntity adminEntity = adminList.get(i);
                if (messageEntity.getSid() == adminEntity.getUid())//除去管理员发送的消息
                    break;
                if (messageEntity.getStatus() == 0) {
                    hasUnReadMsg = true;
                }
                UserEntity sidUserEntity = userDao.selectByUid(messageEntity.getSid());
                UserEntity ridUserEntity = userDao.selectByUid(messageEntity.getRid());

                if (sidUserEntity.getPrivilege() != 0) {
                    if (!userList.contains(messageEntity.getSid())) {
                        if (messageEntity.getSid() != uid) {
                            userList.add(messageEntity.getSid());
                        }
                    }
                }
                if (ridUserEntity.getPrivilege() != 0) {
                    if (!userList.contains(messageEntity.getRid())) {
                        if (messageEntity.getRid() != uid) {
                            userList.add(messageEntity.getRid());
                        }
                    }
                }

            }

        }
        for (Integer id : userList) {
            if (id != uid) {
                UserEntity userEntity = userDao.selectByUid(id);
                if (hasUnReadMsg) {
                    userEntity.setHasUnRead(0);//有未读
                } else {
                    userEntity.setHasUnRead(1);//无未读
                }
                userEntityList.add(userEntity);
            }
        }

        return jsonUtil.toJSon(userEntityList);
    }


    @Override
    public String readPrivateMsg(int uid, int oUid) {
        List<MessageEntity> messageEntityList1 = messageDao.selectBySidAndRid(uid, oUid);
        List<MessageEntity> messageEntityList2 = messageDao.selectBySidAndRid(oUid, uid);
        List<MessageEntity> resultList = new ArrayList<>();
        resultList.addAll(messageEntityList1);
        resultList.addAll(messageEntityList2);
        UserEntity userEntity1 = userDao.selectByUid(uid);
        UserEntity userEntity2 = userDao.selectByUid(oUid);

        CompareMessageDateUtil dateUtil = new CompareMessageDateUtil(); //对私信按照时间进行排序
        Collections.sort(resultList, dateUtil);

        for (MessageEntity messageEntity : resultList) {
            messageEntity.setStatus(1);
            messageDao.alterMessage(messageEntity);
            if (messageEntity.getSid() == uid) {
                messageEntity.setpHeadshot(userEntity1.getHeadshot());
                messageEntity.setpUname(userEntity1.getUname());
            }
            if (messageEntity.getSid() == oUid) {
                messageEntity.setpHeadshot(userEntity2.getHeadshot());
                messageEntity.setpUname(userEntity2.getUname());
            }
        }

        for (MessageEntity messageEntity : resultList) {
            if (messageEntity.getStatus() == 0) {
                messageEntity.setStatus(1);
                messageDao.alterMessage(messageEntity);
            }
        }

        return jsonUtil.toJSon(resultList);
    }
}
