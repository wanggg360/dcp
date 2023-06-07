package com.ht.lc.dcp.mailsearch.utils;

//import jakarta.mail.MessagingException;
//import jakarta.mail.Session;
//import jakarta.mail.internet.MimeMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.simplejavamail.outlookmessageparser.model.OutlookMessage;
import org.simplejavamail.outlookmessageparser.OutlookMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:26
 * @Version 1.0
 **/
public class MailUtils {

    private MailUtils() {}
    private static final Logger LOG = LoggerFactory.getLogger(MailUtils.class);

    private static final String MSG_FILE_SUFFIX = ".msg";

    private static final String EML_FILE_SUFFIX = ".eml";

    private static final OutlookMessageParser parser = new OutlookMessageParser();

    private static final Session session = Session.getDefaultInstance(new Properties(), null);

    public static OutlookMessage parseMsgFile(File msgFile) {
        OutlookMessage outlookMessage = new OutlookMessage();
        if (Objects.isNull(msgFile) || msgFile.isDirectory() || !msgFile.getAbsolutePath().endsWith(MSG_FILE_SUFFIX)) {
            LOG.error("file is wrong, please check, path: {}. ", msgFile.getAbsolutePath());
            return outlookMessage;
        }
        try {
            outlookMessage = parser.parseMsg(msgFile);
        } catch (IOException e) {
            LOG.error("parse msg meet exception, file: {}. ", msgFile.getAbsolutePath());
        }
        return outlookMessage;
    }

    public static MimeMessageParser parseEmlFile(File emlFile) {
        MimeMessageParser parser = null;
        if (Objects.isNull(emlFile) || emlFile.isDirectory() || !emlFile.getAbsolutePath().endsWith(EML_FILE_SUFFIX)) {
            LOG.error("file is wrong, please check, path: {}. ", emlFile.getAbsolutePath());
            return parser;
        }
        try {
            InputStream is = FileUtils.openInputStream(emlFile);
            MimeMessage message = new MimeMessage(session, is);
            parser = new MimeMessageParser(message).parse();
        } catch (Exception e) {
            LOG.error("parse eml meet exception, file: {}. ", emlFile.getAbsolutePath());
        }
        return parser;
    }

    //public static  MimeUtility.decodeText(MimeUtility.unfold(rawvalue));
}
