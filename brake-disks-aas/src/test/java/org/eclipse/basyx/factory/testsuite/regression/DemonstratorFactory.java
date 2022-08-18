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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.blank.aas.BlankAAS;
import org.eclipse.basyx.factory.blank.aas.BlankAASFactory;
import org.eclipse.basyx.factory.blank.submodel.BlankSubmodel;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.device.aas.line.ConnectedTransportLineAAS;
import org.eclipse.basyx.factory.device.aas.line.TransportLineAASFactory;
import org.eclipse.basyx.factory.device.aas.oven.ConnectedOvenAAS;
import org.eclipse.basyx.factory.device.aas.oven.OvenAASFactory;
import org.eclipse.basyx.factory.device.aas.qa.ConnectedQaAAS;
import org.eclipse.basyx.factory.device.aas.qa.QaAASFactory;
import org.eclipse.basyx.factory.device.aas.robot.ConnectedTransportRobotAAS;
import org.eclipse.basyx.factory.device.aas.robot.TransportRobotAASFactory;
import org.eclipse.basyx.factory.device.aas.storage.ConnectedStorageAAS;
import org.eclipse.basyx.factory.device.aas.storage.StorageAASFactory;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;
import org.eclipse.basyx.factory.simulation.factory.DefaultDemonstrationFactory;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * It is used to build scenario and mocked blanks
 * for performing test. 
 * 
 * @author danish
 *
 */
public class DemonstratorFactory extends DefaultDemonstrationFactory {

	public DemonstratorFactory(IAASAggregator aggregator) {
		super(aggregator);
	}

	@Override
	public List<IDeviceAAS> buildScenario() {
		AASBundle transportRobot = TransportRobotAASFactory.buildTransportRobotAAS(TRANSPORT_ROBOT_AAS_ID_SHORT, TRANSPORT_ROBOT_AAS_ID);
		AASBundle storage = StorageAASFactory.buildStorageAAS(STORAGE_AAS_ID_SHORT, STORAGE_AAS_ID, new ArrayList<>());
		AASBundle oven = OvenAASFactory.buildOvenAAS(OVEN_AAS_ID_SHORT, OVEN_AAS_ID);
		AASBundle qa = QaAASFactory.buildQaAAS(QA_AAS_ID_SHORT, QA_AAS_ID);
		AASBundle line = TransportLineAASFactory.buildTransportLineAAS(LINE_AAS_ID_SHORT, LINE_AAS_ID, (IDeviceAAS) oven.getAAS(), (IDeviceAAS) qa.getAAS());

		
		pushBundle(storage);
		pushBundle(transportRobot);
		pushBundle(oven);
		pushBundle(qa);
		pushBundle(line);
		
		buildAndPushFilledStorageContainers(1, STORAGE_AAS_ID);
		
		List<IDeviceAAS> devices = new ArrayList<>();
		devices.add(ConnectedStorageAAS.getConnectedStorageAAS(storage.getAAS().getIdentification().getId()));
		devices.add(ConnectedTransportRobotAAS.getConnectedTransportRobotAAS(transportRobot.getAAS().getIdentification().getId()));
		devices.add(ConnectedOvenAAS.getConnectedOvenAAS(oven.getAAS().getIdentification().getId()));
		devices.add(ConnectedQaAAS.getConnectedQaAAS(qa.getAAS().getIdentification().getId()));
		devices.add(ConnectedTransportLineAAS.getConnectedTransportLineAAS(line.getAAS().getIdentification().getId()));

		return devices;
	}
	
	@Override
	protected BlankAAS buildBlank(int index, String material) {
		BlankAASFactory blankFactory = mock(BlankAASFactory.class);
		
		BlankSubmodel blankSM = createMockedBlank();
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(blankSM);
		
		when(blankFactory.buildBlankAAS(material, index)).thenReturn(createMockedAASBundle(blankSM, smList));
		
		AASBundle blank = blankFactory.buildBlankAAS(material, index);
		
		pushBundle(blank);
		
		return (BlankAAS) blank.getAAS();
	}

	private AASBundle createMockedAASBundle(BlankSubmodel blankSM, Set<ISubmodel> smList) {
		return new AASBundle(new BlankAAS("TestBlankAAS", new ModelUrn("TestBlankId"), Util.getAsset("TestBlankAAS", "TestBlankId"), blankSM), smList);
	}

	private BlankSubmodel createMockedBlank() {
		BlankSubmodel blankSM = new BlankSubmodel(new Property("TestBlankSMPropertyIdShort", "TestBlankType"), new ModelUrn("TestBlankSMId"), "TestBlankSMIdShort");
		return blankSM;
	}
	
	@Override
	public IExecutionEngine getEngine(List<IDeviceAAS> devices) {
		return new SteppableExecutionEngine(devices);
	}
}
