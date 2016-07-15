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

package net.cloudkit.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
public class BookDao {
    Map<Integer, Book> books = new HashMap<Integer, Book>();
    private static int nextId = 7;

    public BookDao() {

        books.put(1, new Book(1, "张三", "C语言程序设计", "简单出版社", 29));
        books.put(2, new Book(2, "张三", "Java语言程序设计", "信息出版社", 43));
        books.put(3, new Book(3, "李四", "大学物理", "教育出版社", 22));
        books.put(4, new Book(4, "李四", "高等数学", "教育出版社", 43));
        books.put(5, new Book(5, "孔子", "论语", "春秋出版社", 53));
        books.put(6, new Book(6, "福布斯", "大学英语", "未名出版社", 22));
    }

    public Book get(Integer id) {
        return books.get(id);
    }

    public boolean update(Book bk) {
        if (bk.getId() == null) {
            bk.setId(nextId++);
        }
        books.put(bk.getId(), bk);
        return true;
    }

    public boolean delete(Integer id) {
        if (books.remove(id) == null)
            return false;
        else
            return true;
    }

    public Map<Integer, Book> getAll() {
        return books;
    }
}
