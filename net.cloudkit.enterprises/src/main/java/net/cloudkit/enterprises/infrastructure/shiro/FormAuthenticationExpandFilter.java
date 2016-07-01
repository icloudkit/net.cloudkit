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
package net.cloudkit.enterprises.infrastructure.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.cloudkit.enterprises.infrastructure.utilities.ClientUtility;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * CaptchaFormAuthenticationFilter FormAuthenticationExpandFilter.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:55:45
 */
public class FormAuthenticationExpandFilter extends FormAuthenticationFilter {

    public static final String LOGIN_SESSION_KEY = "LOGIN_REDIS_SESSION";
    public static final String CAPTCHA_SESSION_KEY = "CAPTCHA_SESSION";

    private static Logger logger = LoggerFactory.getLogger(FormAuthenticationExpandFilter.class);

    // public static final String DEFAULT_EMAIL_PARAM = "email";
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 请求基础认证数据
     * -----------------------------------------------------------------------------------------------------------------
     */
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    // /**
    //  * --------------------------------------------------------------------------------------------------------------
    //  * 其它数据
    //  * --------------------------------------------------------------------------------------------------------------
    //  */
    // private static final String AUTH_SERVER_REST_URL = "authServerRestUrl";
    // private static final String SYSTEM_ID = "SystemId";

    // TODO 服务处理逻辑
    // @Resource
    // private AuthenticationService authenticationService;

