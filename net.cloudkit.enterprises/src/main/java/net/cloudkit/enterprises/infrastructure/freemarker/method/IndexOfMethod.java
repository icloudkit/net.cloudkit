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
package net.cloudkit.enterprises.infrastructure.freemarker.method;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 返回第一个字符串在第二个字符串第一次出现时的索引位置，如果第二个字符串中不包含第一个字符串，则返回“-1”。
 *
 * 在Freemarker模版文件中这样使用：
 * root.put("indexOf", new IndexOfMethod());
 * 调用
 * <#assign x = "something">
 * ${indexOf("met", x)}
 * ${indexOf("foo", x)}
 * 输出
 * 2
 * -1
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 下午4:28:22
 */
@Component("indexOfMethod")
public class IndexOfMethod implements TemplateMethodModelEx {

    @Override
    @SuppressWarnings("rawtypes")
    public TemplateModel exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        return new SimpleNumber(((String) args.get(1)).indexOf((String) args.get(0)));
    }
}
