/*******************************************************************************
* Copyright (C) 2022 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation.execution;

import java.util.List;

/**
 * Interface for the Execution Engine <br>
 * It is used to perform different implementations of execution
 * 
 * @author danish
 *
 */
public interface IExecutionEngine {
	
	public void execute(List<ExecutionStep> steps);
	
}
