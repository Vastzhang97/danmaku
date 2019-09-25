package com.niit.service.impl;

import com.niit.dao.impl.ContributionDaoImpl;
import com.niit.dao.impl.UserDaoImpl;
import com.niit.entity.ContributionEntity;
import com.niit.entity.UserEntity;
import com.niit.service.interfaces.IUploadService;
import com.niit.util.ImageUtil;
import com.niit.util.VideoConvertUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Transactional
@Service
public class UploadServiceImpl implements IUploadService {

    private static Logger logger = Logger.getLogger(UploadServiceImpl.class);
    @Autowired
    private ContributionDaoImpl contributionDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private VideoConvertUtil videoConvertUtil;
    @Autowired
    private ImageUtil imageUtil;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String uploadHeadshot(MultipartFile file, HttpServletRequest request) {
        int uid = 0;
        try {
            uid = (Integer) request.getSession().getAttribute("uid");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("uploadHeadshot()异常，得不到用户id");
            logger.warn(e);
            return "FAILED";
        }
        String saveImagesPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/images/");
        File imagesFolder = new File(saveImagesPath);
        if (!imagesFolder.exists()) {
            imagesFolder.mkdir();
        }
        String realFileName = file.getOriginalFilename();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String prefix = "headshot" + fmt.format(date);
        String suffix = realFileName.substring(realFileName.lastIndexOf("."));
        String saveFileName = prefix + suffix;
        try {
            file.transferTo(new File(saveImagesPath + saveFileName));
            imageUtil.condenseHeadshot(saveImagesPath + saveFileName, saveImagesPath + saveFileName);//TODO 未生成压缩图
            UserEntity userEntity = userDao.selectByUid(uid);
            userEntity.setHeadshot("/images/" + saveFileName);
            userDao.alterUser(userEntity);
            return "/images/" + saveFileName;
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("uploadHeadshot() IO异常");
            logger.warn(e);
        }
        return "FAILED";
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int uploadVideo(HttpServletRequest request) throws Exception {

        String bufferPath = request.getSession().getServletContext().getRealPath("/WEB-INF/buffer");//得到上传文件的缓存存放目录
        String saveVideoPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/videos/");
        String saveImagesPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/images/");
        File bufferFolder = new File(bufferPath);
        File videoFolder = new File(saveVideoPath);
        if (!bufferFolder.exists()) {
            bufferFolder.mkdir();
        }
        if (!videoFolder.exists()) {
            videoFolder.mkdir();
        }
        File imagesFolder = new File(saveImagesPath);
        if (!imagesFolder.exists()) {
            imagesFolder.mkdir();
        }
        DiskFileItemFactory sf = new DiskFileItemFactory();//实例化磁盘被文件列表工厂
        sf.setRepository(new File(bufferPath));//设置缓存文件存放目录
        sf.setSizeThreshold(1024 * 1024);//设置文件上传小于1M放在内存中
        ServletFileUpload sfu = new ServletFileUpload(sf);
        sfu.setHeaderEncoding("ISO8859_1");

        List<FileItem> list = sfu.parseRequest(request);//得到request中所有的元素
        ContributionEntity contributionEntity = new ContributionEntity();
        String saveFileName = "";
        for (int i = 0; i < list.size(); i++) {
            FileItem fileItem = list.get(i);
            /*上传文件筛选*/
            if (fileItem.isFormField()) {//普通form元素
                try {
                    HttpSession session = request.getSession();
                    contributionEntity.setUid((Integer) session.getAttribute("uid"));
                    contributionEntity.setAuthor((String) session.getAttribute("uname"));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.warn("uploadVideo()异常，得不到用户id，uname");
                    logger.warn(e);
                    return FAILEDCODE;
                }
                String content = fileItem.getString();
                content = new String(content.getBytes("ISO8859_1"), "utf-8");//解决中文乱码
                if (fileItem.getFieldName().equals("title")) {//视频标题
                    contributionEntity.setTitle(content);
                }
                if (fileItem.getFieldName().equals("type")) {//视频类型
                    contributionEntity.setCategory(Integer.valueOf(content));
                }
                if (fileItem.getFieldName().equals("videoIntro")) {//视频简介
                    contributionEntity.setIntro(content);
                }
            } else {//文件form元素
                String realFileName = fileItem.getName();
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String prefix = fmt.format(date);
                String suffix = realFileName.substring(realFileName.lastIndexOf("."));

                if (fileItem.getFieldName().equals("imgFile")) {//视频封面
                    if (fileItem.getContentType().contains("image")) {
                        saveFileName = "cover" + prefix + suffix;
                        String coverPath = saveImagesPath + saveFileName;
                        fileItem.write(new File(coverPath));
                        imageUtil.condenseImage(coverPath, coverPath);
                        contributionEntity.setCoversrc("/images/" + saveFileName);
                    }
                }
                if (fileItem.getFieldName().equals("videoFile")) {//视频文件
                    System.out.println(fileItem.getContentType());
                    if (fileItem.getContentType().contains("video") || fileItem.getContentType().contains("octet-stream")) {
                        saveFileName = "video" + prefix + suffix;
                        File VideoFile = new File(saveVideoPath + saveFileName);
                        contributionEntity.setVideosrc("/videos/" + saveFileName);
                        fileItem.write(VideoFile);
                    }
                }
            }
        }
        String video_path = saveVideoPath + saveFileName;
        String ffmpeg_path = "";
        String osName = System.getProperty("os.name"); //操作系统名称
        if (osName.contains("Windows")) {
            ffmpeg_path = request.getSession().getServletContext().getRealPath("/WEB-INF/ffmpeg") + "/ffmpeg.exe";
        } else {
            ffmpeg_path = "ffmpeg";
        }
        String duration = videoConvertUtil.getVideoTime(video_path, ffmpeg_path);
        contributionEntity.setDate(new Timestamp(System.currentTimeMillis()));
        contributionEntity.setStatus(0);
        contributionEntity.setDuration(duration);
        contributionDao.addContribution(contributionEntity);
        return SUCCEEDCODE;
    }
}
