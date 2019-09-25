package com.niit.controller;

import com.niit.service.impl.VideoServiceImpl;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "Search")
public class SearchController {

    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private VideoServiceImpl videoService;

    @RequestMapping(value = "/search", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String search(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        String key = "";
        int category = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                key = (String) entry.getValue();
            }
            if (entry.getKey().equals("category")) {
                category = (Integer) entry.getValue();
            }
        }
        return videoService.searchVideo(key, category);
    }

    @RequestMapping(value = "/getTopVideo", produces = "plain/text; charset=UTF-8")
    @ResponseBody
    public String getTopVideo(@RequestBody String json) {
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int category = 0;
        int type = 0;
        int currentPage = 0;
        String key = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("type")) {
                category = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("oType")) {
                type = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("key")) {
                key = (String) entry.getValue();
            }
            if (entry.getKey().equals("currentPage")) {
                currentPage = (Integer) entry.getValue();
            }
        }
        return videoService.searchVideoByCategoryAndType(key, category, type, currentPage);
    }
}
