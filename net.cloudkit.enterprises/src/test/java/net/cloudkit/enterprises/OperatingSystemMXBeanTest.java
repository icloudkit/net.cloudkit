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

import javax.management.MBeanServerConnection;
import java.lang.management.*;
import java.util.List;

/**
 * 软件包 java.lang.management.
 */
public class OperatingSystemMXBeanTest {

    /**
     * 软件包 java.lang.management
     *
     * 提供管理接口，用于监视和管理 Java 虚拟机以及 Java 虚拟机在其上运行的操作系统。
     *
     * 接口摘要
     * ClassLoadingMXBean	    用于 Java 虚拟机的类加载系统的管理接口。
     * CompilationMXBean	    用于 Java 虚拟机的编译系统的管理接口。
     * GarbageCollectorMXBean	用于 Java 虚拟机的垃圾回收的管理接口。
     * MemoryManagerMXBean	    内存管理器的管理接口。
     * MemoryMXBean	            Java 虚拟机的内存系统的管理接口。
     * MemoryPoolMXBean	        内存池的管理接口。
     * OperatingSystemMXBean	用于操作系统的管理接口，Java 虚拟机在此操作系统上运行。
     * RuntimeMXBean	        Java 虚拟机的运行时系统的管理接口。
     * ThreadMXBean             Java 虚拟机线程系统的管理接口。
     * @param args
     */
    public static void main(String[] args) {

        // 操作系统信息
        OperatingSystemMXBean operateSystemMBean = ManagementFactory.getOperatingSystemMXBean();
        String name = operateSystemMBean.getName();
        int availableProcessors = operateSystemMBean.getAvailableProcessors();
        String arch = operateSystemMBean.getArch();// System.getProperty("os.arch");
        String version = operateSystemMBean.getVersion();// System.getProperty("os.version");

        // 运行时信息
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String vmName = runtimeMXBean.getVmName();
        String vmVersion = runtimeMXBean.getVmVersion();
        // System.getProperty("java.version") + " (" + vmVersion + ")");
        String bootClassPath = runtimeMXBean.getBootClassPath();
        String vmVendor = runtimeMXBean.getVmVendor();
        long startTime = runtimeMXBean.getStartTime();
        runtimeMXBean.getInputArguments();
        runtimeMXBean.getSystemProperties();

        // 类信息
        ClassLoadingMXBean classLoadMXBean = ManagementFactory.getClassLoadingMXBean();
        int loadClassCount = classLoadMXBean.getLoadedClassCount();
        long totalLoadedClassCount = classLoadMXBean.getTotalLoadedClassCount();
        long unloadClazzCount = classLoadMXBean.getUnloadedClassCount();

        // 内存
        MemoryMXBean memoryUsage = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryUsage.getHeapMemoryUsage();
        System.out.println("Heap committed:" + heap.getCommitted() + " init:" + heap.getInit() + " max:" + heap.getMax() + " used:" + heap.getUsed());
        memoryUsage.getNonHeapMemoryUsage();
    }


    /**
     * Java 虚拟机的运行时系统
     */
    public static void showJvmInfo() {
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        String vendor = mxbean.getVmVendor();
        System.out.println("jvm name:" + mxbean.getVmName());
        System.out.println("jvm version:" + mxbean.getVmVersion());
        System.out.println("jvm bootClassPath:" + mxbean.getBootClassPath());
        System.out.println("jvm start time:" + mxbean.getStartTime());
    }

    /**
     * Java 虚拟机的内存系统
     */
    public static void showMemoryInfo() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        System.out.println("Heap committed:" + heap.getCommitted() + " init:" + heap.getInit() + " max:"
            + heap.getMax() + " used:" + heap.getUsed());
    }

    /**
     * Java 虚拟机在其上运行的操作系统
     */
    public static void showSystem() {
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Architecture: " + op.getArch());
        System.out.println("Processors: " + op.getAvailableProcessors());
        System.out.println("System name: " + op.getName());
        System.out.println("System version: " + op.getVersion());
        System.out.println("Last minute load: " + op.getSystemLoadAverage());
    }

    /**
     * Java 虚拟机的类加载系统
     */
    public static void showClassLoading(){
        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
        System.out.println("TotalLoadedClassCount: " + cl.getTotalLoadedClassCount());
        System.out.println("LoadedClassCount" + cl.getLoadedClassCount());
        System.out.println("UnloadedClassCount:" + cl.getUnloadedClassCount());
    }

    /**
     * Java 虚拟机的编译系统
     */
    public static void showCompilation(){
        CompilationMXBean com = ManagementFactory.getCompilationMXBean();
        System.out.println("TotalCompilationTime:" + com.getTotalCompilationTime());
        System.out.println("name:" + com.getName());
    }

    /**
     * Java 虚拟机的线程系统
     */
    public static void showThread(){
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        System.out.println("ThreadCount" + thread.getThreadCount());
        System.out.println("AllThreadIds:" + thread.getAllThreadIds());
        System.out.println("CurrentThreadUserTime" + thread.getCurrentThreadUserTime());
        //......还有其他很多信息
    }

    /**
     * Java 虚拟机中的垃圾回收器。
     */
    public static void showGarbageCollector(){
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean GarbageCollectorMXBean : gc){
            System.out.println("name:" + GarbageCollectorMXBean.getName());
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());
        }
    }

    /**
     * Java 虚拟机中的内存管理器
     */
    public static void showMemoryManager(){
        List<MemoryManagerMXBean> mm = ManagementFactory.getMemoryManagerMXBeans();
        for(MemoryManagerMXBean eachmm: mm){
            System.out.println("name:" + eachmm.getName());
            System.out.println("MemoryPoolNames:" + eachmm.getMemoryPoolNames().toString());
        }
    }

    /**
     * Java 虚拟机中的内存池
     */
    public static void showMemoryPool(){
        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean mp : mps){
            System.out.println("name:" + mp.getName());
            System.out.println("CollectionUsage:" + mp.getCollectionUsage());
            System.out.println("type:" + mp.getType());
        }
    }

    /**
     * 访问 MXBean 的方法的三种方法
     */
    public static void visitMBean(){

        //第一种直接调用同一 Java 虚拟机内的 MXBean 中的方法。
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        String vendor1 = mxbean.getVmVendor();
        System.out.println("vendor1:" + vendor1);

        //第二种通过一个连接到正在运行的虚拟机的平台 MBeanServer 的 MBeanServerConnection。
        MBeanServerConnection mbs = null;
        // Connect to a running JVM (or itself) and get MBeanServerConnection
        // that has the JVM MXBeans registered in it

        /*
        try {
            // Assuming the RuntimeMXBean has been registered in mbs
            ObjectName oname = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
            String vendor2 = (String) mbs.getAttribute(oname, "VmVendor");
            System.out.println("vendor2:" + vendor2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

//        //第三种使用 MXBean 代理
//        MBeanServerConnection mbs3 = null;
//        RuntimeMXBean proxy;
//        try {
//            proxy = ManagementFactory.newPlatformMXBeanProxy(mbs3,ManagementFactory.RUNTIME_MXBEAN_NAME,
//                                                     RuntimeMXBean.class);
//            String vendor = proxy.getVmVendor();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
