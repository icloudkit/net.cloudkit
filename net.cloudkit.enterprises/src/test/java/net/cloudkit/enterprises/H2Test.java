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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

/**
 * H2数据库的性能测试
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/13 10:33
 */
public class H2Test {

    public static void main(String[] args) throws Exception {

        // 以嵌入式内存模式工作，建个简单的表
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:data");
        PreparedStatement stmt = conn.prepareStatement("CREATE TABLE TEST (GUID varchar(40) PRIMARY KEY,Data int,AddTime datetime)");
        stmt.execute();
        Random rand = new Random();

        // 精度要求不高，就用这个计时吧
        long start = System.currentTimeMillis();
        stmt = conn.prepareStatement("INSERT INTO TEST VALUES (RANDOM_UUID(), ?, NOW())");
        for(int i = 0;i < 100000;++i){
            stmt.setInt(1, rand.nextInt());
            stmt.execute();
        }
        long duration = System.currentTimeMillis() - start;
        conn.close();

        System.out.println("Finished in " + duration + " ms");
    }
}
