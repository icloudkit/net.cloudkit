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
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/2/19 12:31
 */
public class RandomPassword {

    /**
     * Get rand keys
     *
     * @param length
     * @param isUpperCase
     * @param isLowerCase
     * @param isSpecialChar
     * @return
     */
    public static final String getRandKeys(int length, boolean isUpperCase, boolean isLowerCase, boolean isSpecialChar) {

        String result = "";

        String charTable = "0123456789";
        charTable = isUpperCase ? charTable += "ABCDEFGHJKLMNPQRSTUVWXYZ" : charTable;
        charTable = isLowerCase ? charTable += "abcdefghijkmnpqrstuvwxyz" : charTable;
        charTable = isSpecialChar ? charTable += "~!@#$%^&*()_-+=`;:\"'{[}]<,>.?/" : charTable;

        int len = charTable.length();
        boolean bDone = true;
        do {
            result = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = charTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                result += charTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getRandKeys(8, true, true, true));
    }
}
