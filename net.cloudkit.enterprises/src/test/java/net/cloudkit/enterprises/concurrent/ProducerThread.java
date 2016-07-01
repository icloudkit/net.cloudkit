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
 * 生产者
 *
 * @author Administrator <hongquanli@qq.com>
 * @version 1.0 2011-10-8 下午12:31:44
 */
public class ProducerThread extends Thread {

	private String producerName;
	private BlockingQueue<String> queue;

	public ProducerThread(String producerName, BlockingQueue<String> queue) {
		this.producerName = producerName;
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 添加队列
				queue.put("Produced by " + this.producerName);
				System.out.println("Number of products in the queue after production: " + queue.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
