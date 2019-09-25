package com.niit.controller;

import com.niit.aop.CheckAdmin;
import com.niit.aop.CheckLogin;
import com.niit.dao.impl.FavoriteDaoImpl;
import com.niit.dao.impl.UserDaoImpl;
import com.niit.entity.AdviceEntity;
import com.niit.entity.UserEntity;
import com.niit.service.impl.AdviceServiceImpl;
import com.niit.service.impl.FavoriteServiceImpl;
import com.niit.service.impl.UserServiceImpl;
import com.niit.service.impl.VideoServiceImpl;
import com.niit.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;
import static com.niit.util.JSONUtil.readValue;


@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private VideoServiceImpl videoService;
    @Autowired
    private AdviceServiceImpl adviceService;
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @Autowired
    private FavoriteDaoImpl favoriteDao;
    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private JSONUtil jsonUtil;

    @RequestMapping(value = "/")
    public String goToIndex() {
        return "index";
    }

    @RequestMapping(value = "/goToIndex")
    public String goToIndex2() {
        return "index";
    }

    @CheckLogin
    @RequestMapping(value = "/goToCollection")
    public String goToCollection() {
        return "collection";
    }

    @CheckLogin
    @RequestMapping(value = "/goToContribution")
    public String goToContribution() {
        return "contribution";
    }

    @CheckLogin
    @RequestMapping(value = "/goToInfor")
    public String goToInfor() {
        return "infor";
    }

    @RequestMapping(value = "/goToLogin")
    public String goToLogin() {
        return "login";
    }

    @CheckLogin
    @RequestMapping(value = "/goToMessage")
    public String goToMessage() {
        return "message";
    }

    @CheckLogin
    @RequestMapping(value = "/goToMyVideo")
    public String goToMyVideo() {
        return "myVideo";
    }

    @CheckLogin
    @RequestMapping(value = "/goToMyContribution")
    public String goToMyContribution() {
        return "myContribution";
    }

    @CheckLogin
    @RequestMapping(value = "/goToPerson")
    public String goToPerson() {
        return "person";
    }

    @CheckLogin
    @RequestMapping(value = "/goToReceiveLike")
    public String goToReceiveLike() {
        return "receiveLike";
    }

    @RequestMapping(value = "/goToRegister")
    public String goToResister() {
        return "register";
    }

    @CheckLogin
    @RequestMapping(value = "/goToReply")
    public String goToReply() {
        return "reply";
    }

    @RequestMapping(value = "/goToSearch")
    public String goToSearch() {
        return "search";
    }

    @CheckLogin
    @RequestMapping(value = "/goToSysMessage")
    public String goToSysMessage() {
        return "sysMessage";
    }

    @CheckAdmin
    @RequestMapping(value = "/goToAdmin")
    public String goToadmin() {
        return "admin";
    }

    @CheckAdmin
    @RequestMapping(value = "/goToAdminUser")
    public String goToAdminUser() {
        return "adminUser";
    }

    @CheckAdmin
    @RequestMapping(value = "/goToaAdminComment")
    public String goToaAdminComment() {
        return "adminComment";
    }

    @CheckAdmin
    @RequestMapping(value = "/goToAdminContribution")
    public String goToAdminContribution() {
        return "adminContribution";
    }

    @CheckAdmin
    @RequestMapping(value = "/goToAdminDanmaku")
    public String goToAdminDanmaku() {
        return "adminDanmaku";
    }

    @CheckLogin
    @RequestMapping(value = "/goToPrivateMsg")
    public String goToPrivateMsg() {
        return "privateMsg";
    }

    @RequestMapping(value = "/goToAdvice")
    public String goToAdvice() {
        return "advice";
    }

    /*
     * @RequestMapping(value = "/{webpage}")会导致所有/引用这个Mapping
     * */
    @RequestMapping(value = "/video{webpage}")
    public String goToPages(@PathVariable("webpage") String webpage) {
        return "/generatedPages/" + "video" + webpage;
    }


    @RequestMapping(value = "/test")
    public String test() {
        return "/generatedPages/indexDemo";
    }

    @RequestMapping(value = "/login", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String login(HttpSession session, @RequestBody String json) {
        UserEntity userEntity = readValue(json, UserEntity.class);
        userEntity = userService.login(userEntity.getUname(), userEntity.getPassword(), session);
        if (userEntity != null && userEntity.getStatus() == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("result", 1);
            map.put("uid", userEntity.getUid());
            map.put("uname", userEntity.getUname());
            map.put("headshot", userEntity.getHeadshot());
            map.put("privilege", userEntity.getPrivilege());
            return jsonUtil.toJSon(map);
        }
        if (userEntity != null && userEntity.getStatus() == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("result", 2);
            map.put("msg", "登陆失败!您的账户被禁用");
            return jsonUtil.toJSon(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", 0);
        map.put("msg", "登陆失败!请检查用户名密码是否正确");

        return jsonUtil.toJSon(map);
    }

    @RequestMapping(value = "/logout", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String logout(HttpSession session, @RequestBody String json) {
        userService.logout(session);
        return jsonUtil.jsonResult(SUCCEEDCODE);
    }

    @RequestMapping(value = "/register", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String register(HttpSession session, @RequestBody String json) {
        System.out.println("json is " + json);
        UserEntity userEntity = readValue(json, UserEntity.class);
        try {
            userService.register(userEntity);
            return jsonUtil.jsonResult(SUCCEEDCODE);
        } catch (Exception e) {
            logger.warn("用户注册异常");
            logger.warn(e);
            return jsonUtil.jsonResult(FAILEDCODE);
        }
    }

    @RequestMapping(value = "/getRecommendVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getRecommendVideo() {
        return videoService.getRecommendVideo();
    }

    @RequestMapping(value = "/addAdvice")
    public String addAdvice(@RequestParam("name") String name, @RequestParam("contact") String contact, @RequestParam("content") String content) {
        AdviceEntity adviceEntity = new AdviceEntity();
        adviceEntity.setName(name);
        adviceEntity.setContact(content);
        adviceEntity.setContent(content);
        adviceService.addAdvice(adviceEntity);
        return "adviceSuccess";
    }
}
