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

import java.math.BigDecimal;
import java.util.List;

/**
 * CurrencyMethod.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 下午5:23:03
 */
@Component("currencyMethod")
public class CurrencyMethod implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) {
        if ((arguments != null) && (!arguments.isEmpty()) && (arguments.get(0) != null) && (StringUtils.isNotEmpty(arguments.get(0).toString()))) {
            boolean bool1 = false;
            boolean bool2 = false;
            if (arguments.size() == 2) {
                if (arguments.get(1) != null) {
                    bool1 = Boolean.valueOf(arguments.get(1).toString()).booleanValue();
                }
            } else if (arguments.size() > 2) {
                if (arguments.get(1) != null) {
                    bool1 = Boolean.valueOf(arguments.get(1).toString()).booleanValue();
                }
                if (arguments.get(2) != null) {
                    bool2 = Boolean.valueOf(arguments.get(2).toString()).booleanValue();
                }
            }

            // BigDecimal.setScale()方法用于格式化小数点
            // setScale(1)表示保留一位小数，默认用四舍五入方式
            // setScale(1, BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
            // setScale(1, BigDecimal.ROUND_UP)进位处理，2.35变成2.4
            // setScale(1, BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
            // setScale(1, BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
            BigDecimal localBigDecimal = new BigDecimal(arguments.get(0).toString());
            // newScale:PriceScale 2, roundingMode:PriceRoundType BigDecimal.ROUND_HALF_UP
            String str = localBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            // currencySign
            if (bool1) {
                str = "￥" + str;
            }
            // currencyUnit
            if (bool2) {
                str = str + "元";
            }
            return new SimpleScalar(str);
        }
        return null;
    }
}






