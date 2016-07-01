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
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * FreeMarker用户自定义指令来重复模板中的一部分，可选的是使用hr来分隔输出内容中的重复部分。
 * 指令内容
 * 参数:
 * count: 重复的次数。必须！ 必须是一个非负的数字，如果它不是一个整数，那么小数部分就会被舍去。
 * hr: 用来辨别HTML的 "hr"元素是否在重复内容之 间被打印出来。布尔值。 可选， 默认是false。
 * 循环变量: 1, 可选的。它给定了当前重复内容的数量，从1开始。
 * 嵌套内容: 是
 *
 * 在Freemarker模版文件中这样使用：
 * root.put("repeat", new RepeatDirective());
 *
 * 调用
 * <@repeat count=4>
 *  Test ${x}
 *  <#assign x = x + 1>
 * </@repeat>
 * 输出
 * Test 1
 * Test 2
 * Test 3
 * Test 4
 * </pre>
 */
@Component("repeatDirective")
public class RepeatDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_COUNT = "count";
    private static final String PARAM_NAME_HR = "hr";

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        // ---------------------------------------------------------------------
        // Processing the parameters:

        int countParam = 0;
        boolean countParamSet = false;
        boolean hrParam = false;
        Iterator<?> paramIter = params.entrySet().iterator();
        while (paramIter.hasNext()) {
            Map.Entry ent = (Map.Entry) paramIter.next();
            String paramName = (String) ent.getKey();
            TemplateModel paramValue = (TemplateModel) ent.getValue();
            if (paramName.equals(PARAM_NAME_COUNT)) {
                if (!(paramValue instanceof TemplateNumberModel)) {
                    throw new TemplateModelException("The \"" + PARAM_NAME_HR + "\" parameter " + "must be a number.");
                }
                countParam = ((TemplateNumberModel) paramValue).getAsNumber().intValue();
                countParamSet = true;
                if (countParam < 0) {
                    throw new TemplateModelException("The \"" + PARAM_NAME_HR + "\" parameter " + "can't be negative.");
                }
            } else if (paramName.equals(PARAM_NAME_HR)) {
                if (!(paramValue instanceof TemplateBooleanModel)) {
                    throw new TemplateModelException("The \"" + PARAM_NAME_HR + "\" parameter " + "must be a boolean.");
                }
                hrParam = ((TemplateBooleanModel) paramValue).getAsBoolean();
            } else {
                throw new TemplateModelException("Unsupported parameter: " + paramName);
            }
        }
        if (!countParamSet) {
            throw new TemplateModelException("The required \"" + PARAM_NAME_COUNT + "\" paramter" + "is missing.");
        }
        if (loopVars.length > 1) {
            throw new TemplateModelException("At most one loop variable is allowed.");
        }

        // ---------------------------------------------------------------------
        // Do the actual directive execution:
        Writer out = env.getOut();
        if (body != null) {
            for (int i = 0; i < countParam; i++) {
                // 如果"hr"参数为真，那么就在所有重复部分之间打印<hr>:
                // Prints a <hr> between all repetations if the "hr" parameter was true:
                if (hrParam && i != 0) {
                    out.write("<hr>");
                }
                // 如果有循环变量，那么就设置它:
                // Set the loop variable, if there is one:
                if (loopVars.length > 0) {
                    loopVars[0] = new SimpleNumber(i + 1);
                }
                // 执行嵌入体部分（和FTL中的<#nested>一样）。
                // 这种情况下，我们不提供一个特殊的writer作为参数:
                // Executes the nested body (same as <#nested> in FTL). In this
                // case we don't provide a special writer as the parameter:
                body.render(env.getOut());
            }
        }
    }
}
