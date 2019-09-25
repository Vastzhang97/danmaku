package com.niit.dao.impl;

import com.niit.dao.interfaces.IUserDao;
import com.niit.entity.UserEntity;
import com.niit.entity.UserinfoEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;


@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserEntity selectByUname(String uName) {

        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserEntity where uname = ?");
            query.setParameter(0, uName);
            List<UserEntity> list = query.list();
            UserEntity user = null;
            for (UserEntity user1 : list) {
                user = user1;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserEntity selectByUid(int uid) {
        try {
            return sessionFactory.getCurrentSession().get(UserEntity.class, uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addUser(UserEntity userEntity) {
//        try {
        sessionFactory.getCurrentSession().save(userEntity);
        return SUCCEEDCODE;
//        } catch (Throwable e) {
//            System.out.println("添加用户出现异常");
//            e.printStackTrace();
//            return FAILEDCODE;
//        }
//        return FAILEDCODE;
    }

    public int alterUser(UserEntity userEntity) {

        try {
            sessionFactory.getCurrentSession().update(userEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public List<UserinfoEntity> selectInfoByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserinfoEntity where uid = ?");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param privilege 0管理员，1普通用户
     * @return
     */
    public List<UserEntity> selectByPrivilege(int privilege) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserEntity where privilege = ?");
            query.setParameter(0, privilege);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkFocus(int uid, int fid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserinfoEntity where uid = ? and fid = ?");
            query.setParameter(0, uid);
            query.setParameter(1, fid);
            List list = query.list();
            if (list.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addUserInfo(UserinfoEntity userinfoEntity) {
        try {
            sessionFactory.getCurrentSession().save(userinfoEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public int deleteUserInfo(int uid, int fid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("delete UserinfoEntity where uid = ? and fid = ?");
            query.setParameter(0, uid);
            query.setParameter(1, fid);
            query.executeUpdate();
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public long selectFanNum(int uid) {
        try {
            String hql = "";//此处的Product是对象
            Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from UserinfoEntity where uid = ?");
            query.setParameter(0, uid);
            return ((Long) query.setCacheable(true).uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public List<UserEntity> selectAllUser() {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserEntity");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean selectByUidAndFid(int uid, int fid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from UserinfoEntity where uid =? and fid = ?");
            query.setParameter(0, uid);
            query.setParameter(1, fid);
            List list = query.list();
            if (list.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
