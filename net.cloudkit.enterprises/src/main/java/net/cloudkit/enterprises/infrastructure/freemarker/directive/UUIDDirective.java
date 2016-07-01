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
import java.util.UUID;

/**
 * UUID生成器
 *
 * 在Freemarker模版文件中这样使用： root.put("uuid", new UUIDDiverecti());
 *
 * 调用 <@uuid></@uuid>
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 上午8:48:04
 */
@Component("uuidDirective")
public class UUIDDirective implements TemplateDirectiveModel {

    private static final String VARIABLE_NAME = "executeTime";

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String uuid = UUID.randomUUID().toString();
        // uuid = StringUtils.remove(uuid, '-');
        env.setVariable(VARIABLE_NAME, new DefaultObjectWrapper(Configuration.VERSION_2_3_22).wrap(uuid));
        // env.getOut().append(uuid);
        // body.render(env.getOut());
        if (body != null) {
            Writer out = env.getOut();
            out.append(uuid);
            body.render(out);
        } else {
            Writer out = env.getOut();
            out.append(uuid);
        }
    }
}
