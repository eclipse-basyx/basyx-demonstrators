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

/**
 * An ExecutionStep represents a call of a service of a DeviceAAS with given parameters
 * 
 * @author conradi
 *
 */
public class ExecutionStep {

	private String executionDeviceId;
	private String serviceId;
	
	private String[] params;
	
	public ExecutionStep() {
	}

	public ExecutionStep(String executionDeviceId, String serviceId, String[] params) {
		this.executionDeviceId = executionDeviceId;
		this.serviceId = serviceId;
		this.params = params;
	}

	public String getExecutionDeviceId() {
		return executionDeviceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String[] getParams() {
		return params;
	}
	
}
