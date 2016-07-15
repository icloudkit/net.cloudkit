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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2016/7/15.
 */
@XmlRootElement(name="book")
public class Book {
    Integer id;
    String author;
    String bookName;
    String publisher;
    int price;

    public Book(Integer id, String author, String bookName, String publisher, int price) {
        this.id = id;
        this.author = author;
        this.bookName = bookName;
        this.publisher = publisher;
        this.price = price;
    }

    public void setId(Integer id){
        this.id=id;
    }
    @XmlAttribute
    public Integer getId(){
        return id;
    }
    @XmlAttribute
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @XmlAttribute
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    @XmlAttribute
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    @XmlAttribute
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
