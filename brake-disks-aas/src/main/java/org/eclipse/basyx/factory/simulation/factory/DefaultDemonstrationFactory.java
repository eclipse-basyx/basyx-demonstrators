/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.bundle.AASBundle;
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
import org.eclipse.basyx.factory.simulation.execution.ExecutionEngine;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;

public class DefaultDemonstrationFactory extends DemonstrationFactory {
	
	public static final String TOPOLOGY_SM_ID = "topologySM";
	public static final String TOPOLOGY_SM_ID_SHORT = "topologySMIdShort";
	
	public static final String TRANSPORT_ROBOT_AAS_ID = "transportRobot";
	public static final String TRANSPORT_ROBOT_AAS_ID_SHORT = "transportRobot_idShort";
	
	public static final String STORAGE_AAS_ID = "storage";
	public static final String STORAGE_AAS_ID_SHORT = "storageIdShort";
	
	public static final String OVEN_AAS_ID = "oven";
	public static final String OVEN_AAS_ID_SHORT = "oven_idShort";

	public static final String LINE_AAS_ID = "line";
	public static final String LINE_AAS_ID_SHORT = "line_idShort";
	
	public static final String QA_AAS_ID = "qa";
	public static final String QA_AAS_ID_SHORT = "qa_idShort";
	
	public DefaultDemonstrationFactory(IAASAggregator aggregator) {
		super(aggregator);
	}
	
	/**
	 * Builds a scenario and pushes the Shells to the AASServer
	 */
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
		
		buildAndPushFilledStorageContainers(10, STORAGE_AAS_ID);
		
		List<IDeviceAAS> devices = new ArrayList<>();
		devices.add(ConnectedStorageAAS.getConnectedStorageAAS(storage.getAAS().getIdentification().getId()));
		devices.add(ConnectedTransportRobotAAS.getConnectedTransportRobotAAS(transportRobot.getAAS().getIdentification().getId()));
		devices.add(ConnectedOvenAAS.getConnectedOvenAAS(oven.getAAS().getIdentification().getId()));
		devices.add(ConnectedQaAAS.getConnectedQaAAS(qa.getAAS().getIdentification().getId()));
		devices.add(ConnectedTransportLineAAS.getConnectedTransportLineAAS(line.getAAS().getIdentification().getId()));

		return devices;
	}

	@Override
	public IExecutionEngine getEngine(List<IDeviceAAS> devices) {
		return new ExecutionEngine(devices);
	}
}
