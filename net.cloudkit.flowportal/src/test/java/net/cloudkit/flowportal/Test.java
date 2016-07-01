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

package net.cloudkit.flowportal;

import eport.common.ikey.IKeyAPI;
import net.sf.jni4net.Bridge;
import net.sf.jni4net.Out;

import java.io.IOException;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/12/28 13:53
 */
public class Test {

    public static void main(String arsg[]) throws IOException {

        /*
        // Bridge.init();
        // Bridge.LoadAndRegisterAssemblyFrom(new java.io.File("D:\\IdeaProjects\\trunk\\cloudkit\\net.cloudkit.flowportal\\libs\\IKey.j4n.dll"));

        IKeyAPI iKeyAPI = new IKeyAPI();
        // System.out.println(iKeyAPI.GetHashCode());
        IKeyAPI.InitKey();
        IKeyAPI.VerifyPw("88888888");

        Out<String> out = new Out<String>();
        IKeyAPI.SignData("123456", out);
        System.out.println(out.toString());
        */


//        Bridge.init();
//        Bridge.LoadAndRegisterAssemblyFrom(new java.io.File("D:\\IdeaProjects\\trunk\\cloudkit\\net.cloudkit.flowportal\\libs\\ClassLibrary3.j4n.dll"));
//        classlibrary2.Class1 class1 = new classlibrary2.Class1();
//        class1.test();



        String b="";
        switch(Color.RED){

        }
    }

}

enum Color {
    RED(1, "红色"), GREEN(2, "绿色"), BLANK(3, "白色"), YELLO(4, "黄色");
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private Color(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static Color get(String value) {
        for (Color color : values()) {
            if (color.toString().equals(value)) {
                return color;
            }
        }
        return null;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }
}
