package com.ht.lc.dcp.task.utils;

import com.ht.lc.dcp.common.utils.CommonUtils;
import com.ht.lc.dcp.task.constant.BizConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-20 20:00
 * @Version 1.0
 **/
public class ComUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ComUtils.class);

    private ComUtils() {}

    public static boolean checkStr(String pattern, String source) {
        return Pattern.matches(pattern, source);
    }

    public static boolean isValidHtmlUrl(String url) {
        return (url.startsWith("http") && url.endsWith("html"));
    }

    /**
     * 将2000年1月1日转化成时间类型
     * @param date 日期字符串
     * @return 日期
     */
    public static LocalDate cvtString2Date(String date) {
        LocalDate localDate = null;
        String temp = StringUtils.trimAllWhitespace(date);
        if (!ComUtils.checkStr(BizConst.ElementKeyStr.NOTICE_DETAILS_DATE_FORMAT_PATTERN, temp)) {
            LOG.error("notice details date string format not correct, str:{}. ", temp);
            return localDate;
        }
        temp = date.replaceAll("年|月|日", BizConst.Common.SYMBOL_HYPHEN);
        temp = temp.substring(0, temp.length() - 1);
        String[] dateSplit = temp.split(BizConst.Common.SYMBOL_HYPHEN);
        String y = dateSplit[0];
        String m = dateSplit[1].length() == 1 ? "0" + dateSplit[1] : dateSplit[1];
        String d = dateSplit[2].length() == 1 ? "0" + dateSplit[2] : dateSplit[2];
        temp = y + BizConst.Common.SYMBOL_HYPHEN + m + BizConst.Common.SYMBOL_HYPHEN + d;
        return LocalDate.parse(temp, DateTimeFormatter.ofPattern(BizConst.Common.DATE_FORMAT_NORMAL));
    }

    public static String generateUniqueTaskId() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(BizConst.Common.DATE_FORMAT_TIMESTAMP);
        String dateTime = dtf.format(time);
        return dateTime + "-" + CommonUtils.getRandomString(3);
    }

    public static void main(String[] args) {
        String aa = "2022年1月4日";
        LocalDate aaaa = cvtString2Date(aa);
    }
}
