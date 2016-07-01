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
import java.util.Map;

/**
 * FreeMarker自定义指令在逐步改变 它嵌套内容的输出转换为大写形式
 * 指令内容
 * 指令参数：无
 * 循环变量：无
 * 指令嵌套内容：是
 *
 * 在Freemarker模版文件中这样使用：
 * root.put("upper", new UpperDirective());
 *
 * 调用
 * foo
 * <@upper>
 *  bar
 *  <#-- 这里允许使用所有的FTL -->
 *  <#list ["red", "green", "blue"] as color>
 *      ${color}
 *  </#list>
 *  baaz
 *  </@upper>
 * wombat
 *
 * 输出
 * foo
 * BAR
 * RED
 * GREEN
 * BLUE
 * BAAZ
 * wombat
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 下午4:35:02
 */
@Component("upperDirective")
public class UpperDirective implements TemplateDirectiveModel {

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        // 检查参数是否传入
        if (!params.isEmpty()) {
            throw new TemplateModelException("This directive doesn't allow parameters.");
        }
        if (loopVars.length != 0) {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }
        // 是否有非空的嵌入内容
        if (body != null) {
            // 执行嵌入体部分，和FTL中的<#nested>一样，除了
            // 我们使用我们自己的writer来代替当前的output writer.
            body.render(new UpperCaseFilterWriter(env.getOut()));
        } else {
            throw new RuntimeException("missing body");
        }
    }

    /**
     * {@link Writer}改变字符流到大写形式， 而且把它发送到另外一个{@link Writer}中。
     */
    private static class UpperCaseFilterWriter extends Writer {
        private final Writer out;

        UpperCaseFilterWriter(Writer out) {
            this.out = out;
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            char[] transformedCbuf = new char[len];
            for (int i = 0; i < len; i++) {
                transformedCbuf[i] = Character.toUpperCase(cbuf[i + off]);
            }
            out.write(transformedCbuf);
        }

        public void flush() throws IOException {
            out.flush();
        }

        public void close() throws IOException {
            out.close();
        }
    }
}
