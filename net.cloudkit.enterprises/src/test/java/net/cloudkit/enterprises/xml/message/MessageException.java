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

package net.cloudkit.enterprises.xml.message;

/**
 * 消息异常
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2012-3-2 下午3:29:19
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = 98614824881440751L;

	private String message = null;
	private ErrorEnum error = null;

	public MessageException() {
		super();
	}

	public MessageException(String message) {
		this.message = message;
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(ErrorEnum error) {
		this.error = error;
		this.message = error.toString();
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public ErrorEnum getError() {
		return this.error;
	}
}
