package net.cloudkit.enterprises.infrastructure.utilities;///*
// * Copyright (C) 2015 The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package net.cloudkit.ecological.enterprises.infrastructure.utilities;
//
//import com.google.common.collect.Lists;
//import org.dozer.DozerBeanMapper;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
// *
// * 1. 持有Mapper的单例.
// * 2. 返回值类型转换.
// * 3. 批量转换Collection中的所有对象.
// * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
// *
// * BeanMapperHelp.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2013年11月20日 下午11:40:48
// */
//public class BeanMapperHelper {
//
//    /** 持有Dozer单例, 避免重复创建DozerMapper消耗资源. */
//    private static DozerBeanMapper dozer = new DozerBeanMapper();
//
//    /**
//     * 基于Dozer转换对象的类型.
//     *
//     * @param source
//     * @param destinationClass
//     * @return
//     */
//    public static <T> T map(Object source, Class<T> destinationClass) {
//        return dozer.map(source, destinationClass);
//    }
//
//    /**
//     * 基于Dozer转换Collection中对象的类型.
//     *
//     * @param sourceList
//     * @param destinationClass
//     * @return
//     */
//    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
//        List<T> destinationList = Lists.newArrayList();
//        for (Object sourceObject : sourceList) {
//            T destinationObject = dozer.map(sourceObject, destinationClass);
//            destinationList.add(destinationObject);
//        }
//        return destinationList;
//    }
//
//    /**
//     * 基于Dozer将对象A的值拷贝到对象B中.
//     *
//     * @param source
//     * @param destinationObject
//     */
//    public static void copy(Object source, Object destinationObject) {
//        dozer.map(source, destinationObject);
//    }
//}
