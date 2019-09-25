package com.niit.service.interfaces;

import com.niit.entity.UserEntity;

import javax.servlet.http.HttpSession;

public interface IUerService {

    /**
     * 用户登录
     *
     * @param uName
     * @param password
     * @return
     */
    UserEntity login(String uName, String password, HttpSession session);

    /**
     * 用户注销
     *
     * @param session
     */
    void logout(HttpSession session);

    /**
     * 用户注册
     *
     * @param userEntity
     * @return
     */
    int register(UserEntity userEntity);

    /**
     * 修改用户状态
     *
     * @param status
     * @return
     */
    int changeUserStatus(int status, HttpSession session);

    /**
     * 更新用户等级
     *
     * @param level
     * @return
     */
    int alterLevel(int level, HttpSession session);

    /**
     * 检查用户是否已关注视频作者
     *
     * @param uid
     * @param fid
     * @return
     */
    boolean checkFocus(int uid, int fid);

    /**
     * 关注用户
     *
     * @param uid
     * @param fid
     * @return
     */
    int focusUser(int uid, int fid);

    /**
     * 取消关注用户
     *
     * @param uid
     * @param fid
     * @return
     */
    int unfocusUser(int uid, int fid);

    /**
     * 得到用户粉丝
     *
     * @param uid
     * @return
     */
    String getUserFansList(int uid);

    /**
     * 得到用户信息
     *
     * @param uid
     * @return
     */
    String getMyInfo(int uid);

    /**
     * 修改用户信息
     *
     * @param uid
     * @param uname
     * @param password
     * @return
     */
    int alfterMyInfo(int uid, String uname, String password);

    String selectUserHeadshot(int uid);
}
