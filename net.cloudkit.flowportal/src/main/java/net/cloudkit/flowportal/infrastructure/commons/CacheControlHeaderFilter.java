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

import net.cloudkit.flowportal.infrastructure.utilities.ServletHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CacheControlHeaderFilter.java
 * 为Response设置客户端缓存控制Header的Filter.
 * <p/>
 * eg.在web.xml中设置
 * <p/>
 * <pre>
 *  <filter>
 *      <filter-name>cacheControlHeaderFilter</filter-name>
 *      <filter-class>net.cloudkit.ecosystem.infrastructure.commons.CacheControlHeaderFilter</filter-class>
 *      <init-param>
 *          <param-name>expiresSeconds</param-name>
 *          <param-value>31536000</param-value>
 *      </init-param>
 *  </filter>
 *  <filter-mapping>
 *      <filter-name>cacheControlHeaderFilter</filter-name>
 *      <url-pattern>/images/*</url-pattern>
 *  </filter-mapping>
 * </pre>
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月13日 上午11:38:34
 */
public class CacheControlHeaderFilter implements Filter {

    private static final String PARAM_EXPIRES_SECONDS = "expiresSeconds";
    private long expiresSeconds;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ServletHelper.setExpiresHeader((HttpServletResponse) res, expiresSeconds);
        chain.doFilter(req, res);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) {
        String expiresSecondsParam = filterConfig.getInitParameter(PARAM_EXPIRES_SECONDS);
        if (expiresSecondsParam != null) {
            expiresSeconds = Long.valueOf(expiresSecondsParam);
        } else {
            expiresSeconds = ServletHelper.ONE_YEAR_SECONDS;
        }
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }
}
