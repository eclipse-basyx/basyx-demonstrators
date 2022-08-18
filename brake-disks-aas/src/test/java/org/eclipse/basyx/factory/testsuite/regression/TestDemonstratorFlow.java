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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.factory.simulation.Main;
import org.eclipse.basyx.factory.simulation.execution.ExecutionStep;
import org.eclipse.basyx.factory.simulation.factory.DefaultDemonstrationFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * Tests the overall demonstrator flow
 * 
 * @author danish
 *
 */
@TestMethodOrder(OrderAnnotation.class)
public class TestDemonstratorFlow {
	public static final String REGISTRY_URL = "http://localhost:8080";
	
	private static final String TEST_AAS = "TestBlankAAS";
	
	private static AASAggregator aggregator = new AASAggregator(new AASRegistryProxy(REGISTRY_URL));
	
	private static DefaultDemonstrationFactory factory;
	
	private static SteppableExecutionEngine engine;
	
	private static List<ExecutionStep> steps;
	
	@BeforeAll
	public static void setup() throws InterruptedException {
		factory = new DemonstratorFactory(aggregator);
		
		Main main = new Main(factory, aggregator);
		
		engine = (SteppableExecutionEngine) main.getEngine();
		
		checkIfEngineIsExecuted();
		
		steps = engine.getSteps();
	}

	private static void checkIfEngineIsExecuted() throws InterruptedException {
		while(!engine.getExecutionStatus()) {
			Thread.sleep(2000);
		}
	}
	
	@Test
	@Order(1)
	void fromContainerToStorage() {
		List<String> execResult =  engine.executeSingleStep(steps.get(0));
		
		assertEquals("storageIdShort", execResult.get(0));
		assertEquals(TEST_AAS, execResult.get(1));
	}
	
	@Test
	@Order(2)
	void transportFromStorageToOven() {
		List<String> execResult =  engine.executeSingleStep(steps.get(1));
		
		assertEquals("transportRobot_idShort", execResult.get(0));
	}
	
	@Test
	@Order(3)
	void insideOven() {
		List<String> execResult =  engine.executeSingleStep(steps.get(2));
		
		assertEquals("oven_idShort", execResult.get(0));
		assertEquals(TEST_AAS, execResult.get(1));
	}
	
	@Test
	@Order(4)
	void transportFromOvenToQa() {
		List<String> execResult =  engine.executeSingleStep(steps.get(3));
		
		assertEquals("line_idShort", execResult.get(0));
	}
	
	@Test
	@Order(5)
	void qaProcess() {
		List<String> execResult =  engine.executeSingleStep(steps.get(4));
		
		assertEquals("qa_idShort", execResult.get(0));
		assertEquals(TEST_AAS, execResult.get(1));
	}
	
	@Test
	@Order(5)
	void transportFromQaToStorage() {
		List<String> execResult =  engine.executeSingleStep(steps.get(5));
		
		assertEquals("transportRobot_idShort", execResult.get(0));
	}
	
	@Test
	@Order(6)
	void backToStorage() {
		List<String> execResult =  engine.executeSingleStep(steps.get(6));
		
		assertEquals("storageIdShort", execResult.get(0));
	}
}
