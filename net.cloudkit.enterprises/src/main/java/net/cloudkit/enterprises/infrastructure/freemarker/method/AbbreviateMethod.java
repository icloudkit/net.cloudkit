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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * AbbreviateMethod.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月8日 上午11:45:22
 */
@Component("abbreviateMethod")
public class AbbreviateMethod implements TemplateMethodModelEx {

    private static final Pattern CHAR_REGEX = Pattern.compile("[\\u4e00-\\u9fa5\\ufe30-\\uffa0]+$");

    @Override
    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) {
        if ((arguments != null) && (!arguments.isEmpty()) && (arguments.get(0) != null) && (StringUtils.isNotEmpty(arguments.get(0).toString()))) {
            Integer localInteger = null;
            String str = null;
            if (arguments.size() == 2) {
                if (arguments.get(1) != null) {
                    localInteger = Integer.valueOf(arguments.get(1).toString());
                }
            } else if (arguments.size() > 2) {
                if (arguments.get(1) != null) {
                    localInteger = Integer.valueOf(arguments.get(1).toString());
                }
                if (arguments.get(2) != null) {
                    str = arguments.get(2).toString();
                }
            }
            return new SimpleScalar(abbreviate(arguments.get(0).toString(), localInteger, str));
        }
        return null;
    }

    /**
     * 简略
     *
     * @param paramString1
     * @param paramInteger
     * @param paramString2
     * @return
     */
    private String abbreviate(String paramString1, Integer paramInteger, String paramString2) {
        if (paramInteger != null) {
            int i = 0;
            int j = 0;
            while (i < paramString1.length()) {
                j = CHAR_REGEX.matcher(String.valueOf(paramString1.charAt(i))).find() ? j + 2 : j + 1;
                if (j >= paramInteger.intValue()) {
                    break;
                }
                i++;
            }
            if (i < paramString1.length()) {
                if (paramString2 != null) {
                    return paramString1.substring(0, i + 1) + paramString2;
                }
                return paramString1.substring(0, i + 1);
            }
            return paramString1;
        }
        if (paramString2 != null) {
            return paramString1 + paramString2;
        }
        return paramString1;
    }
}
