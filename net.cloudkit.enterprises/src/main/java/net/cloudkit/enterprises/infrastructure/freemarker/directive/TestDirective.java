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
import net.cloudkit.enterprises.infrastructure.utilities.FreemarkerHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component("testDirective")
public class TestDirective implements TemplateDirectiveModel {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Writer out = env.getOut();// 固定写法
        // 获取标签中，参数名为'param'对应的值
        Long typeId = FreemarkerHelper.getParameter("param", Long.class, params);
        // 获取标签中，参数名为'var'对应的值
        String varName = FreemarkerHelper.getParameter("var", String.class, params);
        // // 根据获得的值，根据业务来处理输出数据
        // // 根据这个TpyID我们可以去获取前Top5的新闻，将这个Top5新闻封装在自定义标签中。在预编译模板的时候，自定义标签就会被解析为5条新闻并按照你预定的格式显示在页面上
        // List list = xxxxService.getTop5News(typeId);
        //
        //  //循环这个列表，依此把对象放入自定义标签中
        //  for (New news: list) {
        //
        //     // ObjectWrapper.BEANS_WRAPPER
        //     // varName,相当于Map中的Key.通过varName可以在模板中取出new对象的值
        //     env.setVariable(varName , DEFAULT_WRAPPER.wrap(news));
        //     body.render(env.getOut());
        // }
    }
}
