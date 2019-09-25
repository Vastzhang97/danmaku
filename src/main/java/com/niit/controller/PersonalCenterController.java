package com.niit.controller;

import com.niit.aop.CheckLogin;
import com.niit.aop.CheckPrivilege;
import com.niit.entity.ContributionEntity;
import com.niit.entity.HistoryEntity;
import com.niit.service.impl.*;
import com.niit.util.JSONUtil;
import com.niit.webService.IDCardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Controller
@RequestMapping(value = "PersonalCenter")
public class PersonalCenterController {

    @Autowired
    private FavoriteServiceImpl favoriteService;
    @Autowired
    private VideoServiceImpl videoService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private ContributionServiceImpl contributionService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private HistoryServiceImpl historyService;
    @Autowired
    private UploadServiceImpl uploadService;
    @Autowired
    private JSONUtil jsonUtil;

    @CheckPrivilege
    @RequestMapping(value = "/getFavorite", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getFavorite(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return favoriteService.getMyFavorite(uid);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getMyInfo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyInfo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return userService.getMyInfo(uid);
    }

    @CheckPrivilege
    @RequestMapping(value = "/alfterMyInfo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String alfterMyInfo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        String uname = "";
        String password = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("uname")) {
                uname = (String) entry.getValue();
            }
            if (entry.getKey().equals("password")) {
                password = (String) entry.getValue();
            }
        }
        return jsonUtil.jsonResult(userService.alfterMyInfo(uid, uname, password));
    }

    @CheckPrivilege
    @RequestMapping(value = "/getMyVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyVideo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return videoService.getMyVideo(uid);
    }

    @CheckPrivilege
    @RequestMapping(value = "/getMyContribution", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getMyContribution(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return contributionService.getMyContribution(uid);
    }

    @RequestMapping(value = "/addContribution", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addContribution(@RequestBody String json) {
        ContributionEntity contribution = jsonUtil.readValue(json, ContributionEntity.class);
        int result = contributionService.addContribution(contribution);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    /**
     * 初始化顶部用户条
     *
     * @param json
     * @return
     */
    @CheckPrivilege
    @RequestMapping(value = "/getNavigationBar", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getNavigationBar(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return historyService.getNavigationBar(uid);
    }

    @RequestMapping(value = "/addHistory", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String addHistory(@RequestBody String json) {
        HistoryEntity historyEntity = jsonUtil.readValue(json, HistoryEntity.class);

        int result = historyService.addHistroy(historyEntity);
        if (result == SUCCEEDCODE) {
            return jsonUtil.jsonResult(SUCCEEDCODE);
        }
        return jsonUtil.jsonResult(FAILEDCODE);
    }

    @RequestMapping(value = "/uploadHeadshot")
    @ResponseBody
    public String uploadHeadshot(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
//            String headshotSrc = userService.selectUserHeadshot((Integer) request.getSession().getAttribute("uid"));
            return uploadService.uploadHeadshot(file, request);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
    }

    @RequestMapping(value = "/uploadVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String upload(HttpServletRequest request) {
        try {
            uploadService.uploadVideo(request);
            return jsonUtil.jsonResult(SUCCEEDCODE);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传异常");
            return jsonUtil.jsonResult(FAILEDCODE);
        }
    }

    @RequestMapping(value = "/imgUpload")
    @ResponseBody
    public synchronized String imgUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String tishi = "no";
        if (!file.isEmpty()) {
            //System.out.println(file.getOriginalFilename());
            tishi = "yes";//返回yes,表示存储成功，可以使用try,catch来捕捉错误，这里偷懒不用
        }
        return tishi;
    }

    @CheckLogin
    @RequestMapping(value = "/verifyIDCard", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String verifyIDCard(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        String IDNum = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("cardno")) {
                IDNum = (String) entry.getValue();
            }
        }
        return IDCardUtil.getRequest1(IDNum);
    }
}
