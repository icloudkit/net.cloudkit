/*
 * Copyright (C) 2015 The Workbench Open Source Project
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
package net.cloudkit.enterprises.interfaces.reports;

import net.cloudkit.enterprises.infrastructure.jasperreports.JasperReportsMultiItemFormatView;
import net.cloudkit.enterprises.infrastructure.utilities.ReflectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表 ReportController.java
 * http://localhost:8080/enterprises/report_view/?jasperViews=report1-report&jasperViews=report1-report&format=pdf&attachmentFileName=DEMO%E6%8A%A5%E8%A1%A8
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月13日 上午9:18:13
 */
@Controller
// @RequestMapping(value = "/")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * 报表页面(通过内嵌iframe,调用show_report显示报表、下载pdf、xls、csv等)<br>
     * 请求链接必须带有reportName参数，用以指定模板名
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/reports")
    public ModelAndView report(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("report");
        // 将相关参数传递至show_report.html
        mv.addObject("params", request.getQueryString());
        return mv;
    }

    @javax.annotation.Resource
    private Properties configProperties;
    /**
     * 显示html、下载pdf、xls、cvs等报表<br>
     * 请求链接必须带有reportName参数，用以指定模板名<br>
     * 请求链接必须带有format参数，用以指定生成的格式(html、pdf、xls、csv)<br>
     * SQL查询等参数可添加至ReportEntity属性
     *
     * @param reportResource 报表相关参数
     * @return
     */
    @RequestMapping(value = "/report_view")
    public ModelAndView reportView(ReportResource reportResource) {

        try {
            System.out.println(configProperties.get("test").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        Date now = new Date();

        // 要调用的jasperreports的模板文件名(不包括后缀)，该文件名必须以-report结尾
        mv.setViewName(reportResource.getJasperViews()[0]);

        // 显示格式：html、pdf、xls、csv
        mv.addObject(JasperReportsMultiItemFormatView.DEFAULT_FORMAT_KEY, reportResource.getFormat());

        String attachmentFileName = reportResource.getAttachmentFileName();
        // 当为pdf、xls、csv时的附件名
        if (attachmentFileName != null && !attachmentFileName.equals("")) {
            mv.addObject(JasperReportsMultiItemFormatView.ATTACHEMT_FILE_NAME_KEY, attachmentFileName);
        } else {
            mv.addObject(JasperReportsMultiItemFormatView.ATTACHEMT_FILE_NAME_KEY, SimpleDateFormat.getDateTimeInstance().format(new Date()));
        }

        List<Map<String, Object>> datas = new ArrayList<>();

        Map<String, Object> data1 = new HashMap<>();
        List<Map<String, Object>> dataList1 = new ArrayList<>();
        // Resource resource1 = new Resource();
        // resource1.setNickname("Coffee");
        Map<String, Object> fieldMap1 = new HashMap<>();
        fieldMap1.put("username", "Tom");
        fieldMap1.put("dataList", new ArrayList<>());
        fieldMap1.put("dataMap", new HashMap<String, String>());
        dataList1.add(fieldMap1);

        data1.put("username", "Mango");
        List<String> dataList = new ArrayList<>();
        dataList.add("Coffee");
        data1.put("dataList", dataList);
        Map<String, String> paramMap1 = new HashMap<>();
        paramMap1.put("username", "Apple");
        data1.put("dataMap", paramMap1);
        data1.put("dataSource", dataList1);
        datas.add(data1);

        mv.addObject(JasperReportsMultiItemFormatView.JASPER_VIEW_DATAS_NAME_KEY, datas);

        Map<String, Object> params = convertParams(reportResource);
        mv.addAllObjects(params);

        // JRDataSource datasource = new JRBeanCollectionDataSource(dataList1);
        // mv.addObject("datasource", datasource);

        return mv;
    }

    /**
     * 将请求参数转换为map类型
     *
     * @param reportResource
     * @return
     */
    private Map<String, Object> convertParams(ReportResource reportResource) {
        Map<String, Object> params = new HashMap<String, Object>();
        Field[] fields = ReportResource.class.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (!name.equals(JasperReportsMultiItemFormatView.ATTACHEMT_FILE_NAME_KEY) && !name.equals(JasperReportsMultiItemFormatView.DEFAULT_FORMAT_KEY)) {
                try {
                    params.put(name, ReflectHelper.getValueByFieldName(reportResource, name));
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    class ReportResource {

        private String attachmentFileName;

        /** viewNames reportViews 要调用的jasperreports的模板文件名(不包括后缀)，该文件名必须以-report结尾 */
        private String[] jasperViews;

        /** 显示格式：html、pdf、xls、csv */
        private String format;

        /**
         * @return the attachmentFileName
         */
        public String getAttachmentFileName() {
            return attachmentFileName;
        }

        /**
         * @param attachmentFileName the attachmentFileName to set
         */
        public void setAttachmentFileName(String attachmentFileName) {
            this.attachmentFileName = attachmentFileName;
        }

        /**
         * @return the jasperViews
         */
        public String[] getJasperViews() {
            return jasperViews;
        }

        /**
         * @param jasperViews the jasperViews to set
         */
        public void setJasperViews(String[] jasperViews) {
            this.jasperViews = jasperViews;
        }

        /**
         * @return the format
         */
        public String getFormat() {
            return format;
        }

        /**
         * @param format the format to set
         */
        public void setFormat(String format) {
            this.format = format;
        }

    }
}

//// JavaBeanPerson
//class Resource {
//
//    private String nickname;
//    private List dataList = new ArrayList();
//    private Map dataMap = new HashMap();
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public List getDataList() {
//        return dataList;
//    }
//
//    public void setDataList(List dataList) {
//        this.dataList = dataList;
//    }
//
//    public Map getDataMap() {
//        return dataMap;
//    }
//
//    public void setDataMap(Map dataMap) {
//        this.dataMap = dataMap;
//    }
//}
