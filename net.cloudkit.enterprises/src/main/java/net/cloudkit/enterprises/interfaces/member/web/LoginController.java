package net.cloudkit.enterprises.interfaces.member.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户登录 login
     *
     * @param request
     * @param username
     * @param password
     * @param model
     * @return
     */
    /*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpSession session, @RequestParam(FormAuthenticationExpandFilter.DEFAULT_USERNAME_PARAM) String username, @RequestParam(FormAuthenticationExpandFilter.DEFAULT_PASSWORD_PARAM) String password, Model model, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        // if(session.getAttribute(FormAuthenticationExpandFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME) != null) {
        //     mv.setViewName(InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/workbench");
        //     return mv;
        // }

        // System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
        // request.setAttribute("message_login", "未知账户");
        // // 未知账户
        // UnknownAccountException
        //
        // System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
        // request.setAttribute("message_login", "密码不正确");
        // // 密码不正确
        // IncorrectCredentialsException
        //
        // System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
        // request.setAttribute("message_login", "账户已锁定");
        // // 账户已锁定
        // LockedAccountException
        //
        // System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
        // request.setAttribute("message_login", "用户名或密码错误次数过多");
        // // 用户名或密码错误次数过多
        // ExcessiveAttemptsException

        String result = "";
        String error = (String) request.getAttribute(FormAuthenticationExpandFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (error != null) {

            if (error.contains("UnknownAccountException")) {
                result = "您输入的账户名和密码不匹配，请重新输入.";
            } else if (error.contains("NoFoundAccountException")) {
                result = "您输入的账户名不存在,请核对后重新输入.";
            } else if (error.contains("IncorrectCredentialsException")) {
                result = "密码不正确.";
            } else if (error.contains("ExcessiveAttemptsException")) {
                result = "用户名或密码错误次数过多.";
            } else if (error.contains("DisabledAccountException")) {
                result = "用户已被禁用,请登录其他用户.";
            } else if (error.contains("LockedAccountException")) {
                result = "账户已锁定.";
            } else if (error.contains("IncorrectCaptchaException")) {
                result = "登录失败验证码错误.";
            } else {
                result = "登录失败，请重试.";
            }
        }

        // result.rejectValue("username", "LoginFailed", message);

        // Object obj = request.getAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        // AuthenticationException authExp = (AuthenticationException) obj;
        // if (authExp != null) {
        //     String message = "";
        //
        //     if (authExp instanceof UnknownAccountException || authExp instanceof IncorrectCredentialsException) {
        //         message = "错误的用户账号或密码！";
        //     } else if (authExp instanceof IncorrectCaptchaException) {
        //         message = "验证码错误！";
        //     } else {
        //         message = "登录异常 :" + authExp.getMessage();
        //     }
        // }

        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute(FormAuthenticationExpandFilter.DEFAULT_USERNAME_PARAM, username);

        mv.setViewName(InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login");
        return mv;
    }
    */


    /**
     * 用户登录 login
     *
     * @param request
     * @return
     */
    /*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {

        String viewName = InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login";
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 获取HttpSession中的验证码
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");

        // 获取用户请求表单中输入的验证码
        String submitCode = WebUtils.getCleanParam(request, "verifyCode");
        //System.out.println("用户[" + username + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为[" + verifyCode + "]");
        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())) {
            request.setAttribute("message_login", "验证码不正确");
            return viewName;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        System.out.println("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            //System.out.println("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            //System.out.println("对用户[" + username + "]进行登录验证..验证通过");
            viewName = "/workbench";
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            request.setAttribute("message_login", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            request.setAttribute("message_login", "密码不正确");
        } catch (DisabledAccountException lae) {
            // LockedAccountException
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            request.setAttribute("message_login", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            request.setAttribute("message_login", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            request.setAttribute("message_login", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            //System.out.println("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        } else {
            token.clear();
        }
        return viewName;
    }
    */

    /**
     * 获取当前登录用户登录失败次数。
     *
     * @param loginName
     * @param model
     * @return
     */
    @RequestMapping(value = {"/get_login_failed_count"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Model loginFailedCount(@RequestParam(value = "login_name", required = true) String loginName, Model model) {

        model.addAttribute("success", false);
        try {

            // TODO 查询登录失败次数，IP在5分钟之内登录失败是否超过三次。
            // getLoginFailedCount
            model.addAttribute("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("message", "查询错误！");
        }
        return model;
    }



//    Subject currentUser = SecurityUtils.getSubject();
//
//    if (!currentUser.isAuthenticated()) {
//        return "false";
//    }

//    Subject currentUser = SecurityUtils.getSubject();
//    if (!currentUser.isAuthenticated()) {
//        model = login(request, session, username,password, model);
//    }else{
//        // 重复登录
//        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) currentUser.getPrincipal();
//        // 如果登录名不同
//        if(!shiroUser.getLoginName().equalsIgnoreCase(username)){
//            currentUser.logout();
//            model = login(request, session, username,password, model);
//        }
//    }
}
