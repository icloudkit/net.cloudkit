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

/**
 * ReportResource.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月13日 上午8:57:15
 */
public class ReportResource {

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
