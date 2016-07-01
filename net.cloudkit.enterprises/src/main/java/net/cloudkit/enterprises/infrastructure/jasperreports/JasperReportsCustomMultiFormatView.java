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

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * DigDataJasperReportsView.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月13日 上午8:57:30
 */
public class JasperReportsCustomMultiFormatView extends JasperReportsMultiFormatView {

    /** 格式不为html时的下载文件名 */
    public static final String ATTACHEMT_FILE_NAME_KEY = "attachmentFileName";

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
            contentDispositionMappings.put(format.toString(), "attachment; filename=" + attachmentFileName + "." + format);
        }
        super.renderReport(populatedReport, model, response);
    }
}
