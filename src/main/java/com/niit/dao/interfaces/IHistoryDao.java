package com.niit.dao.interfaces;

import com.niit.entity.HistoryEntity;

import java.util.List;

public interface IHistoryDao {

    int addHistory(HistoryEntity historyEntity);

    HistoryEntity selectByHid(int hid);

    /**
     * 不重复vid的history
     * @param uid
     * @return
     */
    List<Integer> selectByUid(int uid);

    int alterHistory(HistoryEntity historyEntity);

    List<HistoryEntity> selectByUidAndVid(int uid,int vid);
}
