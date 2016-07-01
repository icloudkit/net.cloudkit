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

package net.cloudkit.relaxation.fonts;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016-05-19 19:19
 */

import net.cloudkit.relaxation.VerifyImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Fonts {
    private Map<String, byte[][]> cache = new HashMap();
    private static Fonts instance = new Fonts();

    public static Fonts getInstance() {
        return instance;
    }

    private Fonts() {
        this.init();
    }

    public Map<String, byte[][]> getFonts() {
        return this.cache;
    }

    private void init() {
        try {
            URL ex = Fonts.class.getResource("libs");
            System.out.println(ex.getPath());
            if("file".equals(ex.getProtocol())) {
                this.loadDir(ex);
            } else {
                this.loadJar(ex);
            }
        } catch (IOException var2) {
            Logger.getLogger(Fonts.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

        this.cache = Collections.unmodifiableMap(this.cache);
    }

    private void loadJar(URL url) throws IOException {
        JarURLConnection jarURL = (JarURLConnection)url.openConnection();
        JarFile jarFile = jarURL.getJarFile();
        Enumeration jarEntries = jarFile.entries();
        String pkgName = Fonts.class.getPackage().getName().replaceAll("\\.", "/") + "/lib";
        InputStream in = null;

        while(jarEntries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry)jarEntries.nextElement();
            String entryName = jarEntry.getName();
            if(entryName.startsWith(pkgName) && entryName.endsWith(".bmp")) {
                String key = entryName.substring(pkgName.length() + 1, entryName.length()).replace(".bmp", "");

                try {
                    in = jarFile.getInputStream(jarEntry);
                    this.cache.put(key, VerifyImage.imgToByteArray(ImageIO.read(in)));
                } finally {
                    if(in != null) {
                        in.close();
                    }

                }
            }
        }

    }

    private void loadDir(URL url) throws IOException {
        File file = new File(url.getFile());
        File[] files = file.listFiles();
        File[] arr$ = files;
        int len$ = files.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            File f = arr$[i$];
            if(f.getName().endsWith("bmp")) {
                this.cache.put(f.getName().replace(".bmp", ""), VerifyImage.imgToByteArray(ImageIO.read(f)));
            }
        }

        System.out.println("font size:" + this.cache.size());
    }

    public static void main(String[] args) {
        Map fonts = getInstance().getFonts();
        Iterator i$ = fonts.entrySet().iterator();

        while(i$.hasNext()) {
            Entry font = (Entry)i$.next();
            System.out.println("k:" + (String)font.getKey() + " v:" + font.getValue());
        }

    }
}
