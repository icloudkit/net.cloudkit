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
package net.cloudkit.enterprises.infrastructure.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.cloudkit.enterprises.infrastructure.commons.ExecuteTimeInterceptor;
import net.cloudkit.enterprises.infrastructure.utilities.FreemarkerHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.util.Map;

/**
 * ExecuteTimeDirective.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月8日 下午12:00:17
 */
@Component("executeTimeDirective")
public class ExecuteTimeDirective implements TemplateDirectiveModel {

    private static final String VARIABLE_NAME = "execute_time";

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        // TODO Auto-generated method stub
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            Long executeTime = (Long) requestAttributes.getAttribute(ExecuteTimeInterceptor.EXECUTE_TIME_ATTRIBUTE_NAME, 0);
            if (executeTime != null) {
                TemplateModel templateModel = FreemarkerHelper.getVariable(env, VARIABLE_NAME);
                // env.setVariable(NAME, ObjectWrapper.DEFAULT_WRAPPER.wrap(localLong));
                FreemarkerHelper.setVariable(env, VARIABLE_NAME, executeTime);
                body.render(env.getOut());
                FreemarkerHelper.setVariable(env, VARIABLE_NAME, templateModel);
            }
        }
    }

}
