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

package net.cloudkit.enterprises.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 处理队列
		BlockingQueue<String> queue1 = new ArrayBlockingQueue<String>(20, true);

		// Executors类，提供了一系列工厂方法用于创先线程池，返回的线程池都实现了ExecutorService接口。
		// public static ExecutorService newFixedThreadPool(int nThreads)
		// 创建固定数目线程的线程池。
		// public static ExecutorService newCachedThreadPool()
		// 创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
		// public static ExecutorService newSingleThreadExecutor()
		// 创建一个单线程化的Executor。
		// public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
		// 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
		// 创建一个线程池 Executors.newFixedThreadPool(2); Executors.newCachedThreadPool();
		ExecutorService executor = Executors.newFixedThreadPool(2);

		// 执行各个线程
		for (int i = 0; i < 20; i++) {
			executor.execute(new ProducerThread("ProcessorThread:" + i, queue1));
			executor.execute(new ConsumerThread(queue1));
		}

		// 关闭线程池
		executor.shutdown();
	}

}
// public static void main(String[] args) {
//
// ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
//
// Producer producer1 = new Producer("producer1", queue);
// Producer producer2 = new Producer("producer2", queue);
// Producer producer3 = new Producer("producer2", queue);
//
// Consumer consumer1 = new Consumer(queue);
// Consumer consumer2 = new Consumer(queue);
// Consumer consumer3 = new Consumer(queue);
//
// new Thread(producer1).start();
// new Thread(producer2).start();
// new Thread(producer3).start();
// new Thread(consumer1).start();
// new Thread(consumer2).start();
// new Thread(consumer3).start();
// }
