/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
 ******************************************************************************/


/**
 * A simple Parser that parses MqttAASRegistryService events 
 * according to https://wiki.eclipse.org/BaSyx_/_Developer_/_Extensions_/_Eventing
 * 
 * @author jungjan
 *
 */
package org.eclipse.basyx.factory.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryEventParser {
	
	private final String payload;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public RegistryEventParser(String payload) {
		this.payload = payload;
	}
	
	
	/**
	 * Parses the Id part of a shell identifier from a Registry MQTT event.
	 * Assuming the pattern &lt;shellId&gt; for events concerning a shell only
	 * Assuming the pattern (&lt;shellId&gt;,&lt;submodelId&gt;) for events concerning a submodel of a shell
	 * @return the Identifier Id as a String
	 */
	public String parseShellIdentifierId() {
		if(!this.payload.startsWith("("))
			return this.payload;
		String[] ids = extractIds(this.payload);
		return ids[0];			
	}

	
	/**
	 * Parses the Id part of a submodel identifier from a Registry MQTT event.
	 * Assuming the pattern (&lt;shellId&gt;,&lt;submodelId&gt;) for events concerning a submodel of a shell
	 * @return the Identifier Id as a String
	 */
	public String parseSubmodelIdentifierId() {
		if(!this.payload.startsWith("(")) {
			logger.debug("Payload {} does not conatin a submodelId.", payload);
			return null;
		}
		String[] ids = extractIds(this.payload);
		return ids[1];	
	}

	/**
	 * Extracts Ids from a payload string.
	 * Assuming the pattern (&lt;shellId&gt;,&lt;submodelId&gt;) for the payload
	 * @param payload
	 * @return String[] ids = {"&lt;shellId&gt;", "&lt;submodelId&gt;"}
	 */
	private String[] extractIds(String payload) {
		String tmpPayload = removeOuterBrackets(payload);
		return tmpPayload.split(",");
	}
	
	private static String removeOuterBrackets(String str) {
		if(!(str.startsWith("(") && str.endsWith(")"))) {
			return str;
		}
		return str.substring(1, str.length()-1);
	}

}
