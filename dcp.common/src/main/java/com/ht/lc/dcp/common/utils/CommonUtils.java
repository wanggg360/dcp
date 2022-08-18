package com.ht.lc.dcp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-30 17:09
 * @Version 1.0
 **/
public class CommonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private CommonUtils() {}

    public static String getRandomString(int len) {
        Random random=new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<len; i++){
            int number=random.nextInt(62);
            sb.append(CHARACTER_SET.charAt(number));
        }
        return sb.toString();
    }

    public static <T> List<List<T>> splitList(List<T> src, int num) {
        List<List<T>> resultList = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(src)) {
            LOG.error("src list empty. ");
            return resultList;
        }
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = src.size()/num;
        List<T> subList;
        for (int i = 0;i <= insertTimes;i++) {
            priIndex = num * i;
            lastPriIndex = priIndex + num;
            if (i == insertTimes) {
                subList = src.subList(priIndex, src.size());
            } else {
                subList = src.subList(priIndex, lastPriIndex);
            }
            if (subList.size() > 0) {
                resultList.add(subList);
            }
        }
        return resultList;
    }
}
