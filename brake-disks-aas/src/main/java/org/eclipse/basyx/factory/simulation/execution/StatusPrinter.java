/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation.execution;

import java.util.List;

import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience class for printing the status of all devices
 * 
 * @author conradi
 *
 */
public class StatusPrinter {

	private static Logger logger = LoggerFactory.getLogger(StatusPrinter.class);
	
	private List<IDeviceAAS> devices;
	
	
	public StatusPrinter(List<IDeviceAAS> devices) {
		this.devices = devices;
	}
	
	public void printStatus() {
		devices.stream().forEach((device) -> {
			logger.info("- " + device.getIdShort()+ ": " + device.getProcessSubmodel().getCurrentStep().getValue());
			String content = device.getProcessSubmodel().getCurrentProduct() != null ? device.getProcessSubmodel().getCurrentProduct().toString() : "empty";
			logger.info("-- content of \"" + device.getIdShort() + "\": " + content);
		});
		logger.info("-------------");
	}
	
}
