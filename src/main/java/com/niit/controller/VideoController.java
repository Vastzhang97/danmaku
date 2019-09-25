package com.niit.controller;

import com.niit.aop.CheckPrivilege;
import com.niit.entity.CommentEntity;
import com.niit.entity.DanmakuEntity;
import com.niit.entity.FavoriteEntity;
import com.niit.service.impl.*;
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
@RequestMapping(value = "Video")
public class VideoController {

    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private DanmakuServiceImpl danmakuService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private VideoServiceImpl videoService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FavoriteServiceImpl favoriteService;

    @RequestMapping(value = "/addDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addDanmaku(@RequestBody String json) {
        DanmakuEntity danmakuEntity = jsonUtil.readValue(json, DanmakuEntity.class);
        int result = danmakuService.addDanmaku(danmakuEntity);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @RequestMapping(value = "/getDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getDanmaku(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println(entry.getValue());
            vid = (Integer) entry.getValue();
        }
        return danmakuService.getDanmakuByVid(vid);
    }

    @RequestMapping(value = "/reportDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String reportDanmaku(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int did = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            did = (Integer) entry.getValue();
        }
        int result = danmakuService.reportDanmaku(did);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @RequestMapping(value = "/addComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addComment(@RequestBody String json) {
        CommentEntity commentEntity = jsonUtil.readValue(json, CommentEntity.class);
        if (commentEntity.getRcid() == 0) {
            commentEntity.setRcid(null);
        }
        commentEntity.setStatus(0);
        return commentService.addComment(commentEntity);
    }

    @RequestMapping(value = "/getComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getComment(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            vid = (Integer) entry.getValue();
        }
        return commentService.getComment(vid);
    }

    @RequestMapping(value = "/gradeVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String gradeVideo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int vid = 0;
        int grade = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("vid")) {
                vid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("grade")) {
                grade = (Integer) entry.getValue();
            }
        }
        int result = videoService.gradeVideo(vid, grade);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/likeVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String likeVideo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int vid = 0;
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("vid")) {
                vid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
        }
        int result = videoService.likeVideo(vid, uid);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/likeDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String likeDanmaku(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int did = 0;
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("did")) {
                did = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
        }
        int result = videoService.likeDanmaku(did, uid);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/likeComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String likeComment(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("cid")) {
                cid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
        }
        int result = videoService.likeComment(cid, uid);
        return String.valueOf(result);
    }

    @CheckPrivilege
    @RequestMapping(value = "/checkFocus", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String checkFocus(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int fid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                fid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("fid")) {
                fid = (Integer) entry.getValue();
            }
        }
        boolean result = userService.checkFocus(uid, fid);
        if (result) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/focusUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String focusUser(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int fid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("fid")) {
                fid = (Integer) entry.getValue();
            }
        }
        int result = userService.focusUser(uid, fid);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/unfocusUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String unfocusUsuer(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int fid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("fid")) {
                fid = (Integer) entry.getValue();
            }
        }
        return jsonUtil.jsonResult(userService.unfocusUser(uid, fid));
    }

    @RequestMapping(value = "/addFavorite", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addFavorite(@RequestBody String json) {
        FavoriteEntity favoriteEntity = jsonUtil.readValue(json, FavoriteEntity.class);
        int result = favoriteService.addFavorite(favoriteEntity);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/removeFavorite", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String removeFavorite(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("vid")) {
                vid = (Integer) entry.getValue();
            }
        }
        return jsonUtil.jsonResult(favoriteService.deleteFavorite(uid, vid));
    }

    @RequestMapping(value = "/reportComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String reportComment(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            cid = (Integer) entry.getValue();
        }
        int result = commentService.reportComment(cid);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @CheckPrivilege
    @RequestMapping(value = "/checkDanmakuLike", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String checkDanmakuLike(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("vid")) {
                vid = (Integer) entry.getValue();
            }
        }
        //TODO
        return null;
    }

    @RequestMapping(value = "/checkStatus", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String checkStatus(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("vid")) {
                vid = (Integer) entry.getValue();
            }
        }
        return videoService.checkStatus(uid, vid);
    }

    @RequestMapping(value = "/initVideoInfo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String initVideoInfo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int vid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            vid = (Integer) entry.getValue();
        }
        return videoService.getVideoInfo(vid);
    }

}
