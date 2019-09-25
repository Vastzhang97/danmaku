package com.niit.websocket;

import com.niit.dao.impl.MessageDaoImpl;
import com.niit.entity.MessageEntity;
import com.niit.entity.UserEntity;
import com.niit.service.TimedTaskService;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.xml.soap.Text;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MessageWebSocket extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(MessageWebSocket.class);
    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private SessionFactory sessionFactory;
    //在线用户列表
    private static final Map<Integer, WebSocketSession> users;

    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        Integer uid = getUid(session);
        System.out.println("成功建立MessageWebSocket " + session.getId() + " 连接");
//        System.out.println("afterConnectionEstablished建立连接的id是 " + uid);
        if (uid != null) {
            users.put(uid, session);
//            session.sendMessage(new TextMessage("成功建立MessageWebSocket连接"));
        }
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        System.out.println("收到的消息 " + message.getPayload());
        MessageEntity messageEntity = jsonUtil.readValue(message.getPayload(), MessageEntity.class);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));

        String resultJson = jsonUtil.toJSon(messageEntity);
        TextMessage message1 = new TextMessage(resultJson);

        boolean result = sendMessageToUser(messageEntity.getSid(), messageEntity.getRid(), message1);
        if (result) {
            messageEntity.setStatus(1);
            messageEntity = addMessage(messageEntity);
        } else {
            messageEntity.setStatus(0);
            messageEntity = addMessage(messageEntity);
        }
        try {
            //返回消息给当前用户
//            String resultJson = jsonUtil.toJSon(messageEntity);
//            TextMessage message1 = new TextMessage(resultJson);
            System.out.println("message1 " + message1.getPayload());
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送信息给指定用户
     *
     * @param sid     发送消息的用户id
     * @param rid     接收消息的用户id
     * @param message
     * @return true 用户在线发送消息,false用户不在线
     */
    public boolean sendMessageToUser(Integer sid, Integer rid, TextMessage message) {
        int uid = rid;
        if (users.get(uid) == null) return false;
        WebSocketSession session = users.get(uid);
        if (!session.isOpen()) return false;
        try {
            Session hibernateSession = sessionFactory.openSession();
            MessageEntity messageEntity = jsonUtil.readValue(message.getPayload(), MessageEntity.class);
            hibernateSession.beginTransaction();
            UserEntity sendUserEntity = hibernateSession.get(UserEntity.class, messageEntity.getSid());
            messageEntity.setpUname(sendUserEntity.getUname());
            hibernateSession.getTransaction().commit();
            hibernateSession.close();

            String resultJson = jsonUtil.toJSon(messageEntity);
            TextMessage message1 = new TextMessage(resultJson);
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<Integer> uidSet = users.keySet();
        WebSocketSession session = null;
        for (Integer rid : uidSet) {
            try {
                session = users.get(rid);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        if (session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("handleTransportError！");
                logger.warn(e);
            }
        }
        System.out.println("连接出错");
        users.remove(getUid(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        System.out.println("连接已关闭：" + session.getId());
        users.remove(getUid(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
    private Integer getUid(WebSocketSession session) {
        try {
            Integer uid = (Integer) session.getAttributes().get("uid");
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getUid()异常");
            logger.warn(e);
            return null;
        }
    }


    private MessageEntity addMessage(MessageEntity messageEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserEntity sendUserEntity = session.get(UserEntity.class, messageEntity.getSid());
        messageEntity.setpUname(sendUserEntity.getUname());
        session.save(messageEntity);
        session.getTransaction().commit();
        session.close();
        return messageEntity;
    }
}