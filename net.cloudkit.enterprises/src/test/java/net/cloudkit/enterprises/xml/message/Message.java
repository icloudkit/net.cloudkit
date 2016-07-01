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

import java.util.List;
import java.util.Map;

public class Message {

	/** Message Attribute **/
	private MessageAttribute attribute;

	/** Message Header **/
	private MessageHeader messageHeader;

	/** List<Map<实体名->表名, List<实体>>> **/
	private List<Map<String, List<Object>>> recordMapList;

	/**
	 * @return the attribute
	 */
	public MessageAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(MessageAttribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the messageHeader
	 */
	public MessageHeader getMessageHeader() {
		return messageHeader;
	}

	/**
	 * @param messageHeader the messageHeader to set
	 */
	public void setMessageHeader(MessageHeader messageHeader) {
		this.messageHeader = messageHeader;
	}

	/**
	 * @return the recordMapList
	 */
	public List<Map<String, List<Object>>> getRecordMapList() {
		return recordMapList;
	}

	/**
	 * @param recordMapList the recordMapList to set
	 */
	public void setRecordMapList(List<Map<String, List<Object>>> recordMapList) {
		this.recordMapList = recordMapList;
	}

}
