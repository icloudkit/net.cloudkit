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
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import net.cloudkit.enterprises.infrastructure.freemarker.Message;
import net.cloudkit.enterprises.infrastructure.utilities.FreemarkerHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component("flashMessageDirective")
public class FlashMessageDirective implements TemplateDirectiveModel {

    public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = FlashMessageDirective.class.getName() + ".FLASH_MESSAGE";
    private static final String VARIABLE_NAME = "flashMessage";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            Message message = (Message) requestAttributes.getAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, 0);
            if (body != null) {
                FreemarkerHelper.setVariable(env, VARIABLE_NAME, message);
            } else if (message != null) {
                Writer writer = env.getOut();
                writer.write("$.message(\"" + message.getStatus() + "\", \"" + message.getCode() + "\");");
            }
        }
    }
}
