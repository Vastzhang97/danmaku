package com.niit.util;

import com.niit.entity.VideoEntity;
import com.niit.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class CreateIndexPageUtil {

    @Autowired
    private VideoServiceImpl videoService;

    /**
     * @param demoPath 模板页面路径
     * @param newPath  生成路径
     * @throws Exception
     */
    public void createNewPage(String demoPath, String newPath, List<VideoEntity> topViewList, List<VideoEntity> topViewByCategoryList) {
        OutputStreamWriter writer = null;
        try {
            File demoFile = new File(demoPath);
            File newFile = new File(newPath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(demoFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            FileOutputStream fop = new FileOutputStream(newFile);
            writer = new OutputStreamWriter(fop, "UTF-8");

            String str = null;
            while ((str = br.readLine()) != null) {
                if (str.indexOf("<div class=\"hot-list hot-list-max\">") != -1) {
                    for (int i = 1; i < topViewList.size() + 1; i++) {
                        VideoEntity videoEntity = topViewList.get(i - 1);
                        str += "<div class=\"hot-items\">\n" +
                                "                <a href=\"" + videoEntity.getWebpage() + "\" target=\"_blank\"><img class=\"hot-image\" src=\"" + videoEntity.getCoversrc() + "\">\n" +
                                "                    <div class=\"hot-video-text\">\n" +
                                "                        <div class=\"hot-video-title\">" + videoEntity.getTitle() + "</div>\n" +
                                "                        <div class=\"hot-video-type hot-video-type-frontColor\">热门视频</div>\n" +
                                "                    </div>\n" +
                                "                </a>\n" +
                                "            </div>";
                    }
                }
                List<VideoEntity> videoEntityList = new ArrayList<>();
                for (int i = 1; i < 8; i++) {
                    if (str.indexOf("<div id=\"video-" + i + "\" class=\"main-items-video\">") != -1) {
                        for (int j = 1; j < topViewByCategoryList.size() + 1; j++) {
                            VideoEntity videoEntity = topViewByCategoryList.get(j - 1);
                            if (videoEntity.getCategory() == i) {
                                videoEntityList.add(videoEntity);
                            }
                        }
                        for (int x = 1; x < videoEntityList.size() + 1; x++) {
                            VideoEntity videoEntity1 = videoEntityList.get(x - 1);
                            str += "<a href=\"" + videoEntity1.getWebpage() + "\"  target=\"_blank\">\n" +
                                    "                <div class=\"area-item\"><img class=\"hot-image\" src=\"" + videoEntity1.getCoversrc() + "\">\n" +
                                    "                    <div class=\"hot-video-text\">\n" +
                                    "                        <div class=\"hot-video-title\">\n" +
                                    "                            <div class=\"video-infor-title\">" + videoEntity1.getTitle() + "</div>\n" +
                                    "                        </div>\n" +
                                    "                        <div class=\"hot-video-type\">\n" +
                                    "                            <div class=\"video-infor\">\n" +
                                    "                                <div class=\"video-infor-logo\"><img src=\"/images/danmaku.png\"></div>\n" +
                                    "                                <div class=\"video-infor-item\">" + videoEntity1.getDanmakunum() + "</div>\n" +          //弹幕数和点击量反了
                                    "                            </div>\n" +
                                    "                            <div class=\"video-infor\">\n" +
                                    "                                <div class=\"video-infor-logo\"><img src=\"/images/view.png\"></div>\n" +
                                    "                                <div class=\"video-infor-item\">" + videoEntity1.getView() + "</div>\n" +
                                    "                            </div>\n" +
                                    "                            <div class=\"video-infor\">\n" +
                                    "                                <div class=\"video-infor-logo\"><img src=\"/images/duration.png\"></div>\n" +
                                    "                                <div class=\"video-infor-item\">" + videoEntity1.getDuration() + "</div>\n" +
                                    "                            </div>\n" +
                                    "                        </div>\n" +
                                    "                    </div>\n" +
                                    "                </div>\n" +
                                    "            </a>";
                        }
                        videoEntityList.clear();
                    }
                }
                writer.append(str + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
