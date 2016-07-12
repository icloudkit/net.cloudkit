/*
 * Copyright (C) 2015 The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cloudkit.enterprises.infrastructure.jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.view.jasperreports.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JasperReportsMultiFormatPlusView.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月13日 上午8:57:30
 */
public class JasperReportsMultiItemFormatView extends JasperReportsMultiFormatView {

    private static final Logger logger = LoggerFactory.getLogger(JasperReportsMultiItemFormatView.class);

    public JasperReportsMultiItemFormatView() {
        super();
        // Map<String, Class<? extends AbstractJasperReportsView>> formatMappings = new HashMap<String, Class<? extends AbstractJasperReportsView>>(6);
        // formatMappings.put("print", JasperReportsPrintView.class);
        // super.setFormatMappings(formatMappings);
    }

    /**
     * 格式不为html时的下载文件名
     */
    public static final String ATTACHEMT_FILE_NAME_KEY = "attachmentFileName";

    /**
     * jasperViews reportViews
     */
    // public static final String REPORT_VIEWS_NAME_KEY = "reportViews";
    public static final String JASPER_VIEWS_NAME_KEY = "jasperViews";

    public static final String JASPER_VIEW_DATAS_NAME_KEY = "jasperViewDatas";

    @Override
    protected void renderReport(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response) throws Exception {
        Object format = model.get(DEFAULT_FORMAT_KEY);
        if (format == null) {
            throw new IllegalArgumentException("model中未找到指定的输出格式(format:html、pdf、xls、csv)");
        }
        if (!format.equals("html")) {
            Object attachmentFileName = model.get(ATTACHEMT_FILE_NAME_KEY);
            if (attachmentFileName == null) {
                throw new IllegalArgumentException("model中未指定输出文件名(attachmentFileName)");
            }
            Properties contentDispositionMappings = getContentDispositionMappings();
            response.setCharacterEncoding("UTF-8");

            // MIME TYP
            // image/bmp                     BMP
            // image/gif                     GIF
            // image/jpeg                    JPEG
            // image/tiff                    TIFF
            // image/x-dcx                   DCX
            // image/x-pcx                   PCX
            // text/html                     HTML
            // text/plain                    TXT
            // text/xml                      XML
            // application/afp               AFP
            // application/pdf               PDF
            // application/rtf               RTF
            // application/msword            MSWORD
            // application/vnd.ms-excel      MSEXCEL
            // application/vnd.ms-powerpoint MSPOWERPOINT
            // application/wordperfect5.1    WORDPERFECT
            // application/vnd.lotus-wordpro WORDPRO
            // application/vnd.visio         VISIO
            // application/vnd.framemaker    FRAMEMAKER
            // application/vnd.lotus-1-2-3   LOTUS123
            // response.setContentType("application/pdf; charset=utf-8");

            // contentDispositionMappings.put(format.toString(), "attachment; filename=" + URLEncoder.encode(String.valueOf(attachmentFileName), "UTF-8") + "." + format);
            contentDispositionMappings.put(format.toString(), "attachment; filename=" + attachmentFileName + "." + format);
        }
        super.renderReport(populatedReport, model, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void postProcessReport(JasperPrint populatedReport, Map<String, Object> model) throws Exception {

        if (model.containsKey(JasperReportsMultiItemFormatView.JASPER_VIEWS_NAME_KEY) && model.containsKey(JasperReportsMultiItemFormatView.JASPER_VIEW_DATAS_NAME_KEY)) {

            // 报表混合视图 jasperViews
            String[] jasperViews = (String[]) model.get(JasperReportsMultiItemFormatView.JASPER_VIEWS_NAME_KEY);

            // System.out.println(jasperViews[0] + " " + jasperViews[1]);

            List<JasperReport> reports = new ArrayList<JasperReport>();
            for (int i = 0; i < jasperViews.length; i++) {

                String viewName = jasperViews[i];
                if (viewName == null || "".equals(viewName)) {
                    continue;
                }
                String path = this.getUrl();
                /*
                String fileName = path.substring(path.lastIndexOf("/"));
                String suffix=fileName.substring(fileName.indexOf("."));

                String regex = "[^/]+\\.[^/]+";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(path);
                path = matcher.replaceAll(viewName + suffix);
                */

                // /WEB-INF/reports/report1-report.jasper
                String regex = "(?<=/)(?>[0-9a-zA-Z\\-]+)(?=\\.)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(path);

                path = matcher.replaceAll(viewName);

                // TODO 从URL获取模板
                // Resource resource = new UrlResource("http://www.example.com/main.html");
                // TODO 从InputStream获取模板
                // Resource resource = new InputStreamResource();
                // 从本地获取模板
                Resource resource = getApplicationContext().getResource(path);
                reports.add(loadReport(resource));
            }

            /*
            // 删除多余 Page
            List reportPages = populatedReport.getPages();
            for (int i = reportPages.size() - 1; i >= 0; i--) {
                populatedReport.removePage(i);
            }
            */

            List<Map<String, Object>> jasperViewDatas = (List<Map<String, Object>>) model.get(JasperReportsMultiItemFormatView.JASPER_VIEW_DATAS_NAME_KEY);
            for (Map<String, Object> dataModel : jasperViewDatas) {
                for (Iterator it = reports.iterator(); it.hasNext(); ) {
                    JasperReport jReport = (JasperReport) it.next();
                    JRDataSource jrDataSource = getReportData(dataModel);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, dataModel, jrDataSource);

                    List pages = jasperPrint.getPages();
                    for (Iterator pageIt = pages.iterator(); pageIt.hasNext(); ) {
                        JRPrintPage printPage = (JRPrintPage) pageIt.next();
                        populatedReport.addPage(printPage);
                    }
                }
            }
        }

        // html视图
        if (model.containsKey(JasperReportsMultiItemFormatView.DEFAULT_FORMAT_KEY)) {
            if ("html".equalsIgnoreCase(model.get(JasperReportsMultiItemFormatView.DEFAULT_FORMAT_KEY).toString())) {
                if (model.containsKey("requestObject")) {
                    HttpServletRequest request = (HttpServletRequest) model.get("requestObject");
                    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, populatedReport);
                }
            }
        }
    }
}
