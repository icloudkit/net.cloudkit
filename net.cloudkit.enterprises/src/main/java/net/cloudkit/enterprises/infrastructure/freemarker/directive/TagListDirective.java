package net.cloudkit.enterprises.infrastructure.freemarker.directive;///*
// * Copyright (C) 2015 The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package net.cloudkit.workbench.infrastructure.freemarker.directive;
//
//import java.beans.PropertyDescriptor;
//import java.io.IOException;
//import java.utils.ArrayList;
//import java.utils.List;
//import java.utils.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import com.ticeng.lattice.domain.model.common.Tag;
//import com.ticeng.lattice.infrastructure.sql.Filter;
//import com.ticeng.lattice.infrastructure.sql.Order;
//import com.ticeng.lattice.infrastructure.utilities.FreemarkerHelper;
//
//import freemarker.core.Environment;
//import freemarker.template.TemplateDirectiveBody;
//import freemarker.template.TemplateDirectiveModel;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateModel;
//
///**
// *
// * TagListDirective.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2014年1月8日 下午2:47:00
// */
//@Component("tagListDirective")
//public class TagListDirective implements TemplateDirectiveModel {
//
//    private static final String VARIABLE_NAME = "tags";
//
//    @Resource(name = "tagServiceImpl")
//    private TagService tagService;
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @Override
//    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
//        // TODO Auto-generated method stub
//        Boolean useCache = FreemarkerHelper.getParameter("useCache", Boolean.class, params);
//        useCache = useCache != null ? useCache.booleanValue() : true;
//
//        String cacheRegion = FreemarkerHelper.getParameter("cacheRegion", String.class, params);
//        cacheRegion = cacheRegion != null ? cacheRegion : env.getTemplate().getName();
//
//        Long id = FreemarkerHelper.getParameter("id", Long.class, params);
//
//        List<Filter> filterList = new ArrayList<Filter>();
//        PropertyDescriptor[] arrayOfPropertyDescriptor1 = PropertyUtils.getPropertyDescriptors(Tag.class);
//        for (PropertyDescriptor propertyDescriptor : arrayOfPropertyDescriptor1) {
//            String property = propertyDescriptor.getName();
//            Class<?> clazz = propertyDescriptor.getPropertyType();
//            // String... paramVarArgs
//            if ((!ArrayUtils.contains(new String[0], property)) && (params.containsKey(property))) {
//                Object obj = FreemarkerHelper.getParameter(property, clazz, params);
//                filterList.add(Filter.eq(property, obj));
//            }
//        }
//
//        String str1 = StringUtils.trim((String) FreemarkerHelper.getParameter("orderBy", String.class, params));
//        List<Order> orderByList = new ArrayList<Order>();
//        if (StringUtils.isNotEmpty(str1)) {
//            String[] arrayOfString1 = str1.split("\\s*,\\s*");
//            for (String str2 : arrayOfString1) {
//                if (StringUtils.isNotEmpty(str2)) {
//                    String property = null;
//                    Order.Direction direction = null;
//                    String[] arrayOfString = str2.split("\\s+");
//                    if (arrayOfString.length == 1) {
//                        property = arrayOfString[0];
//                    } else {
//                        if (arrayOfString.length < 2) {
//                            continue;
//                        }
//                        property = arrayOfString[0];
//                        try {
//                            direction = Order.Direction.valueOf(arrayOfString[1]);
//                        } catch (IllegalArgumentException localIllegalArgumentException) {
//                            continue;
//                        }
//                    }
//                    if (!ArrayUtils.contains(new String[0], property)) {
//                        orderByList.add(new Order(property, direction));
//                    }
//                }
//            }
//        }
//
//        List<Tag> tagList;
//        if (useCache) {
//            tagList = tagService.findList(id, filterList, orderByList, cacheRegion);
//        } else {
//            tagList = tagService.findList(id, filterList, orderByList);
//        }
//
//        TemplateModel templateModel = FreemarkerHelper.getVariable(env, VARIABLE_NAME);
//        FreemarkerHelper.setVariable(env, VARIABLE_NAME, tagList);
//        body.render(env.getOut());
//        FreemarkerHelper.setVariable(env, VARIABLE_NAME, templateModel);
//    }
//
//}
