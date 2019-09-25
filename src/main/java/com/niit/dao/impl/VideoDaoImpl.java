package com.niit.dao.impl;

import com.niit.dao.interfaces.IVideoDao;
import com.niit.entity.VideoEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.niit.util.Constant.FAILEDCODE;
import static com.niit.util.Constant.SUCCEEDCODE;

@Repository
public class VideoDaoImpl implements IVideoDao {

    @Autowired
    private SessionFactory sessionFactory;

    public VideoEntity selectByVid(int vid) {
        try {
            return sessionFactory.getCurrentSession().get(VideoEntity.class, vid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByUid(int uid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where uid = ?");
            query.setParameter(0, uid);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByCategory(int category) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ?");
            query.setParameter(0, category);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByPopularitynum(int category) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ? order by popularitynum desc");
            query.setParameter(0, category);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByDate(int category) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ? order by date desc");
            query.setParameter(0, category);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    /**
     * @param category 0查询所有类别视频中最高点击量 else按类别查询最高点击量
     * @return
     */
    public List<VideoEntity> selectByView(int category) {
        try {
            Query query = null;
            if (category == 0) {
                query = sessionFactory.getCurrentSession().createQuery("from VideoEntity order by view desc");
                query.setMaxResults(6);
                List<VideoEntity> videoEntityList = query.list();
                for (VideoEntity videoEntity : videoEntityList) {
                    sessionFactory.getCurrentSession().evict(videoEntity);//缓冲对象进行清除
                }
                return videoEntityList;
            } else {
                query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ? order by view desc");
                query.setParameter(0, category);
                query.setMaxResults(4);
                return query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByDanmakunum(int category) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ? order by danmakunum desc");
            query.setParameter(0, category);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VideoEntity> selectByTitle(String key, int category, int everyPageNum) {
        int currentPage = 1;
        int firstResult = 0;
        try {
            if (category == 0) {
                Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? ");
                query.setParameter(0, "%" + key + "%");
                if (everyPageNum != 0) {
                    query.setMaxResults(everyPageNum);
                }
                firstResult = everyPageNum * (currentPage - 1);
                query.setFirstResult(firstResult);
                return query.list();
            } else {
                Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? and category = ?");
                query.setParameter(0, "%" + key + "%");
                query.setParameter(1, category);
                if (everyPageNum != 0) {
                    query.setMaxResults(everyPageNum);
                }
                firstResult = everyPageNum * (currentPage - 1);
                query.setFirstResult(firstResult);
                return query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int selectCountByTitle(String key, int category) {
        try {
            if (category == 0) {
                Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where title like ? ");
                query.setParameter(0, "%" + key + "%");
                long count = (long) query.uniqueResult();
                return (int) count;
            } else {
                Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? and category = ?");
                query.setParameter(0, "%" + key + "%");
                long count = (long) query.uniqueResult();
                return (int) count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<VideoEntity> selectByCategoryAndType(String key, int category, int type, int firstResult, int everyPageNum) {
//        System.out.println("key:" + key);
        try {
//            if (!((key.equals("") && key.equals("undefined") && key.equals(null)))) {
            if (key.equals("")) {
//                System.out.println("type:" + type);
//                System.out.println("category:" + category);
                if (category == 0) {
                    if (type == 8) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity order by popularitynum desc");
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);//表示的是从查询记录的地几个开始，而不是从第几页开始
                        return query.list();
                    }
                    if (type == 9) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity order by date desc");
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                    if (type == 10) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity order by view desc");
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                    if (type == 11) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity order by danmakunum desc");
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                } else {
                    if (type == 8) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where  category = ? order by popularitynum desc");
                        query.setParameter(0, category);
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);//表示的是从查询记录的地几个开始，而不是从第几页开始
                        return query.list();
                    }
                    if (type == 9) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where  category = ? order by date desc");
                        query.setParameter(0, category);
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                    if (type == 10) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where  category = ? order by view desc");
                        query.setParameter(0, category);
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                    if (type == 11) {
                        Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where category = ? order by danmakunum desc");
                        query.setParameter(0, category);
                        if (everyPageNum != 0) {
                            query.setMaxResults(everyPageNum);
                        }
                        query.setFirstResult(firstResult);
                        return query.list();
                    }
                }
            } else {
                if (type == 8) {
                    Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? and category = ? order by popularitynum desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    if (everyPageNum != 0) {
                        query.setMaxResults(everyPageNum);
                    }
                    query.setFirstResult(firstResult);
                    return query.list();
                }
                if (type == 9) {
                    Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? and category = ? order by date desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    if (everyPageNum != 0) {
                        query.setMaxResults(everyPageNum);
                    }
                    query.setFirstResult(firstResult);
                    return query.list();
                }
                if (type == 10) {
                    Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where title like ? and category = ? order by view desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    if (everyPageNum != 0) {
                        query.setMaxResults(everyPageNum);
                    }
                    query.setFirstResult(firstResult);
                    return query.list();
                }
                if (type == 11) {
                    Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where  title like ? and category = ? order by danmakunum desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    if (everyPageNum != 0) {
                        query.setMaxResults(everyPageNum);
                    }
                    query.setFirstResult(firstResult);
                    return query.list();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int selectCountByCategoryAndType(String key, int category, int type) {

        try {
            if (key.equals("")) {
                if (category == 0) {
                    if (type == 8) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity order by popularitynum desc");
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 9) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity order by date desc");
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 10) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity order by view desc");
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 11) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity order by danmakunum desc");
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                } else {
                    if (type == 8) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where  category = ? order by popularitynum desc");
                        query.setParameter(0, category);
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 9) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where  category = ? order by date desc");
                        query.setParameter(0, category);
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 10) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where  category = ? order by view desc");
                        query.setParameter(0, category);
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                    if (type == 11) {
                        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where category = ? order by danmakunum desc");
                        query.setParameter(0, category);
                        long count = (long) query.uniqueResult();
                        return (int) count;
                    }
                }
            } else {
                if (type == 8) {
                    Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where title like ? and category = ? order by popularitynum desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    long count = (long) query.uniqueResult();
                    return (int) count;
                }
                if (type == 9) {
                    Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where title like ? and category = ? order by date desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    long count = (long) query.uniqueResult();
                    return (int) count;
                }
                if (type == 10) {
                    Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where title like ? and category = ? order by view desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    long count = (long) query.uniqueResult();
                    return (int) count;
                }
                if (type == 11) {
                    Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from VideoEntity where  title like ? and category = ? order by danmakunum desc");
                    query.setParameter(0, "%" + key + "%");
                    query.setParameter(1, category);
                    long count = (long) query.uniqueResult();
                    return (int) count;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addVideo(VideoEntity videoEntity) {
        try {
            Serializable vid = sessionFactory.getCurrentSession().save(videoEntity);
            return (Integer) vid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    public int alterVideo(VideoEntity videoEntity) {
        try {
            sessionFactory.getCurrentSession().update(videoEntity);
            return SUCCEEDCODE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAILEDCODE;
    }

    @Override
    public List<VideoEntity> selectByUidAndVid(int uid, int vid) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from VideoEntity where uid = ? and vid = ? order by date desc");
            query.setParameter(0, uid);
            query.setParameter(1, vid);
            query.setMaxResults(1);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
