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
package net.cloudkit.flowportal.infrastructure.utilities;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * XmlHelper.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月2日 上午9:14:20
 */
public class XmlHelper {

    public static String docAsString(Document document) {
        try {
            StringResult res = new StringResult();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), res);
            return res.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

class StringResult extends StreamResult {

    public StringResult() {
        super(new StringWriter());
    }

    /** Returns the written XML as a string. **/
    public String toString() {
        return getWriter().toString();
    }

}
