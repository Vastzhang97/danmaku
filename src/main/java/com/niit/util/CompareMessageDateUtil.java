package com.niit.util;

import com.niit.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 日期排序工具
 */
@Component
public class CompareMessageDateUtil implements Comparator {

    public int compare(Object obj1, Object obj2) {
        MessageEntity t1 = (MessageEntity) obj1;
        MessageEntity t2 = (MessageEntity) obj2;
        return t1.getDate().compareTo(t2.getDate());
    }
}
