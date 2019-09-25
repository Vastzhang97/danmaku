package com.niit.dao.interfaces;

import com.niit.entity.UserEntity;
import com.niit.entity.UserinfoEntity;

import java.util.List;

public interface IUserDao {

    UserEntity selectByUname(String uName);

    UserEntity selectByUid(int uid);

    int addUser(UserEntity userEntity);

    int alterUser(UserEntity userEntity);

    /**
     * 根据uid查询关注用户表
     *
     * @param uid
     * @return
     */
    List<UserinfoEntity> selectInfoByUid(int uid);

    /**
     * 根据权限查询用户
     *
     * @param privilege 0管理员，1普通用户
     * @return
     */
    List<UserEntity> selectByPrivilege(int privilege);

    /**
     * 检查用户是否已关注视频作者
     *
     * @param uid
     * @param fid
     * @return
     */
    Boolean checkFocus(int uid, int fid);

    /**
     * 增加userInfo表
     *
     * @return
     */
    int addUserInfo(UserinfoEntity userinfoEntity);

    /**
     * @param uid 视频作者id
     * @param fid 当前用户id
     * @return
     */
    int deleteUserInfo(int uid, int fid);

    /**
     * 得到用户粉丝数目
     *
     * @param uid
     * @return
     */
    long selectFanNum(int uid);

    List<UserEntity> selectAllUser();

    /**
     * 查询是否有该记录
     *
     * @param uid
     * @param fid
     * @return ture 存在
     */
    Boolean selectByUidAndFid(int uid, int fid);
}
