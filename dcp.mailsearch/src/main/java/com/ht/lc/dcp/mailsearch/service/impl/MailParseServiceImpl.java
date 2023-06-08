package com.ht.lc.dcp.mailsearch.service.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ht.lc.dcp.common.constants.CommonConst;
import com.ht.lc.dcp.common.utils.CommonUtils;
import com.ht.lc.dcp.common.utils.DateUtils;
import com.ht.lc.dcp.mailsearch.config.MailParseConfig;
import com.ht.lc.dcp.mailsearch.pojo.MailAddress;
import com.ht.lc.dcp.mailsearch.pojo.MailAttachment;
import com.ht.lc.dcp.mailsearch.pojo.MailInfo;
import com.ht.lc.dcp.mailsearch.service.MailParseService;
import com.ht.lc.dcp.mailsearch.utils.MailUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.simplejavamail.outlookmessageparser.model.OutlookAttachment;
import org.simplejavamail.outlookmessageparser.model.OutlookFileAttachment;
import org.simplejavamail.outlookmessageparser.model.OutlookMessage;
import org.simplejavamail.outlookmessageparser.model.OutlookMsgAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.NewsAddress;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:32
 * @Version 1.0
 **/
@Service
public class MailParseServiceImpl implements MailParseService {

    private static Logger LOG = LoggerFactory.getLogger(MailParseServiceImpl.class);

    @Autowired
    private MailParseConfig mailParseConfig;

    @Override
    public void parseMsg2JsonFile() {
        String parentDir = mailParseConfig.getMsgParentDir();
        if(!StringUtils.hasText(parentDir)) {
            LOG.error("msg src parent dir empty! ");
            return;
        }
        List<File> msgFiles = new ArrayList<>();
        // 获取所有文件
        File parentFile = new File(parentDir);
        String[] suffix = {"msg"};
        if (parentFile.isDirectory()) {
            msgFiles =(ArrayList) FileUtils.listFiles(parentFile, suffix, true);
        }
        // 文件不为空，解析内容
        if (!CollectionUtils.isEmpty(msgFiles)) {
            LOG.info("begin to parser msg mail files...");
            List<MailInfo> infos = new ArrayList<>(2);
            for (File f : msgFiles) {
                OutlookMessage msg = MailUtils.parseMsgFile(f);
                infos.add(fromOutlookMessage(msg, f));
            }
            // 大对象写入文件
            if (!CollectionUtils.isEmpty(infos)) {
                jsonObject2File(infos, mailParseConfig.getMsgResultJsonFile());
            }
            LOG.info("finish parse msg mail files, generate json file, list size: {}. ", infos.size());
        }
    }

    @Override
    public void parseEml2JsonFile() {
        String parentDir = mailParseConfig.getEmlParentDir();
        if(!StringUtils.hasText(parentDir)) {
            LOG.error("eml src parent dir empty! ");
            return;
        }
        List<File> emlFiles = new ArrayList<>();
        // 获取所有文件句柄
        File parentFile = new File(parentDir);
        String[] suffix = {"eml"};
        if (parentFile.isDirectory()) {
            emlFiles =(ArrayList) FileUtils.listFiles(parentFile, suffix, true);
        }
        // 文件不为空，解析内容
        if (!CollectionUtils.isEmpty(emlFiles)) {
            LOG.info("begin to parser eml mail files...");
            List<MailInfo> infos = new ArrayList<>();
            for (File f : emlFiles) {
                MimeMessageParser msg = MailUtils.parseEmlFile(f);
                infos.add(fromMimeMessage(msg, f));
            }
            if (!CollectionUtils.isEmpty(infos)) {
                jsonObject2File(infos, mailParseConfig.getEmlResultJsonFile());
            }
            LOG.info("finish parse eml mail files, generate json file, list size: {}. ", infos.size());
        }
    }

