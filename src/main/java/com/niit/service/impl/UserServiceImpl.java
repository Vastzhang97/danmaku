package com.niit.service.impl;

import com.niit.dao.impl.UserDaoImpl;
import com.niit.entity.UserEntity;
import com.niit.entity.UserinfoEntity;
import com.niit.service.interfaces.IUerService;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Transactional
@Service
public class UserServiceImpl implements IUerService {

    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private JSONUtil jsonUtil;

    public UserEntity login(String uName, String password, HttpSession session) {

        UserEntity userEntity = userDao.selectByUname(uName);
        if (userEntity != null) {
            if (userEntity.getPassword().equals(password)) {
                session.setAttribute("uname", userEntity.getUname());
                session.setAttribute("uid", userEntity.getUid());
                return userEntity;
            }
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int register(UserEntity userEntity) {
        try {
            userEntity.setHeadshot("/images/defaultHeadshot.jpg");
            userEntity.setPrivilege(1);
            userDao.addUser(userEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("register捕获异常");
            return FAILEDCODE;
        }

    }

    public int changeUserStatus(int status, HttpSession session) {
        int uid = (Integer) session.getAttribute("uid");
        UserEntity userEntity = userDao.selectByUid(uid);
        userEntity.setStatus(status);
        return userDao.alterUser(userEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int alterLevel(int level, HttpSession session) {
        int uid = (Integer) session.getAttribute("uid");
        UserEntity userEntity = userDao.selectByUid(uid);
        userEntity.setLevel(level);
        return userDao.alterUser(userEntity);
    }

    public boolean checkFocus(int uid, int fid) {
        return userDao.checkFocus(uid, fid);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int focusUser(int uid, int fid) {
        UserinfoEntity userinfoEntity = new UserinfoEntity();
        userinfoEntity.setUid(uid);
        userinfoEntity.setFid(fid);
        return userDao.addUserInfo(userinfoEntity);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int unfocusUser(int uid, int fid) {
        UserinfoEntity userinfoEntity = new UserinfoEntity();
        userinfoEntity.setUid(uid);
        userinfoEntity.setFid(fid);
        return userDao.deleteUserInfo(uid, fid);
    }

    public String getUserFansList(int uid) {
        return null;
    }

    public String getMyInfo(int uid) {
        UserEntity userEntity = userDao.selectByUid(uid);
        long fanNum = userDao.selectFanNum(uid);
        String jsonResult = jsonUtil.toJSon(userEntity);
        String json = jsonResult.substring(0, jsonResult.length() - 1) + ",\"num\":" + fanNum + "}";
        return json;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int alfterMyInfo(int uid, String uname, String password) {
        UserEntity userEntity = userDao.selectByUid(uid);
        userEntity.setUname(uname);
        userEntity.setPassword(password);
        return userDao.alterUser(userEntity);
    }

    @Override
    public String selectUserHeadshot(int uid) {
        return userDao.selectByUid(uid).getHeadshot();
    }


}
