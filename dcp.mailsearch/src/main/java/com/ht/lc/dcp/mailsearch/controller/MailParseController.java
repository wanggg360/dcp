package com.ht.lc.dcp.mailsearch.controller;

import com.ht.lc.dcp.mailsearch.service.MailParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:35
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/mailsearch")

public class MailParseController {

    @Autowired
    private MailParseService mailParseService;

    @RequestMapping(value = "/parseMsgFile", method = RequestMethod.POST)
    public void parseMsgFile() {
        mailParseService.parseMsg2JsonFile();
    }

    @RequestMapping(value = "/parseEmlFile", method = RequestMethod.POST)
    public void parseEmlFile() {
        mailParseService.parseEml2JsonFile();
    }

}
