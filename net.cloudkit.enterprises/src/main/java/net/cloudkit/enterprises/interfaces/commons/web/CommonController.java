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
package net.cloudkit.enterprises.interfaces.commons.web;

import net.cloudkit.enterprises.infrastructure.utilities.CaptchaImageUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

/**
 * CommonController.java
 * Default Bootstrap
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月13日 上午11:38:34
 */
@Controller
public class CommonController implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    public static final String SESSION_CAPTCHA_NAME_KEY = "SESSION_CAPTCHA";

    /**
     * HTTP 404 Resource not found
     *
     * @param request
     * @return
     */
    @RequestMapping({"/resource_not_found"})
    public String resourceNotFound(HttpServletRequest request) {

        /*
        String webAppRoot = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
        System.setProperty("webapp.root", webAppRoot);

        File tpl = new File(webAppRoot + File.separator + "default.ftl");
        if (!tpl.exists()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", "123456");
            FreemarkerHelper.process("default.ftl", map, webAppRoot + File.separator + "default.ftl");
        }
        */

        return "/resource_not_found";
    }

    /**
     * Error
     *
     * @return
     */
    @RequestMapping({"/error"})
    public String error() {
        return "/error";
    }

    /**
     * Site disabled
     *
     * @return
     */
    @RequestMapping({"/disabled"})
    public String isDisabled() {
        // localSetting.getIsSiteEnabled().booleanValue()
        boolean isSiteEnabled = true;
        if (isSiteEnabled) {
            // return "redirect:/";
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
        }
        return "disabled";
    }

    /**
     * Unauthorized
     *
     * @return
     */
    @RequestMapping({"/unauthorized"})
    public String unauthorized() {
        return "/unauthorized";
    }

    /**
     * URL Redirect
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/redirect")
    public ModelAndView redirect(String url) {

        ModelAndView mv = new ModelAndView();
        // 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        // mv.setViewName("forward:selected");
        if (url != null) {
            mv.setViewName("redirect:" + url);
        } else {
            mv.setViewName("redirect:/");
            mv.setViewName(InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/");
        }
        return mv;
    }

    /**
     * ie6
     *
     * @return
     */
    @RequestMapping({"/ie6"})
    public String ie6() {
        return "/ie6";
    }

    /**
     * 获取验证码图片和文本 verify_code_image
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/captcha_image")
    public void captchaImages(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try {

            String verifyCode = CaptchaImageUtility.generateTextCode(CaptchaImageUtility.TYPE_NUM_ONLY, 4, null);
            // 将验证码放到HttpSession里面
            request.getSession().setAttribute(CommonController.SESSION_CAPTCHA_NAME_KEY, verifyCode);
            System.out.println("生成的验证码为[" + verifyCode + "]");
            // 设置输出的内容的类型为JPEG图像
            response.setContentType("image/jpeg");
            BufferedImage bufferedImage = CaptchaImageUtility.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
            // 写给浏览器
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleException(Exception e) {
        Response response = new Response();
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodException = (MethodArgumentNotValidException)e;
            BindingResult bindingResult =methodException.getBindingResult();
            if(bindingResult!=null && bindingResult.hasErrors()){
                List<FieldError> fieldErrorList= bindingResult.getFieldErrors();
                for(FieldError fieldError: fieldErrorList ){
                    response.adderror(fieldError.getDefaultMessage());
                }
            }
        }
        return response;
    }
    */

    /**
     * Mobile
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/mobile"}, method = RequestMethod.GET)
    public String mobile(HttpServletRequest request, Model model) {
        return "/mobile";
    }

    /**
     * Downloads
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/downloads"}, method = RequestMethod.GET)
    public String downloads(HttpServletRequest request, Model model) {
        return "/downloads";
    }

    /**
     * Download
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/download"}, method = RequestMethod.GET)
    public void download(@RequestParam(name = "id", required = true) String id, HttpServletRequest request, HttpServletResponse response, Model model) {

        if (null != id) {
            return;
        }

        // TODO 根据ID查找文件
        File downloadFile = new File("");
        if (!downloadFile.exists()) {
            return;
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(downloadFile));
            bos = new BufferedOutputStream(response.getOutputStream());

            // 中文文件名支持
            String encodedFileName = null;
            String agent = request.getHeader("USER-AGENT");
            // IE
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                encodedFileName = java.net.URLEncoder.encode(downloadFile.getName(), "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                encodedFileName = new String(downloadFile.getName().getBytes("UTF-8"), "ISO-8859-1");
            } else {
                encodedFileName = URLEncoder.encode(downloadFile.getName(), "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, byteRead);
            }
            bos.flush();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            // IOUtils.closeQuietly(bis);
            // IOUtils.closeQuietly(bos);
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    ;
                }
            }
            if(bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
    }

    @RequestMapping(value = "/download_file", params = "method=file")
    public void download(HttpServletRequest request, HttpServletResponse response) {

        // 设置下载文件名字
        String realName = "test.txt";
        // 获取完整的文件名
        String fileName = request.getParameter("fileName");

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            long fileLength = new File(fileName).length();
            String ctxPath = request.getSession().getServletContext().getRealPath("/");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(realName.getBytes("UTF-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(fileName));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // IOUtils.closeQuietly(bis);
            // IOUtils.closeQuietly(bos);
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    ;
                }
            }
            if(bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Model upload(@ModelAttribute(value = "file") CommonsMultipartFile file, HttpServletRequest request, BindingResult result, Model model) {

        model.addAttribute("success", "false");

        /*
        // 判断文件是否为空,空直接返回上传错误
        if(!file.isEmpty()){
            // TODO
        }
        // 获取本地文件存储目录
        String path = request.getServletContext().getRealPath("/fileupload/");
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 获得文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 拼接文件路径,以UUID生成文件名
        String filePath = path + File.separator + UUID.randomUUID().toString()+ suffix;
        // 创建一个要保存的新文件
        File saveFile = new File(filePath);
        // 将文件写入到新文件中
        file.getFileItem().write(saveFile);
        */

        if (!result.hasErrors()) {
            FileOutputStream outputStream = null;
            String filePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            try {
                outputStream = new FileOutputStream(new File(filePath));
                outputStream.write(file.getFileItem().get());
                outputStream.close();
            } catch (Exception e) {
                // System.out.println("Error while saving file");
                model.addAttribute("message", "Error while saving file");
                return model;
            }
            model.addAttribute("success", "true");
            return model;
        } else {
            model.addAttribute("message", "Error while saving file");
            return model;
        }
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response
            , Object object, Exception exception) {

        Map<String, Object> model = new HashMap<>();
        if (exception instanceof MaxUploadSizeExceededException) {
            model.put("errors", "File size should be less then " + ((MaxUploadSizeExceededException) exception).getMaxUploadSize() + " byte.");
        } else {
            model.put("errors", "Unexpected error: " + exception.getMessage());
        }
        // model.put("FORM", new UploadForm());
        return new ModelAndView("/upload", model);
    }

    /**
     * test
     *
     * @param name
     * @param offset
     * @param limit
     * @param url
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public Model test(@RequestParam("name") String name, @RequestParam int offset, @RequestParam int limit, @RequestBody String url, Model model) {

        model.addAttribute("success", false);
        try {
            List<String> data = new ArrayList<String>();
            data.add("123456");
            data.add("456789");

            model.addAttribute("data", data);
            model.addAttribute("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return model;
    }

    /*
    // @RestController
    // @RequestMapping(value="/api/mail")
    @RequestMapping(produces = "application/json" ,method=RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<HttpStatus> sendMail(@RequestBody MailRequest mailRequest){

        System.out.println("MailRequest 2 mailRequest!!!!!!!!!!!!!!!!!!! " );

        //  dispatcher.SendMail(new MailRequestEvent(mailRequest));
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    */

    // @RequestMapping("/valid/{username}")
    // @PathVariable String username
    // @RequestHeader("Accept-Encoding") String encoding,
    // @RequestHeader("Keep-Alive") long keepAlive
    // @CookieValue("JSESSIONID") String cookie
    // @RequestParam("username") String username
    // @RequestBody String body 用来处理Content-Type: 不是application/x-www-form-urlencoded编码的内容，例如application/json, application/xml等;
    // @SessionAttributes("user") 用来绑定HttpSession中的attribute对象的值，便于在方法中的参数里使用。
    // @ModelAttribute 用于方法上时:通常用来在处理@RequestMapping之前，为请求绑定需要从后台查询的model；用于参数上时:用来通过名称对应，把相应名称的值绑定到注解的参数bean上；

    // 验证分组 First @Validated({First.class})
    @RequestMapping(value = "/valid", produces = "application/json")
    @ResponseBody
    public Model valid(HttpServletRequest request, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        RequestContext requestContext = new RequestContext(request);
        model.addAttribute("success", false);
        try {

            // ResourceBundle myResources = ResourceBundle.getBundle("messages", Locale.getDefault());
            // System.out.println(myResources.getString("Pattern.user.username"));

            if (result.hasErrors()) {
                List<FieldError> errs = result.getFieldErrors();
                Map<String, String> mapErrors = new LinkedHashMap<String, String>();
                for (FieldError fieldError : errs) {
                    // fieldError.getDefaultMessage()
                    // System.out.println("fieldError:" + fieldError.getCodes()[0]);
                    /*
                    System.out.println("fieldError:" + fieldError.getCode() + " " + fieldError.getDefaultMessage());
                    for(String code : fieldError.getCodes()) {
                        System.out.println("code:" + code);
                    }
                    */
                    mapErrors.put(fieldError.getField(), requestContext.getMessage(fieldError.getCodes()[0]));
                }
                model.addAttribute("success", false);
                model.addAttribute("errors", mapErrors);
                // model.addAttribute("error", requestContext.getMessage(result.getFieldErrors().get(0).getCodes()[0]));
                return model;
            }

            List<String> data = new ArrayList<String>();
            data.add("123456");
            data.add("456789");

            model.addAttribute("data", data);
            model.addAttribute("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("message", "error!");
        }
        return model;
    }


    // isAvailable
    // isUsernameAvailability
    // isEmailAvailability
    // isMobileAvailability

    // supplementary
}
