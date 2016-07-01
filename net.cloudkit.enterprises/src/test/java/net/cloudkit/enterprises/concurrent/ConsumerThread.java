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

import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 *
 * @author Administrator <hongquanli@qq.com>
 * @version 1.0 2011-10-8 下午12:31:44
 */
public class ConsumerThread implements Runnable {

	private BlockingQueue<String> queue;

	public ConsumerThread(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 执行队列
				System.out.println(queue.take());
				System.out.println("Number of products in the queue after consumption: " + queue.size() + " " + System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
