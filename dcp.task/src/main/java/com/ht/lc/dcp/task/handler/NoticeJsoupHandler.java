package com.ht.lc.dcp.task.handler;

import com.ht.lc.dcp.common.constants.CommonConst;
import com.ht.lc.dcp.common.exception.ServiceException;
import com.ht.lc.dcp.common.utils.CommonUtils;
import com.ht.lc.dcp.common.utils.DateUtils;
import com.ht.lc.dcp.common.utils.JsoupUtils;
import com.ht.lc.dcp.task.constant.BizConst;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.NoticeDetails;
import com.ht.lc.dcp.task.entity.NoticePageInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-17 14:30
 * @Version 1.0
 **/

public class NoticeJsoupHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NoticeJsoupHandler.class);

    private static int[] NOTICE_DETAILS_TYPE_ARRAY =
            {BizConst.ElementKeyStr.NOTICE_DETAILS_FORMAT_TYPE_1, BizConst.ElementKeyStr.NOTICE_DETAILS_FORMAT_TYPE_2};

    private NoticeJsoupHandler() {
    }

    public static NoticePageInfo getNoticePageInfo(Document document) throws ServiceException {
        JsoupUtils.validateDocument(document);
        NoticePageInfo noticePageInfo = new NoticePageInfo();
        String pageSize = Optional.ofNullable(document.select(BizConst.ElementKeyStr.LOC_PAGE_SIZE).first())
                .map(e -> e.attr(BizConst.ElementKeyStr.ATTR_VALUE)).orElse(BizConst.ElementKeyStr.DEFAULT_PAGE_SIZE);
        noticePageInfo.setPageSize(Integer.valueOf(pageSize));

        String pageInfo = document.select(BizConst.ElementKeyStr.LOC_PAGE_INFO_FUN).first().data();

        if (!CommonUtils.checkStr(BizConst.ElementKeyStr.PAGE_INFO_CHECK_PATTERN, pageInfo)) {
            LOG.info("page info string not meet the pattern, str: {}. ", pageInfo);
            return noticePageInfo;
        }
        String temp = pageInfo.substring(pageInfo.indexOf('(') + 1, pageInfo.indexOf(')'));
        String[] strArray = temp.split(",");
        if (strArray.length < CommonConst.Number.NUM_6) {
            LOG.info("page info string size not correct, str: {}. ", pageInfo);
            return noticePageInfo;
        }
        String pageCnt = strArray[CommonConst.Number.NUM_1];
        String totalCnt = strArray[CommonConst.Number.NUM_5];
        if (CommonUtils.checkStr(BizConst.ElementKeyStr.NUMBER_CHECK_PATTERN, pageCnt)) {
            noticePageInfo.setPageCnt(Integer.valueOf(pageCnt));
        }
        if (CommonUtils.checkStr(BizConst.ElementKeyStr.NUMBER_CHECK_PATTERN, totalCnt)) {
            noticePageInfo.setTotalCnt(Integer.valueOf(totalCnt));
        }
        return noticePageInfo;
    }

    /**
     * 由于每个局数据格式不一样，同一个局中的数据格式也会不一样因此需要切换模式处理。
     *
     * @param document
     * @return
     */
    public static NoticeDetails cycleGenerateNoticeDetailsFromDoc(Document document, NoticeBrief nb) {
        JsoupUtils.validateDocument(document);
        NoticeDetails noticeDetails = new NoticeDetails();

        for (int type : NOTICE_DETAILS_TYPE_ARRAY) {
            noticeDetails = generateNoticeDetailsByType(document, type);
            if (StringUtils.hasText(noticeDetails.getContent())) {
                break;
            }
        }
        noticeDetails.setTaskId(nb.getTaskId());
        noticeDetails.setBriefId(nb.getId());
        noticeDetails.setPublishDate(nb.getPublishDate());
        return noticeDetails;
    }

    private static NoticeDetails generateNoticeDetailsByType(Document document, int type) {
        NoticeDetails noticeDetails = new NoticeDetails();
        String titlePattern, objectPattern, contentPattern, contentChildPattern = "";
        switch (type) {
            case BizConst.ElementKeyStr.NOTICE_DETAILS_FORMAT_TYPE_1:
                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_ZJH;
                objectPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_ZJH;
                contentPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_ZJH;
                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_ZJH;
                break;

            case BizConst.ElementKeyStr.NOTICE_DETAILS_FORMAT_TYPE_2:
                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_BJ;
                objectPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_BJ;
                contentPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_BJ;
                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_BJ;
                break;

            default:
                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_BJ;
                objectPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_BJ;
                contentPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_BJ;
                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_BJ;

        }
        Elements titleEles = document.select(titlePattern);
        if (titleEles.size() != CommonConst.Number.NUM_0) {
            noticeDetails.setTitle(titleEles.first().html());
        }
        Elements objectEles = document.select(objectPattern);
        if (objectEles.size() != CommonConst.Number.NUM_0) {
            noticeDetails.setNoticeObject(objectEles.first().html());
        }
        Elements contents = document.select(contentPattern);
        if (contents.size() == CommonConst.Number.NUM_0) {
            LOG.error("notice details content element empty, can not get content! ");
            return noticeDetails;
        }
        String content = parseNoticeDetailsContent(contents, contentChildPattern);
        noticeDetails.setContent(content);
        String dateStr = parseNoticeDetailsElement(contents.last(), contentChildPattern);
        LocalDate localDate = DateUtils.cvtString2Date(dateStr);
        noticeDetails.setNoticeDate(localDate);
        return noticeDetails;
    }

    //    public static NoticeDetails getNoticeDetailsFromDoc(Document document, NoticeBrief nb) {
    //        validateDocument(document);
    //        NoticeDetails noticeDetails = new NoticeDetails();
    //        // 从brief信息中获取已有值
    //        noticeDetails.setTaskId(nb.getTaskId());
    //        noticeDetails.setBriefId(nb.getId());
    //        noticeDetails.setPublishDate(nb.getPublishDate());
    //        String titlePattern, objectPattern, contentPattern, contentChildPattern = "";
    //        switch (nb.getBranchId()) {
    //            case "1" :
    //                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_ZJH;
    //                objectPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_ZJH;
    //                contentPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_ZJH;
    //                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_ZJH;
    //                break;
    //
    //            case "11" :
    //                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_BJ;
    //                objectPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_BJ;
    //                contentPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_BJ;
    //                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_BJ;
    //                break;
    //
    //            default:
    //                titlePattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_BJ;
    //                objectPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_BJ;
    //                contentPattern =  BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_BJ;
    //                contentChildPattern = BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_CHILD_BJ;
    //
    //        }
    //        Elements titleEles = document.select(titlePattern);
    //        if (titleEles.size() != ComConstant.Number.NUM_0) {
    //            noticeDetails.setTitle(titleEles.first().html());
    //        }
    //        Elements objectEles = document.select(objectPattern);
    //        if (objectEles.size() != ComConstant.Number.NUM_0) {
    //            noticeDetails.setNoticeObject(objectEles.first().html());
    //        }
    //        Elements contents = document.select(contentPattern);
    //        if (contents.size() == ComConstant.Number.NUM_0) {
    //            LOG.error("notice details content element empty, can not get content! ");
    //            return noticeDetails;
    //        }
    //        String content = parseNoticeDetailsContent(contents, contentChildPattern);
    //        noticeDetails.setContent(content);
    //
    //        String dateStr = parseNoticeDetailsElement(contents.last(), contentChildPattern);
    //        LocalDate localDate = ComUtils.cvtString2Date(dateStr);
    //        noticeDetails.setNoticeDate(localDate);
    //        return noticeDetails;
    //    }

    private static String parseNoticeDetailsContent(Elements elements, String contentChildPattern) {
        StringBuffer sb = new StringBuffer();
        String temp = "";
        for (Element e : elements) {
            temp = parseNoticeDetailsElement(e, contentChildPattern);
            sb.append(temp);
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String parseNoticeDetailsElement(Element element, String ccp) {
        Elements children = element.select(ccp);
        if (children.size() == 0) {
            LOG.error("notice details content element empty, can not get content! ");
            return "";
        }
        StringBuffer sb = new StringBuffer();
        children.stream().forEach(c -> sb.append(c.text()));
        return sb.toString();
    }

    public static List<NoticeBrief> getNoticeBriefListFromDoc(Document document, int dataType) {
        JsoupUtils.validateDocument(document);
        List<NoticeBrief> briefList = new ArrayList<>(2);
        switch (dataType) {
            case BizConst.DataType.SUP_MEASURE:
                briefList = parseSupMeasureBriefList(document);
                break;
            default:
                break;
        }
        return briefList;
    }

    private static List<NoticeBrief> parseSupMeasureBriefList(Document document) {
        List<NoticeBrief> list = new ArrayList<>(4);
        Elements elements = document.select(BizConst.ElementKeyStr.LOC_SUP_MEASURE_LIST);
        if (elements.isEmpty()) {
            LOG.error("can not parse sup measure brief from doc, elements empty. ");
            return list;
        }
        for (Element e : elements) {
            list.add(parseSupMeasureBrief(e));
        }
        return list;
    }

    private static NoticeBrief parseSupMeasureBrief(Element e) {
        NoticeBrief sbmi = new NoticeBrief();
        String linkURL = e.select(BizConst.ElementKeyStr.TAG_A).first().attr(BizConst.ElementKeyStr.ATTR_HREF);
        String localDate = e.select(BizConst.ElementKeyStr.TAG_SPAN).first().html();
        String title = e.select(BizConst.ElementKeyStr.TAG_A).first().html();
        LocalDate ld = LocalDate.parse(localDate, DateTimeFormatter.ofPattern(CommonConst.DateFormat.DATE_FORMAT_NORMAL));
        sbmi.setContentUrl(linkURL);
        sbmi.setPublishDate(ld);
        sbmi.setTitle(title);
        return sbmi;
    }

    public static void main(String[] args) {

        List<String> strings = new ArrayList<>(2);

        String aa = "\n" + "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n" + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" + "    \n"
                + "<meta name=\"SiteName\" content=\"北京监管局\"/>\n"
                + "<meta name=\"SiteDomain\" content=\"www.csrc.gov.cn\"/>\n"
                + "<meta name=\"SiteIDCode\" content=\"zq56000003\"/>\n" + "<meta name=\"ColumnName\" content=\"监管措施\" />\n"
                + "<meta name=\"ColumnDescription\" content=\"监管措施\"/>\n"
                + "<meta name=\"ColumnKeywords\" content=\"监管措施\"/>\n" + "<meta name=\"ColumnType\" content=\"专题专栏\"/>\n"
                + "<meta name=\"ArticleTitle\" content=\"关于对首创证券股份有限公司采取责令改正监管措施的决定\"/>\n"
                + "<meta name=\"PubDate\" content=\"2022-04-07 15:39:34\"/>\n"
                + "<meta name=\"ContentSource\" content=\"\"/>\n" + "<meta name=\"Keywords\" content=\"\"/>\n"
                + "<meta name=\"Description\" content=\"〔2022〕64号首创证券股份有限公司:你公司于2021年5月18日发生集中交易系统部分中断,影响交易时间合计约20分\"/>\n"
                + "\n" + "<meta name=\"others\" content=\"页面生成时间 2022-04-07 15:39:34\" />\n"
                + "<meta name=\"template,templategroup,version\" content=\"3,default,2.4\" />\n"
                + "<title>关于对首创证券股份有限公司采取责令改正监管措施的决定_北京监管局</title>\n"
                + "    <link rel=\"stylesheet\" href=\"/beijing/xhtml/css/common.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"/beijing/xhtml/css/footer.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"/beijing/xhtml/css/common_detail.css\">\n"
                + "    <script src=\"/beijing/xhtml/js/jquery.min.js\"></script>\n" + "</head>\n" + "<body>\n"
                + "    <!--公共头-->\n"
                + "\t <link rel=\"stylesheet\" type=\"text/css\" href=\"/beijing/xhtml/css/common.css\" />\n" + "\n"
                + "<div class=\"header\">\n" + "    <div class=\"head-box\">\n" + "        <div class=\"tips\">\n"
                + "            <ul>\n" + "                <li>\n"
                + "                    <script src=\"/beijing/xhtml/js/trans.js\"></script>|\n" + "                </li>\n"
                + "                <li>\n"
                + "                    <img src=\"/beijing/xhtml/images/index/wza.png\" alt=\"\" class=\"logoimg\">\n"
                + "                    <a  id=\"cniil_wza\" href=\"/wza\">无障碍</a>\n" + "                </li>\n"
                + "                <div class=\"clear\"></div>\n" + "            </ul>\n"
                + "            <div class=\"clear\"></div>\n" + "        </div>\n" + "        <div class=\"logo\">\n"
                + "        \t\t <a href=\"/\"><img src=\"/beijing/xhtml/images/index/logo.png\" alt=\"\" class=\"logoimg1\"></a>\n"
                + "        \t\t <div class=\"logoxian\"></div>\n"
                + "        \t\t <h1><a href=\"/beijing/index.shtml\">北京监管局</a></h1>\n"
                + "            <div class=\"fr r-box\">\n" + "                <p  id=\"sc\">打造一个规范、透明、开放、有活力、有韧性的资本市场</p>\n"
                + "              \t<p id=\"zy\">建制度、不干预、零容忍</p>\n"
                + "              \t<p  id=\"hl\">敬畏市场、敬畏法治、敬畏专业、敬畏风险、发挥合力</p>\n"
                + "                <!--<div class=\"slideBox\">\n" + "\t\t\t\t\t<div class=\"hd\">\n"
                + "\t\t\t\t\t\t<ul></ul>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"bd\">\n" + "\t\t\t\t\t\t<ul>\n"
                + "\t\t\t\t\t\t\t<li>打造一个规范、透明、开放、有活力、有韧性的资本市场</li>\n" + "\t\t\t\t\t\t\t<li>建制度、不干预、零容忍</li>\n"
                + "\t\t\t\t\t\t\t<li>敬畏市场、敬畏法治、敬畏专业、敬畏风险、发挥合力</li>\n" + "\t\t\t\t\t<!--<li>忠诚、专业、进去、公开、公平、公正</li>\n"
                + "\t\t\t\t\t\t</ul>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t</div>-->\n"
                + "              <form action=\"/guestweb4/s\" method=\"post\" name=\"form1\" target=\"_blank\" id=\"form1\" class=\"search-group\" onsubmit=\"return check()\">\n"
                + "                    <input name=\"searchWord\" type=\"text\" id=\"searchWord\" size=\"50\" placeholder=\"请输入关键字\">\n"
                + "                    <input name=\"uc\" type=\"hidden\" id=\"uc\" value=\"1\">\n"
                + "                    <input name=\"siteCode\" type=\"hidden\" id=\"siteCode\" size=\"50\" value=\"zq56000003\">\n"
                + "                    <input name=\"column\" type=\"hidden\" id=\"siteCode\" size=\"50\" value=\"全部\">\n"
                + "                    <div class=\"searth-btn\">\n"
                + "                      <a href=\"#\"><img src=\"/beijing/xhtml/images/index/search.png\" alt=\"\"></a>\n"
                + "                      <input type=\"submit\" name=\"button\" id=\"button\" value=\"提交\" >\n"
                + "                  \t</div>\n" + "\n" + "\t\t\t\t</form>\n"
                + "                <div class=\"search-group\" style=\"display: none;\">\n"
                + "                    <input type=\"text\" placeholder=\"请输入关键字\">\n"
                + "                    <a href=\"#\">\n"
                + "                        <img src=\"/beijing/xhtml/images/index/search.png\" alt=\"\">\n"
                + "                    </a>\n" + "                    \n" + "                </div>\n"
                + "            </div>\n" + "            <div class=\"clear\"></div>\n" + "        </div>\n" + "    </div>\n"
                + "</div>\n" + " <script>\n" + "    function check(){\n" + "      if(!$('#searchWord').val()){\n"
                + "             alert('请输入关键字!');\n" + "             return false;\n" + "      }else{\n"
                + "      \t\treturn true;\n" + "      }\n" + "    }\n" + "  </script>\n" + "<div class=\"nav\">\n"
                + "    <ul class=\"nav-bar\">\n" + "        <li class=\"sy hover\">\n"
                + "            <a href=\"/beijing/index.shtml\">首页</a>\n" + "            <div></div>\n" + "        </li>\n"
                + "        <li class=\"zjjjs\">\n" + "            <a href=\"/beijing/c100272/tz.shtml\">证监局介绍</a>\n"
                + "        </li>\n" + "        <li class=\"xqjgdt\">\n"
                + "            <a href=\"/beijing/c100276/tz.shtml\">辖区监管动态</a>\n" + "        </li>\n"
                + "        <li class=\"zwxx\">\n" + "            <a href=\"/beijing/c103539/tz.shtml\">政务信息</a>\n"
                + "        </li>\n" + "        <li class=\"bsfw\">\n"
                + "            <a href=\"/beijing/c103567/zfxxgk_zdgk.shtml?channelid=786dd216a9bb4463b47418b8f962a2fd\">办事服务</a>\n"
                + "        </li>\n" + "        <li class=\"xqsj\">\n"
                + "            <a href=\"/beijing/c103569/zfxxgk_zdgk.shtml?channelid=740a6d381b594363921d485f699296e2\">辖区数据</a>\n"
                + "        </li>\n" + "        <li class=\"hdjl\">\n"
                + "            <a href=\"http://gzly.csrc12386.org.cn/csrc/sh.inb.publicmessage!messageList.sh\">互动交流</a>\n"
                + "        </li>\n" + "        <li class=\"ztzl\">\n"
                + "            <a href=\"/beijing/c100301/tz.shtml?channelid=e8024b62af8e4a908f68f8ad0a994e1e\">专题专栏</a>\n"
                + "        </li>\n" + "        <div class=\"clear\"></div>\n" + "    </ul>\n" + "</div>\n"
                + "<script src=\"/beijing/xhtml/js/jquery.SuperSlide.js\"></script>\n"
                + "<script src=\"/beijing/xhtml/js/head.js\"></script>\n" + "    <div class=\"BreadcrumbNav mt10\">\n"
                + "        <img src=\"/beijing/xhtml/images/public/location.png\" alt=\"\">\n" + "        当前位置：\n"
                + "<a href='/beijing/index.shtml'  target=\"_parent\">首页</a>><a href='/beijing/c100301/tz.shtml'  target=\"_parent\">专题专栏</a>><a href='/beijing/c105543/common_list_zt.shtml'  target=\"_parent\">信用专栏</a>><a href='/beijing/c105547/common_list.shtml'  target=\"_parent\"><span>监管措施</span></a>\n"
                + "\n" + "    </div>\n" + "    <div class=\"main\">\n" + "        <div class=\"content\">\n"
                + "            <h2>关于对首创证券股份有限公司采取责令改正监管措施的决定</h2>\n" + "            <div class=\"info\">\n"
                + "                <p class=\"fl\">日期：2022-04-07     来源：</p>\n"
                + "                <div class=\"share fr m-hide\" id=\"share\">\n"
                + "                    <div class=\"print-icon\"></div>\n"
                + "                    <div class=\"share-icon\"></div>\n"
                + "                    <div class=\"share-box\" id=\"share-box\">\n"
                + "                        <a class=\"share-btn gwds_weixin\" data-w=\"gwds_weixin\" title=\"微信\"> </a>\n"
                + "                        <a class=\"share-btn gwds_tsina\" data-w=\"gwds_tsina\" title=\"新浪微博\"> </a>\n"
                + "                        <span class=\"gwds_more\"> </span>\n" + "                    </div>\n"
                + "                    <div class=\"right share-popup bottom\" id=\"share-popup\" style=\"display: none;\">\n"
                + "                        <h6>分享到：<b></b></h6>\n" + "                        <ul>\n"
                + "                            <li><a data-w=\"gwds_qzone\" class=\"share-btn gwds_qzone\">QQ空间</a></li>\n"
                + "                            <li><a data-w=\"gwds_douban\" class=\"share-btn gwds_douban\">豆瓣网</a></li>\n"
                + "                        </ul>\n" + "                    </div>\n" + "                </div>\n"
                + "                <p class=\"fr m-hide changeSize\" id=\"changeSize\">\n" + "                \t【字号：\n"
                + "                \t<span class=\"\">大</span>\n" + "                  \t<span class=\"\">中</span>\n"
                + "               \t \t<span class=\"on\">小</span>】\n" + "                </p>\n"
                + "                <div class=\"clear\"></div>\n" + "            </div>\n"
                + "            <div class=\"repeatX\"></div>\n" + "            <div class=\"detail-news\">\n"
                + "                <p align=\"center\" style=\"line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">〔</span><span style=\"font-size: 15pt;\">2022〕64号</span></font></p>\n"
                + "<p align=\"center\" style=\"line-height: 1.5; text-indent: 2em;\"><br>\n" + "</p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p align=\"center\" style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">首创证券股份有限公司</span><span style=\"font-size: 15pt;\">:</span></font></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">你公司于</span><span style=\"font-size: 15pt;\">2021年5月18日发生集中交易系统部分中断,影响交易时间合计约20分钟,按照</span><span style=\"font-size: 15pt;\">《证券期货业信息安全事件报告与调查处理办法》</span><span style=\"font-size: 15pt;\">(证监会公告〔</span><span style=\"font-size: 15pt;\">2012〕46号)</span><span style=\"font-size: 15pt;\">第十二条规定,该事件达到较大信息安全事件标准。事后经我局调查,发现你公司信息技术部门有关人员在应急处置过程中删除相关日志及数据库信息,且未进行备份,</span><span style=\"font-size: 15pt;\">导致始终无法</span><span style=\"font-size: 15pt;\">确定本次信息安全事件的真实原因,公司在第一次报告中未如实报告应急处理不当的情况。</span></font></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">上述问题反映出你公司信息安全管理和信息安全事件应对方面存在缺陷,违反了《证券基金经营机构信息技术管理办法》(证监会令第</span><span style=\"font-size: 15pt;\">152号)第三十六条、第五十四条的相关规定。根据《证券期货业信息安全事件报告与调查处理办法》第二十九条和《证券基金经营机构信息技术管理办法》第五十七条的规定,我局决定对你公司采取责令整改的行政监管措施。你公司应对信息安全相关问题进行全面自查,切实提高系统运维保障能力和故障原因排查能力,完善信息安全应急处理工作机制,加强信息技术人员培训,确保其履职能力,杜绝类似问题再次发生。</span></font></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">如果对本监督管理措施不服,可在收到本决定书之日起</span><span style=\"font-size: 15pt;\">60日内向中国证券监督管理委员会提出行政复议申请,也可以在收到本决定书之日起6个月内向有管辖权的人民法院提起诉讼。复议与诉讼期间,上述监督管理措施不停止执行。</span></font></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"><br>\n" + "</p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"><br>\n" + "</p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p style=\"line-height: 1.5; text-indent: 2em;\"></p>\n"
                + "<p align=\"right\" style=\"line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">中国证监会北京监管局</span></font></p>\n"
                + "<p style=\"text-align: right; line-height: 1.5; text-indent: 2em;\"><font face=\"仿宋,仿宋_GB2312\"><span style=\"font-size: 15pt;\">2022</span><span style=\"font-size: 15pt;\">年</span><span style=\"font-size: 15pt;\">4</span><span style=\"font-size: 15pt;\">月</span><span style=\"font-size: 15pt;\">1</span><span style=\"font-size: 15pt;\">日</span><span style=\"font-size: 15pt;\">&ensp;</span><span style=\"font-size: 15pt;\">&ensp;</span><span style=\"font-size: 15pt;\">&ensp;</span></font></p>\n"
                + "              <div  id=\"files\" style=\"display: none\">\n" + "              </div>\n"
                + "            </div>\n" + "        </div>\n" + "    </div>\n" + "    <!DOCTYPE html>\n" + "<html>\n" + "\n"
                + "\t<head>\n" + "\t\t<meta charset=\"UTF-8\">\n"
                + "\t\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />\n"
                + "\t\t\n" + "<meta name=\"SiteName\" content=\"北京监管局\"/>\n"
                + "<meta name=\"SiteDomain\" content=\"www.csrc.gov.cn\"/>\n"
                + "<meta name=\"SiteIDCode\" content=\"zq56000003\"/>\n" + "<meta name=\"ColumnName\" content=\"公共尾\" />\n"
                + "<meta name=\"ColumnDescription\" content=\"公共尾\"/>\n"
                + "<meta name=\"ColumnKeywords\" content=\"公共尾\"/>\n" + "<meta name=\"ColumnType\" content=\"公共尾\"/>\n"
                + "\n" + "<meta name=\"others\" content=\"页面生成时间 2021-12-14 14:28:01\" />\n" + "<title>底部</title>\n"
                + "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/beijing/xhtml/css/footer.css\" />\n"
                + "\t</head>\n" + "\n" + "\t<body>\n" + "\t\t<div class=\"footpc\">\n" + "\t\t\t<div class=\"footcont\">\n"
                + "\t\t\t\t<div class=\"foot-head\">\n" + "\t\t\t\t\t<div class=\"footcont-left\">\n" + "\t\t\t\t\t\t<p>\n"
                + "\t\t\t\t\t\t\t<span>链接：</span>\n" + "\n"
                + "\t\t\t\t\t\t\t\t<a href=\"http://www.gov.cn/\"><img src=\"/beijing/xhtml/images/public/gh_f.png\" class=\"foot-icon\" />中国政府网</a> \n"
                + "\n" + "\n" + "\t\t\t\t\t\t</p>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"footcont-right\">\n"
                + "\t\t\t\t\t\t<select name=\"--相关机构--\" class=\"k105\" onchange=\"javascript:window.open(this.options[this.selectedIndex].value);this.selectedIndex=0\">\n"
                + "\t\t\t\t\t\t\t<option value=\"/csrc/c100225/common_list.shtml\">行业相关网站</option>\n" + "\n"
                + "\t\t\t\t\t\t\t\t <option value=\"http://www.investor.org.cn/\"><a href=\"http://www.investor.org.cn/\" target=\"_blank\"  >中国投资者网</a></option>\n"
                + "\n"
                + "\t\t\t\t\t\t\t\t <option value=\"http://www.csisc.cn/\"><a href=\"http://www.csisc.cn/\" target=\"_blank\"  >中国资本市场标准网</a></option>\n"
                + "\n" + "\t\t\t\t\t\t\t   \n" + "\t\t\t\t\t\t </select>\n"
                + "\t\t\t\t\t\t <select name=\"--其他连接--\" class=\"k105 qtlj\" onchange=\"MM_jumpMenu('parent',this,0)\">\n"
                + "\t\t\t\t\t\t   <option value=\"\">其他链接</option>\n" + "\t\t\t\t\t\t </select>\n" + "\t\t\t\t\t</div>\n"
                + "\t\t\t\t\t<div class=\"clear\"></div>\n" + "\t\t\t\t</div>\n" + "\t\t\t\t<div class=\"foot-bottom\">\n"
                + "\t\t\t\t\t<div class=\"footcont-left\">\n"
                + "\t\t\t\t\t\t<p>主办单位：中国证券监督管理委员会     版权所有：中国证券监督管理委员会 </p>\n"
                + "\t\t\t\t\t\t<p>网站识别码：bm56000001 <a href=\"https://beian.miit.gov.cn\">京ICP备 05035542号</a>\n"
                + "\t\t\t\t\t\t\t<a href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11040102700080\"  TARGET='_blank'><img src=\"/beijing/xhtml/images/public/footer_tu.png\" class=\"footer_xiao\" />京公网安备 11040102700080号</a>\n"
                + "\t\t\t\t\t\t</p>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"footcont-right\">\n"
                + "                      <script id=\"_jiucuo_\" sitecode='bm56000001' src='https://zfwzgl.www.gov.cn/exposure/jiucuo.js'></script>\n"
                + "\t\t\t\t\t\t<script type=\"text/javascript\">document.write(unescape(\"%3Cspan id='_ideConac' %3E%3C/span%3E%3Cscript src='https://dcs.conac.cn/js/33/000/0000/60407915/CA330000000604079150001.js' type='text/javascript'%3E%3C/script%3E\"));</script>\n"
                + "\t\t\t\t\t\t<p>\n" + "\n"
                + "\t\t\t\t\t\t\t  <a href=\"/beijing/c100275/c1257365/content.shtml\">联系我们</a> |\n" + "\n"
                + "\t\t\t\t\t\t\t  <a href=\"/csrc/c100227/c1362477/content.shtml\">法律声明</a> | \n"
                + "                             <a href=\"\">归档数据</a>\n" + "\t\t\t\t\t\t  </p>\n" + "\t\t\t\t\t</div>\n"
                + "\t\t\t\t\t<div class=\"clear\"></div>\n" + "\t\t\t\t</div>\n" + "\t\t\t</div>\n" + "\t\t</div>\n"
                + "\t\t<!---->\n" + "\t\t<div class=\"footsj\">\n" + "\t\t\t<p>主办单位：中国证券监督管理委员会</p>\n"
                + "\t\t\t<p>版权所有：中国证券监督管理委员会</p>\n" + "\t\t</div>\n" + "\t</body>\n"
                + "  <script language=\"JavaScript\">var _trackDataType = 'web';var _trackData = _trackData || [];</script>\n"
                + "<script type=\"text/javascript\" charset=\"utf-8\" id=\"kpyfx_js_id_10008998\" src=\"//fxsjcj.kaipuyun.cn/count/10008998/10008998.js\"></script>\n"
                + "<script  src=\"/beijing/xhtml/js/foot.js\"></script>\n" + "</html>\n" + "</body>\n"
                + "<script src=\"/beijing/xhtml/js/share.js\"></script>\n"
                + "<script src=\"/beijing/xhtml/js/wxShare.js\"></script>\n"
                + "<script src=\"/beijing/xhtml/js/commonDetail.js\"></script>\n" + "</html>";

        String bb = "\n" + "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n" + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n"
                + "  <meta name=\"gjId\" content=\"1921196\">\n" + "  <meta name=\"keywords\" content=\"\">\n"
                + " <meta name=\"description\" content=',2022-02-18' />\n"
                + "      <meta name=\"ContentSource\" content=\"\">\n" + "    \n"
                + "<meta name=\"SiteName\" content=\"中国证券监督管理委员会\"/>\n"
                + "<meta name=\"SiteDomain\" content=\"www.csrc.gov.cn\"/>\n"
                + "<meta name=\"SiteIDCode\" content=\"bm56000001\"/>\n" + "<meta name=\"ColumnName\" content=\"发行类\" />\n"
                + "<meta name=\"ColumnDescription\" content=\"发行类\"/>\n"
                + "<meta name=\"ColumnKeywords\" content=\"发行类\"/>\n" + "<meta name=\"ColumnType\" content=\"发行类\"/>\n"
                + "<meta name=\"ArticleTitle\" content=\"关于对中天国富证券有限公司及方蔚、赵亮采取出具警示函监管措施的决定\"/>\n"
                + "<meta name=\"PubDate\" content=\"2022-02-18 18:59:41\"/>\n"
                + "<meta name=\"ContentSource\" content=\"\"/>\n" + "<meta name=\"Keywords\" content=\"\"/>\n"
                + "<meta name=\"Description\" content=\"\"/>\n" + "\n"
                + "<meta name=\"others\" content=\"页面生成时间 2022-02-18 18:59:41\" />\n"
                + "<meta name=\"template,templategroup,version\" content=\"423,default,2.5\" />\n"
                + "<title>关于对中天国富证券有限公司及方蔚、赵亮采取出具警示函监管措施的决定_中国证券监督管理委员会</title>\n"
                + "    <link rel=\"stylesheet\" href=\"/csrc/xhtml/css/common.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"/csrc/xhtml/css/footer.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"/csrc/xhtml/css/xxgk_detail.css\">\n"
                + "    <script src=\"/csrc/xhtml/js/jquery.min.js\"></script>\n" + "</head>\n" + "<body>\n"
                + "  <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"/csrc/xhtml/css/common.css\" />-->\n"
                + "<div class=\"header\">\n" + "    <div class=\"head-box\">\n" + "        <div class=\"tips\">\n"
                + "            <ul>\n" + "                <li>\n"
                + "                  <script src=\"/csrc/xhtml/js/public.js\"></script>|\n" + "                </li>\n"
                + "                <li class=\"en\">\n"
                + "                    <a href=\"/csrc_en/index.shtml\">English</a>\n" + "                </li>\n"
                + "                <li class=\"ydd\">\n"
                + "\t\t\t\t\t<img src=\"/csrc/xhtml/images/index/yd.png\" alt=\"\">\n" + "\t\t\t\t\t<a>移动端</a>|\n"
                + "                   <div class=\"wb\">\n"
                + "                       <img src=\"/csrc/xhtml/images/index/andriod.png\">\n"
                + "                     <!--<img src=\"/csrc/xhtml/images/index/ios.png\">-->\n"
                + "                     <div class=\"clear\"></div>\n" + "                  </div>\n" + "\t\t\t\t</li>\n"
                + "\t\t\t\t<li class=\"djwb\">\n" + "\t\t\t\t\t<img src=\"/csrc/xhtml/images/index/wb.png\" alt=\"\">\n"
                + "\t\t\t\t\t<a href=\"https://weibo.com/csrcfabu\">微博</a>|\n" + "                   \n" + "\t\t\t\t</li>\n"
                + "\t\t\t\t<li  class=\"wx\">\n" + "\t\t\t\t\t<img src=\"/csrc/xhtml/images/index/wx.png\" alt=\"\">\n"
                + "\t\t\t\t\t<a>微信</a>|\n" + "                   <div class=\"wx1\">\n"
                + "                       <img src=\"/csrc/xhtml/images/index/wx.jpg\">\n" + "                  </div>\n"
                + "\t\t\t\t</li>\n" + "                <li class=\"wza\">\n"
                + "                    <img src=\"/csrc/xhtml/images/index/wza.png\" alt=\"\">\n"
                + "                    <a id=\"cniil_wza\" href=\"/wza\">无障碍</a>\n" + "                </li>\n"
                + "                <div class=\"clear\"></div>\n" + "            </ul>\n"
                + "            <div class=\"clear\"></div>\n" + "        </div>\n" + "        <div class=\"logo\">\n"
                + "            <a href=\"/\" ><img src=\"/csrc/xhtml/images/index/logo.png\" alt=\"\"></a>\n"
                + "            <div class=\"fr r-box\">\n" + "              \t<p  id=\"sc\">打造一个规范、透明、开放、有活力、有韧性的资本市场</p>\n"
                + "              \t<p id=\"zy\">建制度、不干预、零容忍</p>\n"
                + "              \t<p  id=\"hl\">敬畏市场、敬畏法治、敬畏专业、敬畏风险、发挥合力</p>\n" + "\t\t\t\t<!--<div class=\"slideBox\">\n"
                + "\t\t\t\t\t<div class=\"hd\">\n" + "\t\t\t\t\t\t<ul></ul>\n" + "\t\t\t\t\t</div>\n"
                + "\t\t\t\t\t<div class=\"bd\">\n" + "\t\t\t\t\t\t<ul>\n"
                + "\t\t\t\t\t\t\t<li>打造一个规范、透明、开放、有活力、有韧性的资本市场</li>\n" + "\t\t\t\t\t\t\t<li>建制度、不干预、零容忍</li>\n"
                + "\t\t\t\t\t\t\t<li>敬畏市场、敬畏法治、敬畏专业、敬畏风险、发挥合力</li>\n" + "\t\t\t\t\t\t\t<!--<li>忠诚、专业、进去、公开、公平、公正</li>\n"
                + "\t\t\t\t\t\t</ul>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t</div>-->\n"
                + "              \t<form action=\"/guestweb4/s\" method=\"post\" name=\"form1\" target=\"_blank\" id=\"form1\" class=\"search-group\" onsubmit=\"return check()\">\n"
                + "                    <input name=\"searchWord\" type=\"text\" id=\"searchWord\" size=\"50\"  placeholder=\"请输入关键字\">\n"
                + "                    <input name=\"uc\" type=\"hidden\" id=\"uc\" value=\"1\">\n"
                + "                    <input name=\"siteCode\" type=\"hidden\" id=\"siteCode\" size=\"50\" value=\"bm56000001\">\n"
                + "                    <input name=\"column\" type=\"hidden\" id=\"siteCode\" size=\"50\" value=\"全部\">\n"
                + "                    <div class=\"searth-btn\">\n"
                + "                      <a href=\"#\"><img src=\"/csrc/xhtml/images/index/search.png\" alt=\"\"></a>\n"
                + "                      <input type=\"submit\" name=\"button\" id=\"button\" value=\"提交\" >\n"
                + "                  \t</div>\n" + "\n" + "\t\t\t\t</form>\n"
                + "                <div class=\"search-group\" style=\"display: none;\">\n"
                + "                    <input type=\"text\" placeholder=\"请输入关键字\">\n"
                + "                    <a href=\"#\">\n"
                + "                        <img src=\"/csrc/xhtml/images/index/search.png\" alt=\"\">\n"
                + "                    </a>\n" + "                </div>\n" + "            </div>\n" + "        </div>\n"
                + "    </div>\n" + "</div>\n" + "<script>\n" + "    function check(){\n"
                + "      if(!$('#searchWord').val()){\n" + "             alert('请输入关键字!');\n"
                + "             return false;\n" + "      }else{\n" + "      \t\treturn true;\n" + "      }\n" + "    }\n"
                + "  </script>\n" + "<div class=\"nav\">\n" + "    <ul class=\"nav-bar\">\n" + "        <li class=\"sy\">\n"
                + "            <a href=\"/csrc/index.shtml\">首页</a>\n" + "            <div></div>\n" + "        </li>\n"
                + "        <li class=\"jggk\">\n" + "            <a href=\"/csrc/jggk/index.shtml\">机构概况</a>\n"
                + "        </li>\n" + "        <li class=\"xwfb\">\n"
                + "            <a href=\"/csrc/xwfb/index.shtml\">新闻发布</a>\n" + "        </li>\n"
                + "        <li class=\"zwxx\">\n" + "            <a href=\"/csrc/zwxx/index.shtml\">政务信息</a>\n"
                + "        </li>\n" + "        <li class=\"bsfw\">\n"
                + "            <a href=\"/csrc/bsfw/index.shtml\">办事服务</a>\n" + "        </li>\n"
                + "        <li class=\"hdjl\">\n" + "            <a href=\"/csrc/hdjl/index.shtml\">互动交流</a>\n"
                + "        </li>\n" + "        <li class=\"tjxx\">\n"
                + "            <a href=\"/csrc/tjsj/index.shtml\">统计信息</a>\n" + "        </li>\n"
                + "        <li class=\"ztzl\">\n" + "            <a href=\"/csrc/ztzl/index.shtml\">专题专栏</a>\n"
                + "        </li>\n" + "        <div class=\"clear\"></div>\n" + "    </ul>\n" + "</div>\n"
                + "<script src=\"/csrc/xhtml/js/jquery.SuperSlide.js\"></script>\n"
                + "<script src=\"/csrc/xhtml/js/head.js\"></script>\n" + "    <div class=\"BreadcrumbNav mt10\">\n"
                + "        <img src=\"/csrc/xhtml/images/public/location.png\" alt=\"\">\n" + "        当前位置：\n"
                + "<a href='/csrc/index.shtml'  target=\"_parent\">首页</a>&nbsp;>&nbsp;<a href='/csrc/zwxx/index.shtml'  target=\"_parent\">政务信息</a>&nbsp;>&nbsp;<a href='/csrc/c100032/tz.shtml'  target=\"_parent\">政府信息公开</a>&nbsp;>&nbsp;<a href='/csrc/c100035/zfxxgk_zdgk.shtml'  target=\"_parent\">主动公开目录</a>&nbsp;>&nbsp;<a href='/csrc/c101793/zfxxgk_zdgk.shtml'  target=\"_parent\">按主题查看</a>&nbsp;>&nbsp;<a href='/csrc/c101925/zfxxgk_zdgk.shtml'  target=\"_parent\">行政执法</a>&nbsp;>&nbsp;<a href='/csrc/c105937/zfxxgk_zdgk.shtml'  target=\"_parent\">行政监管措施</a>&nbsp;>&nbsp;<a href='/csrc/c106064/zfxxgk_zdgk.shtml'  target=\"_parent\"><span>发行类</span></a>\n"
                + "\n" + "    </div>\n" + "    <div class=\"main\">\n" + "        <div class=\"content\">\n"
                + "            <div class=\"xxgk-table\">\n" + "                <table>\n" + "                    <tbody>\n"
                + "                        <tr>\n" + "                            <th>索  引  号</th>\n"
                + "                            <td>bm56000001/2022-00001937</td>\n"
                + "                            <th>分        类</th>\n" + "                            <td></td>\n"
                + "                        </tr>\n" + "                        <tr>\n"
                + "                            <th>发布机构</th>\n" + "                            <td></td>\n"
                + "                            <th>发文日期</th>\n"
                + "                            <td><input class=\"fwrq\" type=\"hidden\" value='1645150682000'/><span id=\"fwrqBox\"></span></td>\n"
                + "                        </tr>\n" + "                        <tr>\n"
                + "                            <th>名        称</th>\n"
                + "                            <td colspan=\"3\">关于对中天国富证券有限公司及方蔚、赵亮采取出具警示函监管措施的决定</td>\n"
                + "                        </tr>\n" + "                        <tr>\n"
                + "                            <th>文        号</th>\n" + "                            <td></td>\n"
                + "                            <th>主  题  词</th>\n" + "                            <td></td>\n"
                + "                        </tr>\n" + "                    </tbody>\n" + "                </table>\n"
                + "            </div>\n" + "            <h2>关于对中天国富证券有限公司及方蔚、赵亮采取出具警示函监管措施的决定</h2>\n"
                + "            <div class=\"detail-news\">\n"
                + "                <p align=\"justify\" style=\"\"><span><font face=\"方正仿宋简体\">中天国富证券有限公司及方蔚、赵亮</font></span><span><font face=\"方正仿宋简体\">:</font></span><span><o:p></o:p></span></p>\n"
                + "<p align=\"justify\" style=\"text-indent: 2em;\"><span><font face=\"方正仿宋简体\">经查,我会发现</font></span><span><font face=\"方正仿宋简体\">你们</font></span><span><font face=\"方正仿宋简体\">在保荐</font></span><span><font face=\"方正仿宋简体\">浙江鑫甬生物化工股份有限公司</font></span><span><font face=\"方正仿宋简体\">(以下简称发行人)首次公开发行股票并上市过程中,</font></span><span><font face=\"方正仿宋简体\">未勤勉尽责</font></span><span><font face=\"方正仿宋简体\">督促发行人按照规定履行信息披露义务。</font></span><span><o:p></o:p></span></p>\n"
                + "<p align=\"justify\" style=\"text-indent: 2em;\"><span><font face=\"方正仿宋简体\">上述行为违反了《证券发行上市保荐业务管理办法》(证监会令第</font></span><span>170</span><span><font face=\"方正仿宋简体\">号)第</font></span><span><font face=\"方正仿宋简体\">五</font></span><span><font face=\"方正仿宋简体\">条规定。按照《证券发行上市保荐业务管理办法》第六十</font></span><span><font face=\"方正仿宋简体\">五</font></span><span><font face=\"方正仿宋简体\">条规定,</font></span><span><font face=\"方正仿宋简体\">我会决定</font></span><span><font face=\"方正仿宋简体\">对你们采取出具警示函的监督管理措施。</font></span><span><o:p></o:p></span></p>\n"
                + "<p align=\"justify\" style=\"text-indent: 2em;\"><span><font face=\"方正仿宋简体\">如果对本监督管理措施不服,可以在收到本决定书之日起</font>60<font face=\"方正仿宋简体\">日内向我会提出行政复议申请,也可以在收到本决定书之日起</font><font face=\"Times New Roman\">6</font><font face=\"方正仿宋简体\">个月内向有管辖权的人民法院提起诉讼。复议与诉讼期间,上述监督管理措施不停止执行。</font></span><span><o:p></o:p></span></p>\n"
                + "<p style=\"text-indent: 2em;\"><span><o:p></o:p></span></p>\n"
                + "<p style=\"text-indent: 2em;\"><span><o:p></o:p></span></p>\n"
                + "<p style=\"text-indent: 2em;\"><span><o:p></o:p></span></p>\n"
                + "<p style=\"text-indent: 2em;\"><span><o:p></o:p></span></p>\n"
                + "<p align=\"right\" style=\"text-indent: 2em;\"><span><font face=\"方正仿宋简体\">中国证监会</font></span><span><o:p></o:p></span></p>\n"
                + "<p align=\"right\" style=\"text-indent: 2em;\"><span>2022<font face=\"方正仿宋简体\">年</font><font face=\"Times New Roman\">1</font><font face=\"方正仿宋简体\">月</font><font face=\"Times New Roman\">28</font><font face=\"方正仿宋简体\">日</font></span><span><o:p></o:p></span></p>\n"
                + "              <div  id=\"files\" style=\"display: none\">\n" + "              </div>\n"
                + "              <div class=\"xxgk-down-box\">\n"
                + "                \t<a class=\"print\" href=\"javascript:window.print()\">\n"
                + "                \t\t<img src=\"/csrc/xhtml/images/zfxxgk/print.png\"/>\n"
                + "                      【打印】\n" + "                \t</a>\n"
                + "                \t<a class=\"down\" onclick=\"window.close()\">\n"
                + "                \t\t<img src=\"/csrc/xhtml/images/zfxxgk/download.png\"/>\n"
                + "                      【关闭窗口】\n" + "                \t</a>\n"
                + "                <div class=\"clear\"></div>\n" + "              </div>\n" + "            </div>\n"
                + "        </div>\n" + "    </div>\n"
                + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"/csrc/xhtml/css/footer.css\" />\n" + "\n"
                + "\t\t<div class=\"footpc\">\n" + "\t\t\t<div class=\"footcont\">\n"
                + "\t\t\t\t<div class=\"foot-head\">\n" + "\t\t\t\t\t<div class=\"footcont-left\">\n" + "\t\t\t\t\t\t<p>\n"
                + "\t\t\t\t\t\t\t<span>链接：</span>\n" + "\t\t\t\t\t\t\t\n"
                + "\t\t\t\t\t\t\t\t<a href=\"http://www.gov.cn/\"><img src=\"/csrc/xhtml/images/public/gh_f.png\" class=\"foot-icon\" />中国政府网</a> \n"
                + "\n" + "\n" + "\t\t\t\t\t\t</p>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"footcont-right\">\n"
                + "\t\t\t\t\t\t<select name=\"--相关机构--\" class=\"k105\" onchange=\"javascript:window.open(this.options[this.selectedIndex].value);this.selectedIndex=0\">\n"
                + "\t\t\t\t\t\t  <option value=\"/csrc/c100225/common_list.shtml\">行业相关网站</option>\n" + "\n"
                + "\t\t\t\t\t\t\t\t<option value=\"http://www.investor.org.cn/\"><a href=\"http://www.investor.org.cn/\" target=\"_blank\"  >中国投资者网</a></option>\n"
                + "\n"
                + "\t\t\t\t\t\t\t\t<option value=\"http://www.csisc.cn/\"><a href=\"http://www.csisc.cn/\" target=\"_blank\"  >中国资本市场标准网</a></option>\n"
                + "\n" + "\t\t\t\t\t\t</select>\n" + "\t\t\t\t\t\t<div class=\"zfndbb\">\n"
                + "\t\t\t\t\t\t\t<a href=\"/csrc/c105799/common_list.shtml\">\n"
                + "\t\t\t\t\t\t\t\t<img src=\"/csrc/xhtml/images/public/footer_zf.png\" />\n"
                + "\t\t\t\t\t\t\t\t<h1>政府网站年度报表</h1>\n" + "\t\t\t\t\t\t\t</a>\n" + "\t\t\t\t\t\t</div>\n"
                + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"clear\"></div>\n" + "\t\t\t\t</div>\n"
                + "\t\t\t\t<div class=\"foot-bottom\">\n" + "\t\t\t\t\t<div class=\"footcont-left\">\n"
                + "\t\t\t\t\t\t<p>主办单位：中国证券监督管理委员会      版权所有：中国证券监督管理委员会 </p>\n"
                + "\t\t\t\t\t\t<p>网站识别码：bm56000001 <a href=\"https://beian.miit.gov.cn\">京ICP备 05035542号</a>\n"
                + "\t\t\t\t\t\t\t<a href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11040102700080\" TARGET='_blank'><img src=\"/csrc/xhtml/images/public/footer_tu.png\" class=\"footer_xiao\" />京公网安备 11040102700080号</a>\n"
                + "\t\t\t\t\t\t</p>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"footcont-right\">\n"
                + "                      \t<script id=\"_jiucuo_\" sitecode='bm56000001' src='https://zfwzgl.www.gov.cn/exposure/jiucuo.js'></script>\n"
                + "\t\t\t\t\t\t<script type=\"text/javascript\">document.write(unescape(\"%3Cspan id='_ideConac' %3E%3C/span%3E%3Cscript src='https://dcs.conac.cn/js/33/000/0000/60407915/CA330000000604079150001.js' type='text/javascript'%3E%3C/script%3E\"));</script>\n"
                + "\t\t\t\t\t\t<!--<img src=\"/csrc/xhtml/images/public/footer_logo.png\" class=\"foot_logo\" />-->\n"
                + "\t\t\t\t\t\t<p>\n" + "\t\t\t\t\t\t\t<a href=\"/csrc/c100025/c1003151/content.shtml\">联系我们</a> |\n"
                + "\t\t\t\t\t\t\t<a href=\"/csrc/c100227/c1362477/content.shtml\">法律声明</a> |\n"
                + "                          \t<a href=\"/csrc/c105974/common_list.shtml\">归档数据</a>\n"
                + "\t\t\t\t\t\t</p>\n" + "\t\t\t\t\t</div>\n" + "\t\t\t\t\t<div class=\"clear\"></div>\n"
                + "\t\t\t\t</div>\n" + "\t\t\t</div>\n" + "\t\t</div>\n" + "\t\t<!---->\n" + "\t\t<div class=\"footsj\">\n"
                + "\t\t\t<p>主办单位：中国证券监督管理委员会</p>\n" + "\t\t\t<p>版权所有：中国证券监督管理委员会</p>\n" + "\t\t</div>\n"
                + "\t<script language=\"JavaScript\">var _trackDataType = 'web';var _trackData = _trackData || [];</script>\n"
                + "<script type=\"text/javascript\" charset=\"utf-8\" id=\"kpyfx_js_id_10008998\" src=\"//fxsjcj.kaipuyun.cn/count/10008998/10008998.js\"></script>\n"
                + "\t<script src='/csrc/xhtml/js/foot.js'></script>\n" + "</body>\n"
                + "  <script src=\"/csrc/xhtml/js/xxgkDetail.js\"></script>\n" + "</html>";

        Document mm = JsoupUtils.getDocFromStr(bb);

        NoticeBrief nb = new NoticeBrief();
        nb.setContentUrl("");
        nb.setTitle("");
        nb.setPublishDate(LocalDate.now());
        nb.setTaskId("");
        nb.setDataType(0);
        nb.setBranchCategory("");
        nb.setBranchId("11");
        nb.setId(0);
        //NoticeDetails nd = getNoticeDetailsFromDoc(mm, nb);

        Elements kk = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_BJ);
        List<Element> lists = new ArrayList<>(2);

        Elements titleEles = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TITLE_ZJH);

        Elements objectEles = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_ZJH);

        Elements contents = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT_ZJH);

        String kkk = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_OBJECT_BJ).first().html();

        //Element object = mm.select(BizConst.ElementKeyStr.LOC_NOTICE_DETAILS_TEXT).first();

        //        Elements kkk = mm.select("ul#list.list.mt10 > li");
        //
        //        Element kk2 = mm.select("div *.main-right").first();
        //        Element kk2 = mm.select("script:containsData(createPageHTML)").first();
        //        String ml4 = mm.select("script:containsData(createPageHTML)").first().data();
        //        boolean aaa = Pattern.matches(BizConst.ElementKeyStr.PAGE_INFO_CHECK_PATTERN, ml4);
    }

}
