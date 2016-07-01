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
package net.cloudkit.enterprises.infrastructure.utilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSR303 Validator(Hibernate Validator)工具类.
 *
 * ConstraintViolation中包含propertyPath, message 和invalidValue等信息. 提供了各种convert方法，适合不同的i18n需求:
 * 1. List<String>, String内容为message
 * 2. List<String>, String内容为propertyPath + separator + message
 * 3. Map<propertyPath, message>
 *
 * 详情见wiki: https://github.com/springside/springside4/wiki/HibernateValidator BeanValidators.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月20日 下午11:42:41
 */
public class BeanValidatorHelper {


//    public Map<String, String> validator(Object entity) {
//        // 验证
//        ValidatorFactory validatorfactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorfactory.getValidator();
//        // 单个属性验证
//        // Set<ConstraintViolation<Car>> constraintViolations = validator.validateProperty(car, "manufacturer");
//        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(entity);
//
//        // //用于判断实际值和期望值是否相同
//        // assertEquals(1, constraintViolations.size());
//        // //判断实际值和期望值是否为同一个对象
//        // assertEquals("must be greater than or equal to 2",
//        // constraintViolations.iterator().next().getMessage());
//
//        Map<String, String> messageMap = new HashMap<String, String>();
//        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
//            // getMessage() 获取(经过翻译的)校验错误信息 may not be null
//            // getMessageTemplate() 获取错误信息模版
//            // {javax.validation.constraints.NotNull.message}
//            // getRootBean() 获取被校验的根实体对象 account
//            // getRootBeanClass() 获取被校验的根实体类. Account.class
//            // getLeafBean() 如果约束是添加在一个bean(实体对象)上的,那么则返回这个bean的实例,
//            // 如果是约束是定义在一个属性上的, 则返回这个属性所属的bean的实例对象. car
//            // getPropertyPath() 从被验证的根对象到被验证的属性的路径.
//            // getInvalidValue() 倒是校验失败的值. passengers
//            // getConstraintDescriptor() 导致校验失败的约束定义.
//            // System.err.println(constraintViolation.getInvalidValue() + ", " + constraintViolation.getPropertyPath().toString() + ", " +constraintViolation.getMessage());
//            messageMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
//        }
//        return messageMap;
//    }

    /**
     * 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
     *
     * @param validator
     * @param object
     * @param groups
     * @throws ConstraintViolationException
     */
    public static void validateWithException(Validator validator, Object object, Class<?>... groups) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * 转换ConstraintViolationException中的Set<ConstraintViolations>中为List<message>.
     *
     * @param e
     * @return
     */
    public static List<String> extractMessage(ConstraintViolationException e) {
        return extractMessage(e.getConstraintViolations());
    }

    /**
     * 转换Set<ConstraintViolation>为List<message>
     *
     * @param constraintViolations
     * @return
     */
    public static List<String> extractMessage(Set<? extends ConstraintViolation<?>> constraintViolations) {
        List<String> errorMessages = Lists.newArrayList();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessages.add(violation.getMessage());
        }
        return errorMessages;
    }

    /**
     * 转换ConstraintViolationException中的Set<ConstraintViolations>为Map<property, message>.
     *
     * @param e
     * @return
     */
    public static Map<String, String> extractPropertyAndMessage(ConstraintViolationException e) {
        return extractPropertyAndMessage(e.getConstraintViolations());
    }

    /**
     * 转换Set<ConstraintViolation>为Map<property, message>.
     *
     * @param constraintViolations
     * @return
     */
    public static Map<String, String> extractPropertyAndMessage(Set<? extends ConstraintViolation<?>> constraintViolations) {
        Map<String, String> errorMessages = Maps.newHashMap();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errorMessages;
    }

    /**
     * 转换ConstraintViolationException中的Set<ConstraintViolations>为List<propertyPath message>.
     *
     * @param e
     * @return
     */
    public static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e) {
        return extractPropertyAndMessageAsList(e.getConstraintViolations(), " ");
    }

    /**
     * 转换Set<ConstraintViolations>为List<propertyPath message>.
     *
     * @param constraintViolations
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation> constraintViolations) {
        return extractPropertyAndMessageAsList(constraintViolations, " ");
    }

    /**
     * 转换ConstraintViolationException中的Set<ConstraintViolations>为List<propertyPath +separator+ message>.
     *
     * @param e
     * @param separator
     * @return
     */
    public static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e, String separator) {
        return extractPropertyAndMessageAsList(e.getConstraintViolations(), separator);
    }

    /**
     * 转换Set<ConstraintViolation>为List<propertyPath +separator+ message>.
     *
     * @param constraintViolations
     * @param separator
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation> constraintViolations, String separator) {
        List<String> errorMessages = Lists.newArrayList();
        for (ConstraintViolation violation : constraintViolations) {
            errorMessages.add(violation.getPropertyPath() + separator + violation.getMessage());
        }
        return errorMessages;
    }

}
