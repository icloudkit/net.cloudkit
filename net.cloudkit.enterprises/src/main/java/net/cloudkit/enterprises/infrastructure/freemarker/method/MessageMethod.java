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

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import net.cloudkit.enterprises.infrastructure.utilities.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MessageMethod.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月8日 上午11:37:51
 */
@Component("messageMethod")
public class MessageMethod implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) {
        if ((arguments != null) && (!arguments.isEmpty()) && (arguments.get(0) != null) && (StringUtils.isNotEmpty(arguments.get(0).toString()))) {
            String str1 = null;
            String str2 = arguments.get(0).toString();
            if (arguments.size() > 1) {
                Object[] arrayOfObject = arguments.subList(1, arguments.size()).toArray();
                str1 = SpringHelper.getMessage(str2, arrayOfObject);
            } else {
                str1 = SpringHelper.getMessage(str2, new Object[0]);
            }
            return new SimpleScalar(str1);
        }
        return null;
    }
}
