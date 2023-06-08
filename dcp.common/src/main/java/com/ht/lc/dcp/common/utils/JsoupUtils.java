package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.exception.ServiceException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-17 14:30
 * @Version 1.0
 **/

public class JsoupUtils {

    private JsoupUtils() {
    }
    public static Document getDocFromStr(String input) {
        return StringUtils.hasText(input) ? Jsoup.parse(input) : null;
    }

    public static Element getElementById(Document doc, String id) {
        return Objects.isNull(doc) ? null : doc.getElementById(id);
    }

    public static Elements getElementsByClass(Document doc, String className) {

        return Objects.isNull(doc) ? null : doc.getElementsByClass(className);
    }
}
