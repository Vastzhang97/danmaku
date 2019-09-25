package com.niit.dao.interfaces;

import com.niit.entity.MessageEntity;

import java.util.List;

public interface IMessageDao {

    /**
     * 增加信息
     *
     * @param messageEntity
     * @return
     */
    int addMessage(MessageEntity messageEntity);

    /**
     * 根据mid查询
     *
     * @param mid
     * @return
     */
    MessageEntity selectByMid(int mid);

    /**
     * 根据发送者sid查询
     *
     * @param sid    发送者id
     * @return
     */
    List<MessageEntity> selectBySid(int rid, int sid);

    /**
     * 根据接收者rid查询
     *
     * @param rid
     * @param status （0未读，1已读）
     * @return
     */
    List<MessageEntity> selectByRid(int rid, int status);

    /**
     * 修改消息
     *
     * @param messageEntity
     * @return
     */
    int alterMessage(MessageEntity messageEntity);

    /**
     * @param uid
     * @return
     */
    List<MessageEntity> selectBySidOrRid(int uid);

    /**
     * @param sid
     * @param rid
     * @return
     */
    List<MessageEntity> selectBySidAndRid(int sid, int rid);

    int selectCountBySidOrRid(int sid, int rid);
}
