package com.niit.service.impl;

import com.niit.dao.impl.*;
import com.niit.entity.*;
import com.niit.service.interfaces.IAdminService;
import com.niit.util.CreateVideoPageUtil;
import com.niit.util.JSONUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.niit.util.Constant.SUCCEEDCODE;

@Transactional
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private ContributionDaoImpl contributionDao;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private VideoDaoImpl videoDao;
    @Autowired
    private DanmakuDaoImpl danmakuDao;
    @Autowired
    private CommentDaoImpl commentDao;
    @Autowired
    private MessageDaoImpl messageDao;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private CreateVideoPageUtil pageUtil;

    public String getPendingContribution() {
        List<ContributionEntity> contributionEntityList = contributionDao.selectByStatus(0);
        for (ContributionEntity contributionEntity : contributionEntityList) {
            UserEntity userEntity = userDao.selectByUid(contributionEntity.getUid());
            contributionEntity.setAuthor(userEntity.getUname());
        }
        return jsonUtil.toJSon(contributionEntityList);
    }

    public String getPendingDanmaku() {
        List<DanmakuEntity> danmakuEntityList = danmakuDao.selectByStatus(1);
        for (DanmakuEntity danmakuEntity : danmakuEntityList) {
            UserEntity userEntity = userDao.selectByUid(danmakuEntity.getUid());
            danmakuEntity.setAuthor(userEntity.getUname());
        }
        return jsonUtil.toJSon(danmakuEntityList);
    }

    public String getPendingComment() {
        List<CommentEntity> commentEntityList = commentDao.selectByStatus(1);
        for (CommentEntity commentEntity : commentEntityList) {
            UserEntity userEntity = userDao.selectByUid(commentEntity.getUid());
            commentEntity.setsUname(userEntity.getUname());
        }
        return jsonUtil.toJSon(commentEntityList);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int passContribution(int cid, HttpSession session, HttpServletRequest request) {

        String demoPagePath = request.getSession().getServletContext().getRealPath("/WEB-INF/pages/generatedPages/");
        String savePagePath = request.getSession().getServletContext().getRealPath("/WEB-INF/pages/generatedPages/");
        File generatePagesFolder = new File(savePagePath);
        if (!generatePagesFolder.exists()) {
            generatePagesFolder.mkdir();
        }
        String webpage = "";

        ContributionEntity contributionEntity = contributionDao.selectByCid(cid);
        UserEntity userEntity = userDao.selectByUid(contributionEntity.getUid());
        contributionEntity.setStatus(1);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setUid(contributionEntity.getUid());
        videoEntity.setTitle(contributionEntity.getTitle());
        videoEntity.setAuthor(userDao.selectByUid(contributionEntity.getUid()).getUname());
        videoEntity.setIntro(contributionEntity.getIntro());
        videoEntity.setDate(new Timestamp(System.currentTimeMillis()));
        videoEntity.setCategory(contributionEntity.getCategory());
        String coverSrc = contributionEntity.getCoversrc();
        String videoSrc = contributionEntity.getVideosrc();
        videoEntity.setCoversrc(coverSrc);
        videoEntity.setVideosrc(videoSrc);
        videoEntity.setWebpage(webpage);
        videoEntity.setDuration(contributionEntity.getDuration());
        videoEntity.setView(0);
        videoEntity.setLikenum(0);
        videoEntity.setDanmakunum(0);
        videoEntity.setPopularitynum(0);
        videoEntity.setGrade(0);
        videoEntity.setGradenum(0);

        int vid = videoDao.addVideo(videoEntity);

        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String saveFileName = "video" + sdf.format(contributionEntity.getDate());
        pageUtil.createNewPage(demoPagePath + "/videoDemo.html", savePagePath + saveFileName + ".html",
                vid, contributionEntity.getTitle(), contributionEntity.getVideosrc(),
                userEntity.getUname(), userEntity.getHeadshot(), contributionEntity.getIntro());
        VideoEntity dbVideoEntity = videoDao.selectByVid(vid);
        System.out.println(dbVideoEntity);
        dbVideoEntity.setWebpage("/" + saveFileName);
        videoDao.alterVideo(dbVideoEntity);
        contributionDao.alterContribution(contributionEntity);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(contributionEntity.getUid());

        messageEntity.setContext("您的投稿: " + contributionEntity.getTitle() + " 已通过系统审核" + "<a href=\"" + "/" + saveFileName + "\" target=\"blank\">点此跳转</a>");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);
        return SUCCEEDCODE;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int unPassContribution(int cid, HttpSession session) {
        ContributionEntity contributionEntity = contributionDao.selectByCid(cid);
        contributionEntity.setStatus(2);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(contributionEntity.getUid());

        messageEntity.setContext("您的投稿: " + contributionEntity.getTitle() + " 没有通过系统审核哦");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);

        return contributionDao.alterContribution(contributionEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int passDanmaku(int did, HttpSession session) {
        DanmakuEntity danmakuEntity = danmakuDao.selectByDid(did);
        danmakuEntity.setStatus(0);

        VideoEntity videoEntity = videoDao.selectByVid(danmakuEntity.getVid());

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(danmakuEntity.getUid());

        messageEntity.setContext("您的在视频: " + videoEntity.getTitle() + " 的弹幕： " + danmakuEntity.getContent() + " 系统检测正常");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);

        return danmakuDao.alterDanmaku(danmakuEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int unPassDanmaku(int did, HttpSession session) {
        DanmakuEntity danmakuEntity = danmakuDao.selectByDid(did);
        danmakuEntity.setStatus(2);

        VideoEntity videoEntity = videoDao.selectByVid(danmakuEntity.getVid());
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(danmakuEntity.getUid());

        messageEntity.setContext("您的在视频: " + videoEntity.getTitle() + " 的弹幕： " + danmakuEntity.getContent() + " 被系统禁止了！请遵守相关规定，文明发言");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);

        return danmakuDao.alterDanmaku(danmakuEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int passComment(int cid, HttpSession session) {
        CommentEntity commentEntity = commentDao.selectByCid(cid);
        commentEntity.setStatus(0);

        VideoEntity videoEntity = videoDao.selectByVid(commentEntity.getVid());
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(commentEntity.getUid());

        messageEntity.setContext("您的在视频: " + videoEntity.getTitle() + " 的评论： " + commentEntity.getContent() + " 系统检测正常");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);

        return commentDao.alterComment(commentEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int unPassComment(int cid, HttpSession session) {
        CommentEntity commentEntity = commentDao.selectByCid(cid);
        commentEntity.setStatus(2);

        VideoEntity videoEntity = videoDao.selectByVid(commentEntity.getVid());
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSid((Integer) session.getAttribute("uid"));
        messageEntity.setRid(commentEntity.getUid());

        messageEntity.setContext("您的在视频: " + videoEntity.getTitle() + " 的评论： " + commentEntity.getContent() + "  被系统禁止了！请遵守相关规定，文明发言");
        messageEntity.setStatus(0);
        messageEntity.setDate(new Timestamp(System.currentTimeMillis()));
        messageDao.addMessage(messageEntity);

        return commentDao.alterComment(commentEntity);
    }

    @Override
    public String getAllUser() {
        return jsonUtil.toJSon(userDao.selectAllUser());
    }

    @Override
    public int enableUser(int uid) {
        UserEntity userEntity = userDao.selectByUid(uid);
        userEntity.setStatus(0);
        return userDao.alterUser(userEntity);
    }

    @Override
    public int disableUser(int uid) {
        UserEntity userEntity = userDao.selectByUid(uid);
        userEntity.setStatus(1);
        return userDao.alterUser(userEntity);
    }
}
