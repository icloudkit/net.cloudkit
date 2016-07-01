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

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsView;

/**
 * JasperReportsPrintView.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月13日 上午8:57:30
 */
public class JasperReportsPrintView extends AbstractJasperReportsView {

	@Override
	protected void renderReport(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");

		OutputStream os = response.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(populatedReport);

		os.close();
		out.close();
	}
}
