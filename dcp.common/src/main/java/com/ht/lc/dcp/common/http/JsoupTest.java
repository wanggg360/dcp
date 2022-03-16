package com.ht.lc.dcp.common.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-11 15:39
 * @Version 1.0
 **/
public class JsoupTest {

    public static void main(String[] args) throws Exception {
        Document doc =  Jsoup.connect("http://www.csrc.gov.cn/csrc/c106259/common_list_gd.shtml").get();
        String aaa = doc.toString();
        System.out.println(aaa);
    }
}
