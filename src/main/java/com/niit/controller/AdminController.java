package com.niit.controller;

import com.niit.entity.ContributionEntity;
import com.niit.service.impl.AdminServiceImpl;
import com.niit.service.impl.ContributionServiceImpl;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Controller
@RequestMapping(value = "Admin")
public class AdminController {

    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private ContributionServiceImpl contributionService;
    @Autowired
    private AdminServiceImpl adminService;

    @RequestMapping(value = "/getAllUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getAllUser() {
        return adminService.getAllUser();
    }

    @RequestMapping(value = "/enableUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String enableUser(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.enableUser(uid));
    }

    @RequestMapping(value = "/disableUser", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String disableUser(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            uid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.disableUser(uid));
    }

    @RequestMapping(value = "/getPendingContribution", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getPendingContribution() {
        return adminService.getPendingContribution();
    }

    @RequestMapping(value = "/passContribution", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String passContribution(HttpServletRequest request, HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            cid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.passContribution(cid, session, request));
    }

    @RequestMapping(value = "/unPassContribution", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String unPassContribution(HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            cid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.unPassContribution(cid, session));
    }

    @RequestMapping(value = "/getPendingDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getPendingDanmaku() {
        return adminService.getPendingDanmaku();
    }

    @RequestMapping(value = "/passDanmaku")
    @ResponseBody
    public String passDanmaku(HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int did = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            did = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.passDanmaku(did, session));
    }

    @RequestMapping(value = "/unPassDanmaku", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String unPassDanmaku(HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int did = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            did = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.unPassDanmaku(did, session));
    }

    @RequestMapping(value = "/getPendingComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getPendingComment() {
        return adminService.getPendingComment();
    }

    @RequestMapping(value = "/passComment")
    @ResponseBody
    public String passComment(HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            cid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.passComment(cid, session));
    }

    @RequestMapping(value = "/unPassComment", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String unPassComment(HttpSession session, @RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int cid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            cid = (Integer) entry.getValue();
        }
        return jsonUtil.jsonResult(adminService.unPassComment(cid, session));
    }
}
