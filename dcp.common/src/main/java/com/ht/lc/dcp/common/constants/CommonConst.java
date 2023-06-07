package com.ht.lc.dcp.common.constants;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-04-21 09:00
 * @Version 1.0
 **/
public interface CommonConst {

    interface Number {

        int NUM_0 = 0;

        int NUM_1 = 1;

        int NUM_4 = 4;

        int NUM_5 = 5;

        int NUM_6 = 6;

        int NUM_10 = 10;

        int NUM_100 = 100;

        int NUM_200 = 200;
    }

    interface RegexRule {

        String VALID_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

        String VALID_MOBILE = "^1[23456789]\\d{9}$";

        String DATE_FORMAT_PATTERN_1 =
                "[1-4][0-9]{3}年(([1-9]|[1][0-2])|([0][1-9]|[1][0-2]))月(([1-9]|[1-3][0-9])|([0][1-9]|[1-3][0-9]))日";

        String DATE_FORMAT_PATTERN_2 = "[1-4][0-9]{3}-([0][1-9]|[1][0-2])-([0][1-9]|[1-3][0-9])";
    }

    interface DateFormat {

        String DATE_FORMAT_NORMAL = "yyyy-MM-dd";

        String DATE_FORMAT_TIMESTAMP = "yyyyMMddHHmmss";

        String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    interface Symbol {
        String HYPHEN = "-";
    }
}
