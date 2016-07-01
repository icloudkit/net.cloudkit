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

import org.activiti.engine.delegate.event.*;

/**
 * SimpleActivitiEventListener.java
 * BaseEntityEventListener
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/7 16:13
 */
public class SimpleActivitiEventListener implements ActivitiEventListener {

    protected boolean failOnException;
    protected Class<?> entityClass;

    public SimpleActivitiEventListener() {
        this(true, (Class) null);
    }

    public SimpleActivitiEventListener(boolean failOnException) {
        this(failOnException, (Class) null);
    }

    public SimpleActivitiEventListener(boolean failOnException, Class<?> entityClass) {
        this.failOnException = failOnException;
        this.entityClass = entityClass;
    }

    @Override
    public void onEvent(ActivitiEvent event) {

        /*
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job well done!");
                break;

            case JOB_EXECUTION_FAILURE:
                System.out.println("A job has failed...");
                break;

            default:
                System.out.println("Event received: " + event.getType());
        }
        */

        if (this.isValidEvent(event)) {
            if (event.getType() == ActivitiEventType.ENTITY_CREATED) {
                this.onCreate(event);
            } else if (event.getType() == ActivitiEventType.ENTITY_INITIALIZED) {
                this.onInitialized(event);
            } else if (event.getType() == ActivitiEventType.ENTITY_DELETED) {
                this.onDelete(event);
            } else if (event.getType() == ActivitiEventType.ENTITY_UPDATED) {
                this.onUpdate(event);
            } else {
                this.onEntityEvent(event);
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return this.failOnException;
    }

    protected boolean isValidEvent(ActivitiEvent event) {
        boolean valid = false;
        if (event instanceof ActivitiEntityEvent) {
            if (this.entityClass == null) {
                valid = true;
            } else {
                valid = this.entityClass.isAssignableFrom(((ActivitiEntityEvent) event).getEntity().getClass());
            }
        }

        return valid;
    }

    protected void onCreate(ActivitiEvent event) {
    }

    protected void onInitialized(ActivitiEvent event) {
    }

    protected void onDelete(ActivitiEvent event) {
    }

    protected void onUpdate(ActivitiEvent event) {
    }

    protected void onEntityEvent(ActivitiEvent event) {
    }
}
