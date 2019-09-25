package com.niit.controller;

import com.niit.aop.CheckPrivilege;
import com.niit.entity.MessageEntity;
import com.niit.service.impl.CommentServiceImpl;
import com.niit.service.impl.FavoriteServiceImpl;
import com.niit.service.impl.MessageServiceImpl;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Controller
@RequestMapping(value = "Message")
public class MessageController {

    @Autowired
    private FavoriteServiceImpl favoriteService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private JSONUtil jsonUtil;

    @CheckPrivilege
    @RequestMapping(value = "/getMyReply", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyReply(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return commentService.getMyReply(uid);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getNotification", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getNotification(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return messageService.getNotification(uid);
    }

    @RequestMapping(value = "/markReadMessage", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String markReadMessage(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int mid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            mid = (Integer) entry.getValue();
        }
        int result = messageService.markReadMessage(mid);
        if (result == SUCCEEDCODE) {
            return JSONUtil.jsonResult(SUCCEEDCODE);
        }
        return JSONUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getMyLike", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyLike(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return messageService.getMylike(uid);
    }

    @RequestMapping(value = "/markReadLike", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String markReadLike(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int lid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            lid = (Integer) entry.getValue();
        }
        int result = messageService.markReadLike(lid);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getMyMessage", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyMessage(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return messageService.getMyMessage(uid);
    }

    @RequestMapping(value = "/addMessage", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addMessage(@RequestBody String json) {
        MessageEntity messageEntity = jsonUtil.readValue(json, MessageEntity.class);
        int result = messageService.addMessage(messageEntity);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getPrivateMsgUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getPrivateMsgUser(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return messageService.getPrivateMsgUser(uid);
    }

    @CheckPrivilege
    @RequestMapping(value = "/readPrivateMsg", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String readPrivateMsg(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int oUid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("oUid")) {
                oUid = (Integer) entry.getValue();
            }
        }
        return messageService.readPrivateMsg(uid, oUid);
    }
}
