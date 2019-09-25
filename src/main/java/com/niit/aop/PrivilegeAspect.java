package com.niit.aop;

import com.niit.dao.impl.UserDaoImpl;
import com.niit.entity.UserEntity;
import com.niit.util.JSONUtil;
import com.niit.util.SysContent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.niit.util.Constant.FAILEDCODE;

@Aspect
@Component
public class PrivilegeAspect {

    @Autowired
    private JSONUtil jsonUtil;
    @Autowired
    private UserDaoImpl userDao;
    private final int NEEDLOGIN = 2;

    @Around("@annotation(com.niit.aop.CheckPrivilege)")
    public Object checkPrivilege(ProceedingJoinPoint pjp) throws Throwable {
        String joinPointJson = Arrays.toString(pjp.getArgs());
        String json = joinPointJson.substring(1, joinPointJson.length() - 1);
        Map<String, Object> map = jsonUtil.readValue(json, Map.class);
        int uid = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("fid")) {
                uid = (Integer) entry.getValue();
            }
            if (entry.getKey().equals("uid")) {
                uid = (Integer) entry.getValue();
            }
        }
        HttpSession session = SysContent.getSession();
        if (session.getAttribute("uid") != null) {
            int sessionId = (Integer) session.getAttribute("uid");
            if (uid == sessionId) {
                return pjp.proceed();
            }
        }
        return jsonUtil.jsonResult(NEEDLOGIN);
    }

    @Around("@annotation(com.niit.aop.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        HttpSession session = SysContent.getSession();
        if (session.getAttribute("uid") != null) {
            return pjp.proceed();
        }
        return "login";
    }

    @Around("@annotation(com.niit.aop.CheckAdmin)")
    public Object CheckAdmin(ProceedingJoinPoint pjp) throws Throwable {
        HttpSession session = SysContent.getSession();
        List<UserEntity> adminList = userDao.selectByPrivilege(0);
        if (session.getAttribute("uid") != null) {
            for (UserEntity adminEntity : adminList) {
                if ((Integer) session.getAttribute("uid") == adminEntity.getUid()) {
                    return pjp.proceed();
                }
            }
        }
        return "needAdmin";
    }
}