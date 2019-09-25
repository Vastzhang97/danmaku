package com.niit.service;

import com.niit.dao.impl.VideoDaoImpl;
import com.niit.entity.VideoEntity;
import com.niit.service.impl.VideoServiceImpl;
import com.niit.util.CreateIndexPageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TimedTaskService {

    private static Logger logger = Logger.getLogger(TimedTaskService.class);
    @Autowired
    private CreateIndexPageUtil util;
    @Autowired
    private VideoDaoImpl videoDao;

    @PostConstruct
    @Scheduled(cron = "0 0/2 * * * ?")
    public void job1() {

        String javaClassPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String WEB_INF_Path = javaClassPath.substring(0, javaClassPath.length() - 8);
        String demoPath = WEB_INF_Path + "pages/generatedPages/indexDemo.html";
        String outputPath = WEB_INF_Path + "pages/index.html";
        logger.info("定时刷新主页数据" + new Date());
//        System.out.println("定时刷新主页数据" + new Date());
        List<VideoEntity> videoEntityList = null;
        List<VideoEntity> videoEntityList1 = null;
        List<VideoEntity> videoEntityList2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                videoEntityList = videoDao.selectByView(i);
            } else {
                videoEntityList1 = videoDao.selectByView(i);
                videoEntityList2.addAll(videoEntityList1);
            }
        }
        util.createNewPage(demoPath, outputPath, videoEntityList, videoEntityList2);
    }
}
