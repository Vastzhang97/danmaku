package com.niit.util;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CreateVideoPageUtil {
    /**
     * @param demoPath    模板页面路径
     * @param newPath     生成路径
     * @param title       视频标题
     * @param vid         视频id
     * @param videoSrc    视频src
     * @param author      作者名字
     * @param headshotSrc 作者头像Src
     * @param intro       视频简介
     * @throws Exception
     */
    public void createNewPage(String demoPath, String newPath, int vid, String title, String videoSrc, String author, String headshotSrc, String intro) {
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

                if (str.indexOf("<div id=\"videoDemonId\">1</div>") != -1) {
//                    System.out.println(1);
                    str = str.replace("1", String.valueOf(vid));
                }
                if (str.indexOf("<title>视频页面</title>") != -1) {
                    str = str.replace("视频页面", title);
                }
                if (str.indexOf("<div class=\"title\">视频标题233333</div>") != -1) {
//                    System.out.println(2);
                    str = str.replace("视频标题233333", title);
                }
                if (str.indexOf("videos/az.mkv") != -1) {
//                    System.out.println(3);
                    str = str.replace("videos/az.mkv", videoSrc);
                }
                if (str.indexOf("admin") != -1) {
//                    System.out.println(4);
                    str = str.replace("admin", author);
                }
                if (str.indexOf("images/3.jpg") != -1) {
//                    System.out.println(5);
                    str = str.replace("images/3.jpg", headshotSrc);
                }
                if (str.indexOf("<div class=\"video-intro-content\">") != -1) {
//                    System.out.println(6);
                    str = str.replace("<div class=\"video-intro-content\">", "<div class=\"video-intro-content\">" + intro);
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
