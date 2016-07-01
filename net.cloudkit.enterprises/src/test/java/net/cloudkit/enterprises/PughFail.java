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

package net.cloudkit.enterprises;

/**
 * PughFail.java
 * http://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/15 17:20
 */
public class PughFail {

    public static class Something {
        private Something() {
            super();
            System.out.println(this.getClass().getName() + " called");
            if (System.currentTimeMillis() > 0) {
                System.out.println("EMULATING INIT FAILURE");
                throw new RuntimeException("EMULATING INIT FAILURE");
            }
        }

        private static class LazyHolder {
            private static final Something INSTANCE = new Something();
        }

        public static Something getInstance() {
            return LazyHolder.INSTANCE;
        }
    }

    public static void main(String[] args) {
        System.out.println("First try");
        try {
            Something.getInstance();
        } catch (Throwable t) {
            System.out.println(t);
        }

        System.out.println("Second try");
        try {
            Something.getInstance();
        } catch (Throwable t) {
            System.out.println(t);
        }
    }
}