    /**
     * 从outlook文件转化为pojo，同时下载文件
     * @param message
     * @param file
     * @return
     */
    private MailInfo fromOutlookMessage(OutlookMessage message, File file) {
        LOG.info("mail file is : {}. ", file.getAbsolutePath());
        String sendName = StringUtils.hasText(message.getFromName()) ? message.getFromName().trim() : "";
        String sendEmail = StringUtils.hasText(message.getFromEmail()) ? message.getFromEmail().trim() : "";
        String uuid = sendEmail + "_" + CommonUtils.getRandomString(16);
        MailInfo mailInfo = new MailInfo();
        // 发件人
        MailAddress from = new MailAddress();
        from.setName(sendName);
        from.setEmail(sendEmail);
        mailInfo.setFfrom(from);

        // 主题内容时间
        mailInfo.setTitle(StringUtils.hasText(message.getSubject()) ? message.getSubject().trim() : "");
        mailInfo.setContent(message.getBodyText());
        mailInfo.setSendtime(DateUtils.date2String(message.getClientSubmitTime(), CommonConst.DateFormat.DATE_TIME_FORMAT));
        mailInfo.setOriginFileName(file.getName());
        mailInfo.setId(uuid);

        // 收件人、抄送人员处理
        List<MailAddress> to = new ArrayList<>();
        List<MailAddress> cc = new ArrayList<>();

        String[] toStr = message.getDisplayTo().split(";");
        if (toStr.length > 0) {
            to = Arrays.stream(toStr).map(t -> {
                MailAddress address = new MailAddress();
                if (t.indexOf("@") > 0) {
                    address.setEmail(t.trim());
                } else {
                    address.setName(t.trim());
                }
                return address;
            }).collect(Collectors.toList());
            mailInfo.setToList(to);
        }

        String[] ccStr = message.getDisplayCc().split(";");
        if (ccStr.length > 0) {
            cc = Arrays.stream(ccStr).map(c -> {
                MailAddress address = new MailAddress();
                if (c.indexOf("@") > 0) {
                    address.setEmail(c.trim());
                } else {
                    address.setName(c.trim());
                }
                return address;
            }).collect(Collectors.toList());
            mailInfo.setCcList(cc);
        }

        // 处理attachment
        List<OutlookAttachment> outlookAttachments = message.getOutlookAttachments();
        List<MailAttachment> attachments = new ArrayList<>();
        if (!CollectionUtils.isEmpty(outlookAttachments)) {
            attachments = outlookAttachments.stream().map(a -> {
                if (a instanceof OutlookFileAttachment) {
                    MailAttachment attachment = new MailAttachment();
                    OutlookFileAttachment temp = (OutlookFileAttachment)a;
                    attachment.setFilename(temp.getFilename());
                    String uri = mailParseConfig.getMsgAttachDir() + "/" + uuid + "_" + temp.getFilename();
                    attachment.setUri(uri);
                    // 下载文件
                    File attach = new File(mailParseConfig.getMsgParentDir() + mailParseConfig.getMsgAttachDir());
                    if (attach.exists()) {
                        attach.delete();
                    }
                    attach.mkdir();
                    try {
                        FileUtils.writeByteArrayToFile(new File(mailParseConfig.getMsgParentDir() + uri), temp.getData());
                    } catch (IOException e) {
                        LOG.error("download attachment error, file: {},  exception: {}. ", temp.getFilename(), e.getMessage());
                    }
                    return attachment;
                } else if (a instanceof OutlookMsgAttachment) {
                    return null;
                } else {
                    return null;
                }
            }).filter(m -> Objects.nonNull(m)).collect(Collectors.toList());
        }
        mailInfo.setAttachments(attachments);
        return mailInfo;
    }

