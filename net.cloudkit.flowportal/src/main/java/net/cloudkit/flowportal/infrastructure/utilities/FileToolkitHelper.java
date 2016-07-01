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

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件工具类 FileToolkitHelper.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年3月3日 下午4:42:41
 */
public class FileToolkitHelper {

    //根目录路径，可以指定绝对路径，比如 /var/www/attached/
    public static final String ROOT_PATH = "D:/";

    //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
    public static final String ROOT_URL = "http://www.ticeng.com/attached/";

    //    // BT字节参考量
    //    public static final long SIZE_BT = 1024L;
    //    // KB字节参考量
    //    public static final long SIZE_KB = SIZE_BT * 1024L;
    //    // MB字节参考量
    //    public static final long SIZE_MB = SIZE_KB * 1024L;
    //    // GB字节参考量
    //    public static final long SIZE_GB = SIZE_MB * 1024L;
    //    // TB字节参考量
    //    public static final long SIZE_TB = SIZE_GB * 1024L;
    //    // 保留小数位
    //    public static final int SACLE = 2;

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public FileToolkitHelper() {
    }

    /**
     * 获取目录中文件列表
     *
     * @param directory
     * @param path
     * @param order
     * @param regex
     * @return
     */
    public static Map<String, Object> getFiles(String directory, String path, String order, String regex) {

        //根目录路径，可以指定绝对路径，比如 /var/www/attached/
        String rootPath = ROOT_PATH;
        //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = ROOT_URL;
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        if (directory != null) {

            //            // TODO 允许目录名
            //            if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(directory)) {
            //                System.out.println("Invalid Directory name.");
            //                return null;
            //            }

            rootPath += directory + File.separator;
            rootUrl += directory + File.separator;

            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                System.err.println("Directory does not exist.");
                saveDirFile.mkdirs();
            }
        }
        // 根据path参数，设置各路径和URL
        path = path != null ? path : "";
        String absolutePath = rootPath + path;
        String url = rootUrl + path;
        String prevPath = "";
        if (!"".equals(path)) {
            String str = path.substring(0, path.length() - 1);
            prevPath = str.lastIndexOf(File.separator) >= 0 ? str.substring(0, str.lastIndexOf(File.separator) + 1) : "";
        }

        //排序形式，name or size or type
        order = order != null ? order : "name";

        //不允许使用..移动到上一级目录
        if (path.indexOf("src/main") >= 0) {
            System.out.println("Access is not allowed.");
            return null;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith(File.separator)) {
            System.out.println("Parameter is not valid.");
            return null;
        }
        //目录不存在或不是目录
        File currentPathFile = new File(absolutePath);
        if (!currentPathFile.isDirectory()) {
            System.out.println("Directory does not exist.");
            return null;
        }

        //遍历目录取的文件信息
        List<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();

