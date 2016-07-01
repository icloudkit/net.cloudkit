/*
 * Copyright (C) 2016. The CloudKit Open Source Project
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

package net.cloudkit.flowportal.application;

import org.activiti.engine.RuntimeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/12/24 17:40
 */
public class UserBeanService {

    /** 由Spring注入 */
    private RuntimeService runtimeService;

    @Transactional
    public void hello() {
        // 这里，你可以在你们的领域模型中做一些事物处理。
        // 当在调用Activiti RuntimeService的startProcessInstanceByKey方法时，
        // 它将会结合到同一个事物中。
        runtimeService.startProcessInstanceByKey("helloProcess");
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }
}
