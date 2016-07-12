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

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

/**
 * ResourcesTest.
 */
public class ResourcesTest {

    public static void main(String[] args) {

        try {

            // Resource resource = getApplicationContext().getResource(path);
            // Resource resource = new InputStreamResource();
            Resource resource = new UrlResource("http://guanmaoyun.com/main.html");
            System.out.println("filename:" + resource.getFilename());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
