package com.niit.websocket;

import com.niit.entity.DanmakuEntity;
import com.niit.entity.VideoEntity;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoWebSocket extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(VideoWebSocket.class);
    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private SessionFactory sessionFactory;
    //对应视频的在线用户列表
    private static final Map<Integer, Map> videosMap;//<vid,Map>
    //在线用户列表
    private static final Map<Integer, WebSocketSession> usersMap;//<uid,WebSocketSession>

    static {
        videosMap = new HashMap<>();
        usersMap = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        int vid = getVid(session);
        if (getUid(session) != null) {
            int uid = getUid(session);
            usersMap.put(uid, session);
            if (videosMap.get(vid) == null) {
                videosMap.put(vid, usersMap);
            } else {
                videosMap.replace(vid, usersMap);
            }
//            System.out.println("usersMap.size() " + usersMap.size());
//            System.out.println("videosMap.size " + videosMap.size());
//            if (session.isOpen()) {
//                session.sendMessage(new TextMessage("{\"onlineNum\":" + videosMap.get(vid).size() + "}"));
//            }
        } else {
            try {
                session.sendMessage(new TextMessage("{\"onlineNum\":" + 1 + "}"));//TODO 计算未登录时的在线人数
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("afterConnectionEstablished()");
                logger.warn(e);
            }
        }
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
//        System.out.println("收到的消息 " + message.getPayload());
        sendMessageToOnlineUsers(message, session);
//        messageEntity.getRid()
    }

    /**
     * 广播信息
     *
     * @param message
     * @return
     */
    public boolean sendMessageToOnlineUsers(TextMessage message, WebSocketSession session) {
        boolean allSendSuccess = true;
        String messageStr = message.getPayload();
        int currenttime = Integer.valueOf(messageStr.substring(messageStr.indexOf("currenttime") + 13, messageStr.indexOf("color") - 2));
        Map<Integer, WebSocketSession> onlineUser = videosMap.get(getVid(session));
        for (Map.Entry<Integer, WebSocketSession> sessionEntry : onlineUser.entrySet()) {
            try {
                if (sessionEntry.getValue().isOpen()) {
                    DanmakuEntity newDanmakuEntity = jsonUtil.readValue(message.getPayload(), DanmakuEntity.class);
                    newDanmakuEntity.setDbCurrenttime(formatDateTime(currenttime));
//                    System.out.println("newDanmakuEntity " + newDanmakuEntity);
                    DanmakuEntity danmakuEntity = sendDanmaku(newDanmakuEntity, session);
                    String json = jsonUtil.toJSon(danmakuEntity);
                    sessionEntry.getValue().sendMessage(new TextMessage(json));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allSendSuccess;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        if (session.isOpen()) {
//            session.close();
            int vid = getVid(session);
            if (getUid(session) != null) {
                videosMap.get(vid).remove(getUid(session));
            }
        }
        System.out.println("连接出错");
//        videosMap.replace()
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if (session.isOpen()) {
            if (getUid(session) != null) {
                int vid = getVid(session);
                videosMap.get(vid).remove(getUid(session));
            }
        }
        System.out.println("session id:" + session.getId() + "连接已关闭");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    int getVid(WebSocketSession session) {
        String[] url = session.getUri().toString().split("=");
        return Integer.valueOf(url[1]);
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

    private DanmakuEntity sendDanmaku(DanmakuEntity danmakuEntity, WebSocketSession webSocketSession) {
        Session hibernateSession = sessionFactory.openSession();
        Transaction t = hibernateSession.beginTransaction();
        Serializable did = 0;
        try {
            danmakuEntity.setDbDate(new Timestamp(System.currentTimeMillis()));
            danmakuEntity.setStatus(0);
            danmakuEntity.setLikenum(0);
            did = hibernateSession.save(danmakuEntity);
            VideoEntity videoEntity = hibernateSession.get(VideoEntity.class, getVid(webSocketSession));
            videoEntity.setDanmakunum(videoEntity.getDanmakunum() + 1);
            hibernateSession.update(videoEntity);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            logger.warn("插入新弹幕异常");
            logger.warn(e);
        } finally {
            if (hibernateSession.isOpen()) {
                hibernateSession.close();
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dd = format.format(danmakuEntity.getDbDate());
            danmakuEntity.setDate(dd);
            danmakuEntity.setDid((Integer) did);
            return danmakuEntity;
        }
    }

    /**
     * 将秒转为Time类型
     *
     * @param mss 秒数
     * @return
     */
    public Time formatDateTime(int mss) {
        int hours = (mss % (60 * 60 * 24)) / (60 * 60);
        int minutes = (mss % (60 * 60)) / 60;
        int seconds = mss % 60;
        return new Time(hours, minutes, seconds);
    }
}