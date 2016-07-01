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

package net.cloudkit.enterprises.xml;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取windows系统信息（CPU,内存,文件系统）
 */
public class WindowsTest {

	private static final int CPUTIME = 500;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;

//	public static void main(String[] args) {
//		System.out.println(getCpuRatioForWindows());
//		System.out.println(getMemery());
//		System.out.println(getDisk());
//	}

//	//获取内存使用率
//	public static String getMemery() {
//		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//		// 总的物理内存+虚拟内存
//		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
//		// 剩余的物理内存
//		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
//		Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
//		String str = "内存已使用:" + compare.intValue() + "%";
//		return str;
//	}

	//获取文件系统使用率
	public static List<String> getDisk() {
		// 操作系统
		List<String> list = new ArrayList<String>();
		for (char c = 'A'; c <= 'Z'; c++) {
			String dirName = c + ":/";
			File win = new File(dirName);
			if (win.exists()) {
				long total = (long) win.getTotalSpace();
				long free = (long) win.getFreeSpace();
				Double compare = (Double) (1 - free * 1.0 / total) * 100;
				String str = c + ":盘  已使用 " + compare.intValue() + "%";
				list.add(str);
			}
		}
		return list;
	}

	//获得cpu使用率
	public static String getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return "CPU使用率:" + Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
			} else {
				return "CPU使用率:" + 0 + "%";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "CPU使用率:" + 0 + "%";
		}
	}

	//读取cpu相关信息
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0)
						idletime += Long.valueOf(s1).longValue();
					if (s2.length() > 0)
						idletime += Long.valueOf(s2).longValue();
					continue;
				}
				if (s1.length() > 0)
					kneltime += Long.valueOf(s1).longValue();
				if (s2.length() > 0)
					usertime += Long.valueOf(s2).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 *
	 * @param src 要截取的字符串
	 * @param start_idx 开始坐标（包括该坐标)
	 * @param end_idx 截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	private static void printUsage() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.print("\n");
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
					e.printStackTrace();
				}
				System.out.println("\t" + method.getName() + " = " + value);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("\n\n\t---------------JVM Runtime Details-------------");
		System.out.println("\tAvailable processors (Cores): " + Runtime.getRuntime().availableProcessors());
		System.out.println("\tInitial Memory (-Xms)       : " + (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " MB");
		long maxMemory = Runtime.getRuntime().maxMemory();
		System.out.println("\tMaximum JVM Memory (-Xmx)   : " + (maxMemory / (1024 * 1024)) + " MB");
		System.out.println("\tTotal Used JVM Memory       : " + (Runtime.getRuntime().totalMemory() / (1024 * 1024)) + " MB");

		File[] roots = File.listRoots();
		System.out.println("\n\n\t---------------FileSystem Details-------------");
		for (File root : roots) {
			System.out.println("\n\tFileSystem Root Details: " + root.getAbsolutePath());
			System.out.println("\tTotal Space              : " + (root.getTotalSpace() / (1024 * 1024)) + " MB");
			System.out.println("\tFree Space               : " + (root.getFreeSpace() / (1024 * 1024)) + " MB");
			System.out.println("\tUsable Space             : " + (root.getUsableSpace() / (1024 * 1024)) + " MB");
		}
		System.out.println("\n\n\t---------------CPU USAGES-------------");
		printUsage();
	}

}
