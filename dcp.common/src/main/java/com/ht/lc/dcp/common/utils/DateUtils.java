package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.constants.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 17:13
 * @Version 1.0
 **/
public class DateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {}

    public static String date2String(Date date, String format) {
        String result = "";
        if (!StringUtils.hasText(format) || Objects.isNull(date)) {
            return result;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 将2000年1月1日转化成时间类型
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static LocalDate cvtString2Date(String date) {
        LocalDate localDate = null;
        String temp = StringUtils.trimAllWhitespace(date);
        if (!CommonUtils.checkStr(CommonConst.RegexRule.DATE_FORMAT_PATTERN_1, temp)) {
            LOG.error("notice details date string format not correct, str:{}. ", temp);
            return localDate;
        }
        temp = date.replaceAll("年|月|日", CommonConst.Symbol.HYPHEN);
        temp = temp.substring(0, temp.length() - 1);
        String[] dateSplit = temp.split(CommonConst.Symbol.HYPHEN);
        String y = dateSplit[0];
        String m = dateSplit[1].length() == 1 ? "0" + dateSplit[1] : dateSplit[1];
        String d = dateSplit[2].length() == 1 ? "0" + dateSplit[2] : dateSplit[2];
        temp = y + CommonConst.Symbol.HYPHEN + m + CommonConst.Symbol.HYPHEN + d;
        return LocalDate.parse(temp, DateTimeFormatter.ofPattern(CommonConst.DateFormat.DATE_FORMAT_NORMAL));
    }
}
