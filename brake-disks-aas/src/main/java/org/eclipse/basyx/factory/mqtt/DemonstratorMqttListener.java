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
package org.eclipse.basyx.factory.mqtt;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;

/**
 * Very simple MQTT broker listener for the demonstrator-aas. Stores the
 * last received event and makes its topic and payload available for observers.
 * 
 * @author jungjan
 *
 */
public class DemonstratorMqttListener implements InterceptHandler{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public String lastTopic;
	public String lastPayload;
	private ArrayList<String> topics = new ArrayList<>();
	private Collection<DemonstratorMqttObserver> observers = new ArrayList<>();

	
	public DemonstratorMqttListener() {
		super();
	}
	@Override
	public String getID() {
		return null;
	}

	@Override
	public Class<?>[] getInterceptedMessageTypes() {
		return null;
	}

	@Override
	public void onConnect(InterceptConnectMessage arg0) {
	}

	@Override
	public void onConnectionLost(InterceptConnectionLostMessage arg0) {
	}

	@Override
	public void onDisconnect(InterceptDisconnectMessage arg0) {
	}

	@Override
	public void onMessageAcknowledged(InterceptAcknowledgedMessage arg0) {
	}

	@Override
	public synchronized void onPublish(InterceptPublishMessage msg) {
		this.topics.add(msg.getTopicName());
		this.lastTopic = msg.getTopicName();
		this.lastPayload = msg.getPayload().toString(StandardCharsets.UTF_8);
		logger.debug("MQTT Event: [{}, {}]", this.lastTopic, this.lastPayload);
		notifyObservers();
	}

	@Override
	public void onSubscribe(InterceptSubscribeMessage arg0) {
	}

	@Override
	public void onUnsubscribe(InterceptUnsubscribeMessage arg0) {
	}

	public ArrayList<String> getTopics() {
		return topics;
	}
	
	public void addObserver(DemonstratorMqttObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(DemonstratorMqttObserver observer) {
		if(!observers.contains(observer)) {
			logger.debug("Observer {} does not exist", observer);
			return;
		}
		observers.remove(observer);
	}
	
	private void notifyObservers() {
		for(DemonstratorMqttObserver observer : observers) {
			observer.onUpdate();
		}
	}
	
}
