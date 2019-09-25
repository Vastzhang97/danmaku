package com.niit.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VideoConvertUtil {

    /**
     * 获取视频总时间
     *
     * @param video_path  视频路径
     * @param ffmpeg_path ffmpeg路径
     * @return
     */
    public String getVideoTime(String video_path, String ffmpeg_path) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();
            // 从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
                // System.out.println(line);
            }
            br.close();
            // 从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                String duration = m.group(1);
                String duration1 = duration.substring(0, duration.length() - 3);
                return duration1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 格式:"00:00:10.68"
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;// 秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    public static void getScreenshot(String video_path, String ffmpeg_path) {
        // ffmpeg -ss 10:00 -i input -f image2 -r 0.2 -t 10:00 %3d.jpg
        // ffmpeg -ss 00:02:06 -i test1.flv -f image2 -y test1.jpg
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-ss");
        commands.add("1:00");
        commands.add("-i");
        commands.add(video_path);
        commands.add("-f");
        commands.add("image2");
        commands.add("-r");
        commands.add("0.01");
        commands.add("-t");
        commands.add("4:00");
        commands.add("screenshot\\%3d.jpg");
        /*
         * 1、提取图片
         *
         * FFmpeg -ss start_time -t last_time -i video_path -f image2 -r fps
         * -q:v 2 image_path 其中：start_time 表示起始时间，一般表现为 00:00:00，last_time
         * 表示持续时间，格式同起始时间。-to 可以指定结束时间，单位以秒记。 -f iamge2 指定图片编码格式，-r 指定提取频率，-q:v
         * 指定图片高质量，image_path 为图片输出路径，提取多个图片可用 path_%d 命名。 采样频率 r
         * 建议按照视频自身帧率来采。如视频15帧，而以30的帧频来采集图像，则两帧一重复。同理设置成15以下，采集出的图像数肯定小于总帧数。 貌似
         * jpg 与 bmp 无明显差异，体积大小不同。理
         */

        // commands.add(ffmpeg_path);
        // commands.add("-ss");
        // commands.add("00:02:06");
        // commands.add("-i");
        // commands.add(video_path);
        // commands.add("-f");
        // commands.add("image2");
        // commands.add("-r");
        // commands.add("0.2");
        // commands.add("-t");
        // commands.add("-y");
        // commands.add("screenshot\\test1%03d.jpg");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
                // System.out.println(line);
            }
            br.close();
            System.out.println("OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
