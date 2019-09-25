package com.niit.util;

import com.niit.entity.HistoryEntity;
import com.niit.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * 日期排序工具
 */
@Component
public class CompareHistoryDateUtil implements Comparator {

    public int compare(Object obj1, Object obj2) {
        HistoryEntity t1 = (HistoryEntity) obj1;
        HistoryEntity t2 = (HistoryEntity) obj2;
        return t2.getDate().compareTo(t1.getDate());
    }
}
