package com.niit.service.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IAdminService {

    /**
     * 得到待审核的视频
     *
     * @return
     */
    String getPendingContribution();

    /**
     * 得到待审核的弹幕
     *
     * @return
     */
    String getPendingDanmaku();

    /**
     * 得到待审核的评论
     *
     * @return
     */
    String getPendingComment();

    /**
     * 通过视频审核
     *
     * @param cid
     * @return
     */
    int passContribution(int cid, HttpSession session, HttpServletRequest request);

    /**
     * 不通过视频审核
     *
     * @param cid
     * @return
     */
    int unPassContribution(int cid, HttpSession session);

    /**
     * 通过弹幕审核
     *
     * @param did
     * @return
     */
    int passDanmaku(int did, HttpSession session);

    /**
     * 不通过弹幕审核
     *
     * @param did
     * @return
     */
    int unPassDanmaku(int did, HttpSession session);

    /**
     * 通过评论审核
     *
     * @param cid
     * @return
     */
    int passComment(int cid, HttpSession session);

    /**
     * 不通过评论审核
     *
     * @param cid
     * @return
     */
    int unPassComment(int cid, HttpSession session);

    /**
     * 得到所有用户
     *
     * @return
     */
    String getAllUser();

    /**
     * 解除禁止用户
     *
     * @param uid
     * @return
     */
    int enableUser(int uid);

    /**
     * 禁止用户
     *
     * @param uid
     * @return
     */
    int disableUser(int uid);
}
