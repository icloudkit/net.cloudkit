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
package net.cloudkit.enterprises.infrastructure.freemarker;

import net.cloudkit.enterprises.infrastructure.utilities.SpringHelper;

/**
 * Message.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月16日 上午9:11:05
 */
public class Message {

    public enum Color {
        RED(255, 0, 0), BLUE(0, 0, 255), BLACK(0, 0, 0), YELLOW(255, 255, 0), GREEN(0, 255, 0);

        // 自定义数据域
        private int redValue;
        private int greenValue;
        private int blueValue;

        // 构造枚举值，比如RED(255,0,0)
        private Color(int r, int g, int b) {
            this.redValue = r;
            this.greenValue = g;
            this.blueValue = b;
        }

        // 覆盖了父类Enum的toString()
        @Override
        public String toString() {
            return super.toString() + "(" + redValue + "," + greenValue + "," + blueValue + ")";
        }

    }

    public enum Status {
        SUCCESS(1), WARN(-1), ERROR(0);

        private int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }
    }

    private String message;

    private String code;

    private int status;

    private Object data;

    public Message() {
    }

    //    public Message(String messageCode, Status status) {
    //        this.message = SpringHelper.getMessage(messageCode);
    //        this.status = status;
    //    }
    //
    //    public Message(String messageCode, Status status, Object... args) {
    //        this.message = SpringHelper.getMessage(messageCode, args);
    //        this.status = status;
    //    }
    //
    //    public Message(String messageCode, Status status, Object data) {
    //        this.message = SpringHelper.getMessage(messageCode);
    //        this.data = data;
    //        this.status = status;
    //    }

    public Message(String message, int status, Object data) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public Message(String code, int status, Object data, Object... args) {
        this.code = code;
        this.message = SpringHelper.getMessage(code, args);
        this.data = data;
        this.status = status;
    }

    public static Message success(Object data) {
        return new Message("ok", Status.SUCCESS.value, data);
    }

    public static Message success(String code, Object data, Object... args) {
        return new Message(code, Status.SUCCESS.value, data, args);
    }

    public static Message error(String code, Object data, Object... args) {
        return new Message(code, Status.ERROR.value, data, args);
    }

    public static Message warn(String code, Object data, Object... args) {
        return new Message(code, Status.WARN.value, data, args);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", message:" + message + ", data:" + data + ", status:" + status + "}";
    }
}

//public class Message {
//
//    public enum Status {
//        SUCCESS, WARN, ERROR;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView
//     *
//     * @param contacts
//     * @return
//     */
//    public static Map<String, Object> mapSuccess(String messageCode, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(3);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("status", Status.SUCCESS);
//
//        return modelMap;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView
//     *
//     * @param contacts
//     * @return
//     */
//    public static Map<String, Object> mapSuccess(String messageCode, Object data, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(3);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("data", data);
//        modelMap.put("status", Status.SUCCESS);
//
//        return modelMap;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView in case of exception
//     *
//     * @param msg message
//     * @return
//     */
//    public static Map<String, Object> mapError(String messageCode, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(2);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("status", Status.ERROR);
//
//        return modelMap;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView in case of exception
//     *
//     * @param msg message
//     * @return
//     */
//    public static Map<String, Object> mapError(String messageCode, Object data, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(2);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("data", data);
//        modelMap.put("status", Status.ERROR);
//
//        return modelMap;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView in case of warn
//     *
//     * @param msg message
//     * @return
//     */
//    public static Map<String, Object> mapWarn(String messageCode, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(2);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("status", Status.WARN);
//
//        return modelMap;
//    }
//
//    /**
//     * Generates modelMap to return in the modelAndView in case of warn
//     *
//     * @param msg message
//     * @return
//     */
//    public static Map<String, Object> mapWarn(String messageCode, Object data, Object... args) {
//
//        Map<String, Object> modelMap = new HashMap<String, Object>(2);
//        modelMap.put("message", SpringHelper.getMessage(messageCode, args));
//        modelMap.put("data", data);
//        modelMap.put("status", Status.WARN);
//
//        return modelMap;
//    }
//}
