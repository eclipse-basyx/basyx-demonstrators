/*******************************************************************************
* Copyright (C) 2022 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.testsuite.regression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.IServiceSubmodel;
import org.eclipse.basyx.factory.simulation.execution.ExecutionEngine;
import org.eclipse.basyx.factory.simulation.execution.ExecutionStep;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;
import org.eclipse.basyx.factory.simulation.execution.StatusPrinter;

/**
 * This is an implementation of IExecutionEngine interface
 * It helps in performing the demonstrator flow test
 * 
 * @author danish
 *
 */
public class SteppableExecutionEngine implements IExecutionEngine {
	
	private boolean executionStatus = false;
	
	private List<ExecutionStep> steps = new ArrayList<>();

	public SteppableExecutionEngine(List<IDeviceAAS> devices) {
		new StatusPrinter(devices);
	}

	@Override
	public void execute(List<ExecutionStep> steps) {
		this.steps.addAll(steps);
		
		executionStatus = true;
	}
	
	public List<String> executeSingleStep(ExecutionStep step) {
		ConnectedDeviceAAS device = ConnectedDeviceAAS.getConnectedDeviceAAS(step.getExecutionDeviceId());
		
		IServiceSubmodel serviceSM = device.getServiceSubmodel();
		
		ExecutionEngine.executeService(serviceSM, step.getServiceId(), step.getParams());
		
		String content = device.getProcessSubmodel().getCurrentProduct() != null ? device.getProcessSubmodel().getCurrentProduct().getProductSubmodel().getBlank().getIdShort() : "empty";
		
		return Arrays.asList(device.getIdShort(), content);
	}
	
	public boolean getExecutionStatus() {
		return executionStatus;
	}
	
	public List<ExecutionStep> getSteps() {
		return this.steps;
	}
}
