package com.niit.service.impl;

import com.niit.dao.impl.DanmakuDaoImpl;
import com.niit.entity.DanmakuEntity;
import com.niit.service.interfaces.IDanmakuService;
import com.niit.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@Service
public class DanmakuServiceImpl implements IDanmakuService {

    @Autowired
    private DanmakuDaoImpl dao;
    @Autowired
    private JSONUtil jsonUtil;

    public String getDanmakuByVid(int vid) {
        List<DanmakuEntity> danmakuEntityList = dao.selectByVid(vid);
        for (DanmakuEntity danmakuEntity : danmakuEntityList) {
            Time dbCurrenttime = danmakuEntity.getDbCurrenttime();
            int hours = dbCurrenttime.getHours();
            int minutes = dbCurrenttime.getMinutes();
            int seconds = dbCurrenttime.getSeconds();
            danmakuEntity.setCurrenttime(hours * 60 * 60 + minutes * 60 + seconds);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dd = format.format(danmakuEntity.getDbDate());
            danmakuEntity.setDate(dd);
        }
        return jsonUtil.toJSon(danmakuEntityList);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addDanmaku(DanmakuEntity danmakuEntity) {
        return dao.addDanmaku(danmakuEntity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int reportDanmaku(int did) {
        DanmakuEntity danmakuEntity = dao.selectByDid(did);
        danmakuEntity.setStatus(1);
        return dao.alterDanmaku(danmakuEntity);
    }

    public void deleteDanmaku() {

    }
}
