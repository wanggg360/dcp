package com.ht.lc.dcp.mailsearch.service;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:30
 * @Version 1.0
 **/
public interface MailParseService {

    void parseMsg2JsonFile();

    void parseEml2JsonFile();
}
