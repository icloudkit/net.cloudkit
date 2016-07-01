/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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
package net.cloudkit.flowportal.infrastructure.commons;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * EncodingConvertFilter.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月13日 上午11:38:34
 */
public class EncodingConvertFilter implements Filter {

    private static final String FROM_ENCODING = "fromEncoding";
    private static final String TO_ENCODING = "toEncoding";
    private String fromEncoding = "ISO-8859-1";
    private String toEncoding = "UTF-8";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (servletRequest.getMethod().equalsIgnoreCase("GET")) {
            Iterator<?> it = servletRequest.getParameterMap().values().iterator();
            while (it.hasNext()) {
                String[] arrayOfString = (String[]) it.next();
                for (int i = 0; i < arrayOfString.length; ++i)
                    try {
                        arrayOfString[i] = new String(arrayOfString[i].getBytes(this.fromEncoding), this.toEncoding);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
            }
        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String fromEncoding = filterConfig.getInitParameter(FROM_ENCODING);
        String toEncoding = filterConfig.getInitParameter(TO_ENCODING);
        if (fromEncoding != null) {
            this.fromEncoding = fromEncoding;
        }
        if (toEncoding == null) {
            return;
        }
        this.toEncoding = toEncoding;

    }

}