        File[] files = currentPathFile.listFiles(new FileNameFilter(regex));
        if (files != null) {
            for (File file : files) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("file_size", 0L);
                    // hash.put("is_image", false);
                    hash.put("file_type", "--");
                } else if (file.isFile()) {
                    // prefix
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    // hash.put("has_file", false);

                    // // TODO 文件大小单位转换
                    // Long longSize = file.length();
                    // if (longSize >= 0 && longSize < SIZE_BT) {
                    //     // fileSize = longSize + "B";
                    // } else if (longSize >= SIZE_BT && longSize < SIZE_KB) {
                    //     // fileSize = longSize / SIZE_BT + "KB";
                    // } else if (longSize >= SIZE_KB && longSize < SIZE_MB) {
                    //     // fileSize = longSize / SIZE_KB + "MB";
                    // } else if (longSize >= SIZE_MB && longSize < SIZE_GB) {
                    //     BigDecimal longs = new BigDecimal(Double.valueOf(longSize).toString());
                    //     BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_MB).toString());
                    //     String result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString();
                    //     // fileSize = result + "GB";
                    // } else {
                    //     BigDecimal longs = new BigDecimal(Double.valueOf(longSize).toString());
                    //     BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_GB).toString());
                    //     String result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString();
                    //     // fileSize = result + "TB";
                    // }

                    hash.put("file_size", file.length());
                    hash.put("is_image", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("file_type", fileExt);
                    // Readable/Writeable/Executable
                    hash.put("readable", file.canRead());
                    hash.put("writeable", file.canWrite());
                    // unknow
                    hash.put("executable", file.canExecute());
                }
                hash.put("file_name", fileName);
                hash.put("last_modified", new SimpleDateFormat(DATE_FORMAT_PATTERN).format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new Comparator<HashMap<String, Object>>() {

                @Override
                public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {

                    if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
                        return -1;
                    } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
                        return 1;
                    } else {
                        if (((Long) a.get("file_size")) > ((Long) b.get("file_size"))) {
                            return 1;
                        } else if (((Long) a.get("file_size")) < ((Long) b.get("file_size"))) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new Comparator<HashMap<String, Object>>() {

                @Override
                public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {

                    if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
                        return -1;
                    } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
                        return 1;
                    } else {
                        return ((String) a.get("file_type")).compareTo((String) b.get("file_type"));
                    }
                }
            });
        } else if ("name".equals(order)) {
            Collections.sort(fileList, new Comparator<HashMap<String, Object>>() {

                @Override
                public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {

                    if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
                        return -1;
                    } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
                        return 1;
                    } else {
                        return ((String) a.get("file_name")).compareTo((String) b.get("file_name"));
                    }
                }
            });
        } else {
            Collections.sort(fileList, new Comparator<HashMap<String, Object>>() {

                @Override
                public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {

                    if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
                        return -1;
                    } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
                        return 1;
                    } else {
                        DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = df.parse((String) a.get("last_modified"));
                            date2 = df.parse((String) b.get("last_modified"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //     if (date1.before(date2)) {
                        //         return -1;
                        //     } else if (date1.after(date2)) {
                        //         return 1;
                        //     } else {
                        //         return 0;
                        //     }
                        return date1.compareTo(date2);
                    }
                }
            });

        }

        Map<String, Object> result = new HashMap<String, Object>();

        // next prev(previous)
        result.put("prev_path", prevPath);
        // directory folder path
        result.put("path", path);
        result.put("url", url);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        return result;
    }

    /**
     * 重命名文件（夹）
     *
     * @param originalFileOrFolder
     * @param targetName
     */
    public boolean renameFile(String originalFileOrFolder, String targetName) {
        File originalFile = new File(ROOT_PATH + originalFileOrFolder);
        boolean flag = originalFile.renameTo(new File(originalFile.getParentFile().getAbsoluteFile() + File.separator + targetName));
        return flag;
    }

    /**
     * 删除文件（夹）
     *
     * @param fileOrFolder 文件（夹）
     */
    public static boolean delete(File fileOrFolder) {
        boolean flag = false;

        // File file = new File(ROOT_PATH + original);
        if (!fileOrFolder.exists()) return flag;
        if (fileOrFolder.isFile()) {
            // file.deleteOnExit();
            flag = fileOrFolder.delete();
        } else {
            for (File f : fileOrFolder.listFiles()) {
                delete(f);
            }
            flag = fileOrFolder.delete();
        }
        return flag;
    }

    /**
     * 复制文件（夹）到一个目标文件夹
     *
     * @param sourceFileOrFolder 源文件（夹）
     * @param targetFolder 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static boolean copy(File sourceFileOrFolder, File targetFolder) throws IOException {

        boolean flag = false;
        if (!sourceFileOrFolder.exists()) return flag;
        if (!targetFolder.exists()) targetFolder.mkdirs();
        if (sourceFileOrFolder.isFile()) {
            File targetFile = new File(targetFolder.getPath() + File.separator + sourceFileOrFolder.getName());
            // 复制文件到目标地
            InputStream is = new FileInputStream(sourceFileOrFolder);
            FileOutputStream fos = new FileOutputStream(targetFile);
            try {
                byte[] buffer = new byte[1024 * 512];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
            } finally {
                is.close();
                fos.flush();
                fos.close();
            }
            flag = true;
        } else {
            String objFolder = targetFolder.getPath() + File.separator + sourceFileOrFolder.getName();
            File objFolderFile = new File(objFolder);
            objFolderFile.mkdirs();
            for (File file : sourceFileOrFolder.listFiles()) {
                copy(file, objFolderFile);
            }
        }
        return flag;
    }

    /**
     * 将文件（夹）移动到令一个文件夹
     *
     * @param originalFileOrFolder 源文件（夹）
     * @param targetPath 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static boolean move(String originalFileOrFolder, String targetPath) {
        File originalFile = new File(ROOT_PATH + originalFileOrFolder);
        File targetFile = new File(ROOT_PATH + targetPath);
        boolean flag = originalFile.renameTo(new File(targetFile + File.separator + originalFile.getName()));
        return flag;
    }

    /**
     * 创建文件（夹）
     *
     * @param fileOrFolder directory
     * @return
     */
    public boolean createDirectory(String fileOrFolder) {
        File file = new File(ROOT_PATH + fileOrFolder);
        return file.mkdir();
    }

    /**
     * 写入文件
     * @param file 文件
     * @param path 文件路径
     * @param fileName 文件名
     * @return
     */
    public static void write(File file, String path, String fileName) throws FileNotFoundException , IOException{
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            File parentFile = new File(path);
            /** 当父目录不存在时，先创建父目录 */
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            fileOutputStream = new FileOutputStream(path + File.separator + fileName);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            while ((readSize = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, readSize);
            }
            fileOutputStream.flush();

        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
                fileInputStream = null;
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
                fileOutputStream = null;
            }

        }

    }

    public static void main(String args[]) throws IOException {
        // delete(new File("C:/aaa"));
        // copy(new File("D:\\work\\mrpt"), new File("C:\\aaa"));
        // move(new File("C:\\bbb"), new File("C:\\ddd"));
        Map<String, Object> map = getFiles("temp", "", null, null);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ";" + entry.getValue());
        }
    }
}

class FileNameFilter implements FilenameFilter {

    private String fileName;

    private String regex;

    public FileNameFilter(String regex) {
        this.regex = regex;
    }

    private boolean isFilterName() {
        if (regex != null && !regex.equals("")) {
            return fileName.matches(regex);
        } else {
            return true;
        }
    }

    @Override
    public boolean accept(File dir, String fileName) {
        this.fileName = fileName;
        return isFilterName();
    }
}
