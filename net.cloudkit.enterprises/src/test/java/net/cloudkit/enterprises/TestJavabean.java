/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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

package net.cloudkit.enterprises;

import java.beans.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * JavaBean相对于java inflect来讲要安全的多,可以通过JavaBean来构建对象而不用担心和学习反射机制
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/12/22 9:37
 */
public class TestJavabean {

    @org.junit.Test
    public void testIntrospector() throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException {

        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
        BeanDescriptor bean = beanInfo.getBeanDescriptor();

        System.out.println("属性修饰器");
        User user = new User();
        user.setAge(1);
        user.setUsername("1111");

        // 拿到所有的属性
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            System.out.println(prop.getName());
            // 属性调用getXXX的方法
            System.out.println(prop.getReadMethod().invoke(user));
            System.out.println(prop.getReadMethod().invoke(user, null));
        }

        System.out.println("构建bean");
        Beans beans = new Beans();
        Object object = (User) beans.instantiate(User.class.getClassLoader(), User.class.getName());
        System.out.println(object);
        for (PropertyDescriptor prop : props) {
            if (prop.getName().equals("age")) {
                prop.getWriteMethod().invoke(object, 11);
            } else if (prop.getName().equals("username")) {
                // 属性调用setXXX的方法
                prop.getWriteMethod().invoke(object, "test");
            }
        }

        User user2 = (User) object;
        System.out.println(user2.getAge() + user2.getUsername());
    }
}

class User {

    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
