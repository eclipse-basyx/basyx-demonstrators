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

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.IServiceSubmodel;

public class ExecutionEngine implements IExecutionEngine {
	
	private StatusPrinter printer;

	public ExecutionEngine(List<IDeviceAAS> devices) {
		printer = new StatusPrinter(devices);
	}

	public void execute(List<ExecutionStep> steps) {
		for (ExecutionStep step: steps) {
			ConnectedDeviceAAS device = ConnectedDeviceAAS.getConnectedDeviceAAS(step.getExecutionDeviceId());
			IServiceSubmodel serviceSM = device.getServiceSubmodel();
			executeService(serviceSM, step.getServiceId(), step.getParams());
			printer.printStatus();
		}
	}
	
	public static void executeService(IServiceSubmodel serviceSM, String serviceId, Object[] params) {
		switch (params.length) {
		case 0:
			serviceSM.invokeService(serviceId);
			break;
		case 1:
			serviceSM.invokeService(serviceId, params[0]);
			break;
		case 2:
			serviceSM.invokeService(serviceId, params[0], params[1]);
			break;
		case 3:
			serviceSM.invokeService(serviceId, params[0], params[1], params[2]);
			break;

		default:
			throw new RuntimeException("Call with more than 3 parameters not supported.");
		}
	}
	
}