    /**
     * 创建 Token
     */
    protected UsernamePasswordExpandToken createToken(ServletRequest request, ServletResponse response) {

        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        String captcha = getCaptcha(request);

        // TODO 其它数据
        Map<String, Object> otherDataMap = new HashMap<String, Object>();
        // otherDataMap.put(REST_URL, restUrl);
        // otherDataMap.put(AUTH_SERVER_REST_URL, "");
        // otherDataMap.put(SYSTEM_ID, "workbench");

        return new UsernamePasswordExpandToken(username, password.toCharArray(), rememberMe, host, captcha, otherDataMap);
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param token
     */
    protected void doCaptchaValidate(HttpServletRequest request, UsernamePasswordExpandToken token) {

        // 验证码校验
        // String captcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String captcha = (String) request.getSession().getAttribute(CAPTCHA_SESSION_KEY);
        logger.debug("captcha session value:" + captcha);
        if (captcha == null || captcha == "" || !captcha.equalsIgnoreCase(token.getCaptcha())) {
            logger.info("验证码错误!");
            throw new IncorrectCaptchaException("验证码错误!");
        }
        logger.info("验证码校验通过!");
    }

    /**
     * 认证登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 退出登录
        // SecurityUtils.getSubject().logout();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        UsernamePasswordExpandToken token = createToken(httpServletRequest, httpServletResponse);


        String loginName = getUsername(request);
        String ip = ClientUtility.getRemoteAddr(request);
        String userAgent  = httpServletRequest.getHeader("User-Agent");

        // LoginLogDTO loginLog = null;
        try {
            // 判断5分钟这内登录错误次数, 超过三次开启验证码验证.
            // countLoginLogByLoginNameAndLoginTime(getEmail(request), DateTimeUtility.getDateTimeAfterOfBeforeMinute(-5))
            // int loginFailedTimes = memberService.getLoginFailedTimes(ClientUtility.getRemoteAddr(request), 5);
            // if (loginFailedTimes >= 3) {
            //     // 验证码校验
            //     doCaptchaValidate((HttpServletRequest) request, token);
            // }

            Subject subject = getSubject(request, response);
            subject.login(token);

            // FIXME 已经在 onLoginSuccess 中处理
            // Subject currentUser = SecurityUtils.getSubject();
            // Session session = subject.getSession();
            // ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
            // logger.info("################### subject.getPrincipal():" + subject.getPrincipal());
            // 记录到 Session 缓存服务器 session.getId(); session.getHost();
            // session.setAttribute(LOGIN_SESSION_KEY, shiroUser.getLoginName());

            // 把用户名放入在线列表
            // session.setAttribute("onlineUserBindingListener", new OnlineUserBindingListener(shiroUser.getLoginName()));

            // TODO 将日志记录移到 onLoginSuccess 与 onLoginFailure 中处理
            // 记录登录日志
            // loginLog = new LoginLogDTO(loginName, ip, userAgent, new Date().getTime(), 1);

            // HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            // Session session = httpServletRequest.getSession();
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            // 记录登录日志
            // loginLog = new LoginLogDTO(loginName, ip, userAgent, new Date().getTime(), 0);

            logger.error("登录失败" + e.getMessage(), e);
            return onLoginFailure(token, e, request, response);
        } finally {
            // FIXME 记录登录成功/失败日志
            // memberService.writingLoginLog(loginLog);
        }

    }

    // public boolean isSessionStorageEnabled(Subject subject) {
    //     boolean enabled = false;
    //     if (WebUtils.isWeb(subject)) {
    //         HttpServletRequest request = WebUtils.getHttpRequest(subject);
    //         //set 'enabled' based on the current request.
    //     } else {
    //         //not a web request - maybe a RMI or daemon invocation?
    //         //set 'enabled' another way...
    //     }
    //
    //     return enabled;
    // }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    /**
     * @return the captchaParam
     */
    public String getCaptchaParam() {
        return captchaParam;
    }

    /**
     * @param captchaParam the captchaParam to set
     */
    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    /*
     *	主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 设置用户身份进session属性
        // LoginUserDTO loginUserDTO = passportService.getLoginUserByLoginName(((ShiroDbRealm.ShiroUser) subject.getPrincipal()).getName());
        // subject.getSession().setAttribute(LOGIN_SESSION_KEY, loginUserDTO);
        // subject.getSession().setAttribute(SubjectUtil.LOGIN_SESSION_KEY, subject.getPrincipal());

        // logger.info("loginUserDTO: " + objectMapper.writeValueAsString(loginUserDTO));

        // 不是ajax请求
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            issueSuccessRedirect(request, response);
        } else {
            // 将实体对象转换为JSON Object转换
            // JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
            //
            // Jackson:
            // ObjectMapper objectMapper = new ObjectMapper();
            // objectMapper.writeValueAsString(obj);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            // out.println("{\"success\":true, \"message\":\"" + URLEncoder.encode("登入成功", "UTF-8") + "\"}");
            // out.println("{\"success\":true, \"message\":\"登录成功\", \"data\":" + objectMapper.writeValueAsString(loginUserDTO) + "}");
            out.println("{\"success\":true, \"message\":\"登入成功\"}");
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        // 不是ajax请求
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
            setFailureAttribute(request, e);
            return true;
        }
        try {

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            String result = "";
            String error = e.getClass().getSimpleName();
            // String error = (String) request.getAttribute(FormAuthenticationExpandFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
            if ("UnknownAccountException".equals(error)) {
                result = "您输入的用户名或密码不正确，请重新输入。";
            } else if ("NoFoundAccountException".equals(error)) {
                result = "您输入的用户名或密码不正确，请重新输入。";
            } else if ("IncorrectCredentialsException".equals(error)) {
                result = "您输入的用户名或密码不正确，请重新输入。";
            } else if ("ExcessiveAttemptsException".equals(error)) {
                result = "用户名或密码错误次数过多。";
            } else if ("DisabledAccountException".equals(error)) {
                result = "用户已被禁用。";
            } else if ("LockedAccountException".equals(error)) {
                result = "操作过于频繁，当前用户已锁定。";
            } else if ("IncorrectCaptchaException".equals(error)) {
                result = "登录平台失败，验证码错误。";
            } else {
                result = "登录平台失败，请重试。";
            }
            out.println("{\"success\":false, \"message\":\"" + result + "\"}");
            out.flush();
            out.close();
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
        return false;
    }

    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                }
                // 不是 Ajax 请求
                // if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                //     setFailureAttribute(request, e);
                //     return true;
                // }

                // Ajax 请求
                if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {

                    /*
                    // 方法二: 验证码效验 doCaptchaValidate
                    String captcha = request.getParameter("captcha");
                    HttpServletRequest httpservletrequest = (HttpServletRequest) request;
                    // String sessionCaptcha = (String) httpservletrequest.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                    String sessionCaptcha = (String) httpservletrequest.getSession().getAttribute(LOGIN_SESSION_KEY);
                    if (sessionCaptcha == null || "".equals(sessionCaptcha) || !sessionCaptcha.equals(captcha)) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("{success:false, message:'验证码错误'}");
                        out.flush();
                        out.close();
                        return false;
                    }
                    */
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login page view.");
                }
                // allow them to see the login page ;)
                return true;
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl() + "]");
            }
            if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                // 不是ajax请求
                saveRequestAndRedirectToLogin(request, response);
            } else {
                HttpServletResponse httpResponse = (HttpServletResponse) response;

                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setContentType("application/json; charset=UTF-8");
                // SC_MOVED_TEMPORARILY
                // httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                PrintWriter out = response.getWriter();
                out.println("{\"success\":false, \"message\":\"登录后空闲时间过长，请重新登录！\"}");
                out.flush();
                out.close();
            }
            return false;
        }
    }

    /**
     * 以JSON格式输出
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {

        // 将对象转换为 JSON Object 转换
        // JSONObject responseJSONObject = JSONObject.fromObject(responseObject);

        // Jackson 对象转换为 JSON Object 转换
        ObjectMapper objectMapper = new ObjectMapper();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            // out.append(responseJSONObject.toString());
            // logger.debug(responseJSONObject.toString());

            out.append(objectMapper.writeValueAsString(responseObject));

        } catch (IOException e) {
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