    /**
     * Eml结果转化成MailInfo
     * @param parser
     * @param file
     * @return
     */
    private MailInfo fromMimeMessage(MimeMessageParser parser, File file) {
        LOG.info("eml mail file is : {}. ", file.getAbsolutePath());
        MailInfo mailInfo = new MailInfo();
        try {
            String uuid = parser.getFrom() + "_" + CommonUtils.getRandomString(16);
            mailInfo.setId(uuid);
            mailInfo.setTitle(parser.getSubject());
            mailInfo.setOriginFileName(file.getName());
            mailInfo.setContent(StringUtils.hasText(parser.getPlainContent()) ? parser.getPlainContent() : parser.getHtmlContent());
            mailInfo.setSendtime(DateUtils.date2String(parser.getMimeMessage().getSentDate(), CommonConst.DateFormat.DATE_TIME_FORMAT));
            // 收件人
            MailAddress mailSender = new MailAddress();
            Address[] sender = parser.getMimeMessage().getFrom();
            if (sender.length == 0) {
                mailSender.setEmail(parser.getFrom());
            } else {
                mailSender = fromAddress(sender[0]);
            }
            mailInfo.setFfrom(mailSender);

            // 收件人、抄送人员处理
            List<MailAddress> to = new ArrayList<>();
            List<MailAddress> cc = new ArrayList<>();
            to = parser.getTo().stream().map(t -> fromAddress(t)).filter(m -> Objects.nonNull(m)).collect(Collectors.toList());
            cc = parser.getCc().stream().map(c -> fromAddress(c)).filter(m -> Objects.nonNull(m)).collect(Collectors.toList());
            mailInfo.setToList(to);
            mailInfo.setCcList(cc);

            // 处理附件
            List<MailAttachment> attachments = new ArrayList<>();
            if (parser.hasAttachments()) {
                List<DataSource> rawAttachList = parser.getAttachmentList();
                attachments = rawAttachList.stream().map(a -> {
                    if (a instanceof ByteArrayDataSource) {
                        ByteArrayDataSource temp = (ByteArrayDataSource)a;
                        if (Objects.isNull(a.getName()) || a.getContentType().startsWith("message")) {
                            return null;
                        }
                        MailAttachment attachment = new MailAttachment();
                        attachment.setFilename(temp.getName());
                        String uri = mailParseConfig.getEmlAttachDir() + "/" + uuid + "_" + temp.getName();
                        attachment.setUri(uri);
                        // 下载文件
                        File attach = new File(mailParseConfig.getEmlParentDir() + mailParseConfig.getEmlAttachDir());
                        if (attach.exists()) {
                            attach.delete();
                        }
                        attach.mkdir();
                        try {
                            FileUtils.copyToFile(temp.getInputStream(), new File(mailParseConfig.getEmlParentDir() + uri));
                        } catch (IOException e) {
                            LOG.error("download attachment error, file: {},  exception: {}. ", temp.getName(), e.getMessage());
                        }
                        return attachment;
                    } else {
                        return null;
                    }
                }).filter(m -> Objects.nonNull(m)).collect(Collectors.toList());
            }
            mailInfo.setAttachments(attachments);
        } catch (Exception e) {
            LOG.error("parse MimeMessage error,exception: {}. ", e.getMessage());
        }
        return mailInfo;
    }

    /**
     * 从原始Address解析发件人
     * @param address
     * @return
     */
    private MailAddress fromAddress(Address address) {
        if (Objects.isNull(address)) {
            return null;
        }
        MailAddress mailAddress = new MailAddress();
        if (address instanceof InternetAddress) {
            InternetAddress ia = (InternetAddress) address;
            mailAddress.setName(ia.getPersonal());
            mailAddress.setEmail(ia.getAddress());
        } else if (address instanceof NewsAddress) {
            mailAddress.setEmail(address.toString());
        } else {
            mailAddress.setEmail(address.toString());
        }
        return mailAddress;
    }

    /**
     * json 对象写入文件
     * @param json
     * @param filePath
     */
    private void jsonObject2File(Object json, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            ObjectMapper mapper = new ObjectMapper();
//            mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//                @Override
//                public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                    jsonGenerator.writeString("");
//                }
//            });
            mapper.writeValue(file, json);
        } catch (IOException e) {
            LOG.error("create json file failed! ");
        }
    }
}
