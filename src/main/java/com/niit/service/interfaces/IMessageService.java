package com.niit.service.interfaces;

import com.niit.entity.MessageEntity;

import java.util.List;

public interface IMessageService {

    /**
     * 得到用户收到的消息
     *
     * @param rid 接受者id
     * @return
     */
    String getMyMessage(int rid);

    /**
     * 得到用户接收到的系统信息
     *
     * @param rid
     * @return
     */
    String getNotification(int rid);


    /**
     * 回复消息
     *
     * @param messageEntity
     * @return
     */
    int replyMessage(MessageEntity messageEntity);

    /**
     * 标记消息已读
     *
     * @param mid
     * @return
     */
    int markReadMessage(int mid);

    /**
     * 得到用户未读的点赞信息
     *
     * @param uid
     * @return
     */
    String getMylike(int uid);

    /**
     * 标记点赞信息已读
     *
     * @param lid
     * @return
     */
    int markReadLike(int lid);

    /**
     * 发送新消息
     *
     * @param messageEntity
     * @return
     */
    int addMessage(MessageEntity messageEntity);

    /**
     * 得到与用户有过消息的人
     *
     * @param uid
     * @return
     */
    String getPrivateMsgUser(int uid);

    /**
     * 得到用户与另一用户的私信消息
     *
     * @param uid
     * @param oUid 另一用户
     * @return
     */
    String readPrivateMsg(int uid, int oUid);
}
