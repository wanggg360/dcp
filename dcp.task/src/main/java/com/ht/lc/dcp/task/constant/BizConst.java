package com.ht.lc.dcp.task.constant;

/**
 * @program: dcp
 * @description: 业务常量
 * @author: wanggang
 * @create: 2022-03-20 19:44
 * @Version 1.0
 **/
public interface BizConst {

    interface Common {

        String DATE_FORMAT_NORMAL = "yyyy-MM-dd";

        String DATE_FORMAT_TIMESTAMP = "yyyyMMddHHmmss";

        // 格式：2021-01-09
        String PARAMS_DATE_FORMAT_PATTERN = "[1-4][0-9]{3}-([0][1-9]|[1][0-2])-([0][1-9]|[1-3][0-9])";

        String DATA_FLAG_VALID = "1";

        String DATA_FLAG_INVALID = "0";

        String SYMBOL_HYPHEN = "-";
    }

    interface DataType {
        int SUP_MEASURE = 1;
    }

    interface ElementKeyStr {

        int NOTICE_DETAILS_FORMAT_TYPE_1 = 1;

        int NOTICE_DETAILS_FORMAT_TYPE_2 = 2;

        // 解析监管措施相关字符串
        String LOC_PAGE_SIZE = "input#pageSize";

        String DEFAULT_PAGE_SIZE = "18";

        String PAGE_SPLIT_SYMBOL = "_";

        String PAGE_URL_TAG = "common_list_gd";

        String LOC_PAGE_INFO_FUN = "script:containsData(createPageHTML)";

        String PAGE_INFO_CHECK_PATTERN = "(.*page_div.*|.*shtml.*)";

        String NUMBER_CHECK_PATTERN = "[0-9]+";

        String LOC_SUP_MEASURE_LIST = "div.main-right.fr.common-list > ul#list.list.mt10 > li";

        // 证监会
        String LOC_NOTICE_DETAILS_TITLE_ZJH = "div.main > div.content > h2";

        String LOC_NOTICE_DETAILS_TEXT_ZJH = "div.detail-news > p > span:has(font)";

        String LOC_NOTICE_DETAILS_TEXT_CHILD_ZJH = "span > font[face]";

        String LOC_NOTICE_DETAILS_OBJECT_ZJH = "div.detail-news > p:not([style~=(text-indent)]) > span > font";

        // 北京
        String LOC_NOTICE_DETAILS_TITLE_BJ = "div.main > div.content > h2";

        String LOC_NOTICE_DETAILS_TEXT_BJ = "div.detail-news > p[style]:not([align=center]) > font[face]";

        String LOC_NOTICE_DETAILS_TEXT_CHILD_BJ = "font > span";

        String LOC_NOTICE_DETAILS_OBJECT_BJ =
                "div.detail-news > p[style=line-height: 1.5;]:not([align]) > font[face] > span";

        String NOTICE_DETAILS_DATE_FORMAT_PATTERN =
                "[1-4][0-9]{3}年(([1-9]|[1][0-2])|([0][1-9]|[1][0-2]))月(([1-9]|[1-3][0-9])|([0][1-9]|[1-3][0-9]))日";

        String ATTR_VALUE = "value";

        String ATTR_HREF = "href";

        String TAG_A = "a";

        String TAG_SPAN = "span";
    }
}
