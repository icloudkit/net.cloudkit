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
package net.cloudkit.enterprises.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * http://www.blogjava.net/bukebushuo/articles/229703.html
 * http://jd.benow.ca/
 */
public class Test {

    // 入口启动函数
    public static void main(String[] args) throws Exception {

        // 这个是得到反编译的池
        ClassPool pool = ClassPool.getDefault();

        // 新建一个class
        // CtClass cc = pool.makeClass("Point");

        // 设置目标类的解包后的路径，确保能够找到需要修改的类
        // 取得需要反编译的jar文件，设定路径
        pool.insertClassPath("F:/Downloads/jrebel.jar");

        // 获得要需要反编译修改的类文件，完整包路径
        // CtClass ctClass = pool.get("com.zeroturnaround.javarebel.dP");
        CtClass ctClass = pool.get("com.zeroturnaround.javarebel.eG");

        // 设置方法需要的参数
        CtClass[] param = new CtClass[2];
        // param[0] = pool.get("com.zeroturnaround.javarebel.Yz");
        // param[1] = pool.get("com.zeroturnaround.licensing.UserLicense");
        param[0] = pool.get("com.zeroturnaround.jrebel.bundled.org.bouncycastle.crypto.params.RSAKeyParameters");
        param[1] = pool.get("com.zeroturnaround.licensing.UserLicense");

        /*
        // 取得需要修改的方法
        CtMethod method = ctClass.getDeclaredMethod("validate");
        // 插入修改项，我们让他直接返回(注意：根据方法的具体返回值返回，因为这个方法返回值是void，所以直接return；)
        method.insertBefore("{if(true) return false;}");
        */

        /*
        for(CtMethod ctMethod1 : ctClass.getMethods()) {
            System.out.println(ctMethod1.getName() + ctMethod1.getParameterTypes());
            for (CtClass ctClass1 : ctMethod1.getParameterTypes()) {
                System.out.println(ctClass1.getName());
            }
        }
        */

        // 取得需要修改的方法
        // CtMethod ctMethod = ctClass.getDeclaredMethod("a", param);
        CtMethod ctMethod = ctClass.getDeclaredMethod("a", param);
        // 插入代码块
        // ctMethod.insertBefore("{if(true) return true;}");
        // 设置新的代码
        ctMethod.setBody("return true;");

        /*
        CtMethod fMethod = ctClass.getDeclaredMethod("f");
        CtMethod gMethod = CtNewMethod.copy(fMethod, "g", ctClass, null);
        ctClass.addMethod(gMethod);
        */

        // ctClass.toClass();

        // update the class file

        // 写入保存
        ctClass.writeFile("F:/Downloads/");
        // ctClass.writeFile();
    }

}
