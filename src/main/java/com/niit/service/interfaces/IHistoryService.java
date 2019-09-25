package com.niit.service.interfaces;

import com.niit.entity.HistoryEntity;

public interface IHistoryService {

    /**
     * 得到用户前十条历史记录
     *
     * @param uid
     * @return
     */
    String getNavigationBar(int uid);

    /**
     * 增加新历史
     *
     * @param historyEntity
     * @return
     */
    int addHistroy(HistoryEntity historyEntity);
}
