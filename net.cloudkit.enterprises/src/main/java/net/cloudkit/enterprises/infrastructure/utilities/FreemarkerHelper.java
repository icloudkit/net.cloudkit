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
package net.cloudkit.enterprises.infrastructure.utilities;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static org.springframework.web.servlet.view.AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE;

/**
 * FreemarkerHelper.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 上午10:47:29
 */
public class FreemarkerHelper {

    private static Logger logger = LoggerFactory.getLogger(FreemarkerHelper.class);

    public static final String[] DATE_PATTERNS = { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

    private static final ConvertUtilsBean convertUtilsBean;

    // // 加载配置
    // private static Configuration cfg = null;
    //
    // private static final String templates = "templates";
    //
    // public static void initConfiguration() {
    //     URL url = FreemarkerHelper.class.getClassLoader().getResource(templates);
    //     initConfiguration(url.getPath());
    // }
    //
    // public static void initConfiguration(String templatePath) {
    //     try {
    //         if (cfg == null)
    //             cfg = new Configuration();
    //         cfg.setDirectoryForTemplateLoading(new File(templatePath));
    //         cfg.setDefaultEncoding("UTF-8");
    //         cfg.setOutputEncoding("UTF-8");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         logger.error(e.getMessage());
    //     }
    // }

    static {
        DateConverter localDateConverter = new DateConverter();
        localDateConverter.setPatterns(DATE_PATTERNS);
        convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.register(localDateConverter, Date.class);
    }

    public static String process(String templateName, Map<String, ?> model, String target) {
        String result = null;

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        Writer writer = null;
        File file = new File(target);
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.mkdir();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            // new OutputStreamWriter(new FileOutputStream(file), "UTF-8")
            writer = new BufferedWriter(outputStreamWriter);
            result = process(templateName, model, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(fileOutputStream);

            // try {
            //     writer.close();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
        }
        return result;
    }

    /**
     * Process Template
     *
     * @param templateName
     * @param model
     * @param writer
     * @return
     */
    public static String process(String templateName, Map<String, ?> model, Writer writer) {

        /*
        // Where you initialize the cfg *singleton* (happens just once in the application life-cycle):
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        ...
        cfg.setObjectWrapper(new MyAppObjectWrapper(cfg.getIncompatibleImprovements()));
        */

        Configuration cfg = null;
        ApplicationContext localApplicationContext = SpringHelper.getApplicationContext();
        if (localApplicationContext != null) {
            FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) SpringHelper.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
            if (freeMarkerConfigurer != null) {
                cfg = freeMarkerConfigurer.getConfiguration();
            }
        }
        try {
            // cfg.setDirectoryForTemplateLoading(new File(filePath));
            cfg.setObjectWrapper(new BeansWrapper(Configuration.VERSION_2_3_22));
            Template template = cfg.getTemplate(templateName);
            // template.setEncoding("UTF-8");
            template.process(model, writer);
            // new Template("template", new StringReader(template), cfg).process(model, writer);
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return writer.toString();
    }

    // public static String process(String template, Map<String, ?> model, Configuration cfg) {
    //     if (template == null) {
    //         return null;
    //     }
    //     if (cfg == null) {
    //         cfg = new Configuration();
    //     }
    //     StringWriter stringWriter = new StringWriter();
    //     try {
    //         new Template("template", new StringReader(template), cfg).process(model, stringWriter);
    //     } catch (TemplateException localTemplateException) {
    //         localTemplateException.printStackTrace();
    //     } catch (IOException localIOException) {
    //         localIOException.printStackTrace();
    //     }
    //     return stringWriter.toString();
    // }

    @SuppressWarnings("unchecked")
    public static <T> T getParameter(String name, Class<T> type, Map<String, TemplateModel> params) throws TemplateModelException {
        TemplateModel localTemplateModel = params.get(name);
        if (localTemplateModel == null) {
            return null;
        }
        Object localObject = DeepUnwrap.unwrap(localTemplateModel);
        return (T) convertUtilsBean.convert(localObject, type);
    }

    public static TemplateModel getVariable(Environment env, String name) throws TemplateModelException {
        return env.getVariable(name);
    }

    // @SuppressWarnings("unchecked")
    // public static <T> T getVariable(String name, Map<String, TemplateModel> params, T result) throws TemplateException {
    //     TemplateModel model = params.get(name);
    //     if (model == null) {
    //         return null;
    //     }
    //     if (model instanceof TemplateDateModel) {
    //         ((TemplateDateModel) model).getAsDate();
    //     } else if (model instanceof TemplateBooleanModel) {
    //         ((TemplateBooleanModel) model).getAsBoolean();
    //     }
    //
    //     if (model instanceof TemplateScalarModel) {
    //         String temp = ((TemplateScalarModel) model).getAsString();
    //         if (result instanceof String) {
    //             result = (T) temp;
    //         } else if (result instanceof Boolean) {
    //             result = (T) Boolean.valueOf(temp.equalsIgnoreCase("true") == true? true:false);
    //         } else if(result instanceof Integer){
    //             result = (T) Integer.valueOf(temp);
    //         } else if(result instanceof Long){
    //             result = (T) Long.valueOf(temp);
    //         }
    //     } else if ((model instanceof TemplateNumberModel)) {
    //         if (result instanceof String) {
    //             result = (T) ((TemplateNumberModel) model).getAsNumber().toString();
    //         }if(result instanceof Integer){
    //             result = (T) Integer.valueOf(((TemplateNumberModel) model).getAsNumber().intValue());
    //         } else if(result instanceof Long){
    //             result = (T) Long.valueOf(((TemplateNumberModel) model).getAsNumber().longValue());
    //         }
    ////         else if (result instanceof Boolean) {
    ////             result = (T) Boolean.valueOf(temp == 1? true:false);
    ////         }
    //     }
    //
    //
    //     if (StringUtils.isBlank(str)) {
    //         return null;
    //     }
    //     String[] arr = StringUtils.split(str, ',');
    //     Integer[] ids = new Integer[arr.length];
    //     for (String s : arr) {
    //         ids[i++] = Integer.valueOf(s);
    //     }
    //
    //     return result;
    // }

    public static void setVariable(Environment env, String name, Object value) throws TemplateModelException {
        if ((value instanceof TemplateModel)) {
            env.setVariable(name, (TemplateModel) value);
        } else {
            env.setVariable(name, new BeansWrapper(Configuration.VERSION_2_3_22).wrap(value));
        }
    }

    public static void setVariables(Environment env, Map<String, Object> variables) throws TemplateModelException {
        Iterator<Entry<String, Object>> localIterator = variables.entrySet().iterator();
        while (localIterator.hasNext()) {
            Entry<String, Object> localEntry = localIterator.next();
            String str = (String) localEntry.getKey();
            Object localObject = localEntry.getValue();

            if ((localObject instanceof TemplateModel)) {
                env.setVariable(str, (TemplateModel) localObject);
            } else {
                env.setVariable(str, new BeansWrapper(Configuration.VERSION_2_3_22).wrap(localObject));
            }
        }
    }

    /**
     * 获得RequestContext.ViewResolver中的exposeSpringMacroHelpers必须为true
     *
     * @param env
     * @return
     * @throws TemplateException
     */
    public static RequestContext getContext(Environment env) throws TemplateException {
        TemplateModel ctx = env.getGlobalVariable(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);
        if (ctx instanceof AdapterTemplateModel) {
            return (RequestContext) ((AdapterTemplateModel) ctx).getAdaptedObject(RequestContext.class);
        } else {
            throw new TemplateModelException("RequestContext '" + SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE + "' not found in DataModel.");
        }
    }

}
