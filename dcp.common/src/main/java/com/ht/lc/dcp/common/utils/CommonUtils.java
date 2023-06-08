package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.constants.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

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

    private CommonUtils() {
    }

    /**
     * 获取固定位数随机数
     *
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(62);
            sb.append(CHARACTER_SET.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 切分数组
     *
     * @param src
     * @param num
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> src, int num) {
        List<List<T>> resultList = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(src)) {
            LOG.error("src list empty. ");
            return resultList;
        }
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = src.size() / num;
        List<T> subList;
        for (int i = 0; i <= insertTimes; i++) {
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

    /**
     * 检查字符串是否满足表达式
     *
     * @param pattern
     * @param source
     * @return
     */
    public static boolean checkStringWithPattern(String pattern, String source) {
        return Pattern.matches(pattern, source);
    }

    /**
     * 判断是否是有效的html的url
     *
     * @param url
     * @return
     */
    public static boolean isValidHtmlUrl(String url) {
        return (url.startsWith("http") && url.endsWith("html"));
    }
}
