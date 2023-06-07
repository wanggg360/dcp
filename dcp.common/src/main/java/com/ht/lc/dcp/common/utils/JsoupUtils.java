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

    private static final Logger LOG = LoggerFactory.getLogger(JsoupUtils.class);

    private JsoupUtils() {
    }

    public static Document getDocFromStr(String input) throws ServiceException {
        if (!StringUtils.hasText(input)) {
            throw new ServiceException("jsoup error, input string empty. ");
        }
        return Jsoup.parse(input);
    }

    public static Element getElementById(Document doc, String id) throws ServiceException {
        validateDocument(doc);
        return doc.getElementById(id);
    }

    public static Elements getElementsByClass(Document doc, String className) throws ServiceException {
        validateDocument(doc);
        return doc.getElementsByClass(className);
    }

    public static void validateDocument(Document document) throws ServiceException {
        if (Objects.isNull(document)) {
            throw new ServiceException("jsoup error, input doc is null. ");
        }
    }
}
