/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.robot.servicebuilder;

import java.util.function.Consumer;

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.aas.robot.ConnectedTransportRobotAAS;
import org.eclipse.basyx.factory.device.submodel.ConnectedProcessSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTransportRobotServiceBuilder implements ITransportRobotServiceBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultTransportRobotServiceBuilder.class);

	/**
	 * Builds consumer for transportFromTo service
	 * Consumer takes two strings, the ids of the machines from where to where should be transported
	 * 
	 * @param robotAASId id of the transportRobotAAS
	 * @return Consumer object for transportFromTo service
	 */
	@Override
	public Consumer<Object[]> buildTransportFromToConsumer(String robotAASId) {
		Consumer<Object[]> invokable = (Consumer<Object[]>) o -> {
			
			String fromId = (String) o[0];
			String toId = (String) o[1];
			
			logger.info("TransportRobot '" + robotAASId + "': Starting transportFromTo service ...");
			logger.info("TransportRobot '" + robotAASId + "': Transporting from '" + fromId + "' to '" + toId + "'.");
			
			if(fromId.equals(toId)) {
				throw new RuntimeException("Specified from and to devices are identical.");
			}
			
			
			ConnectedTransportRobotAAS robot = ConnectedTransportRobotAAS.getConnectedTransportRobotAAS(robotAASId);
			ConnectedProperty actuatorPosition = robot.getActuatorPositionSubmodel().getCurrentPosition();
			ConnectedProcessSubmodel robotProcessSubmodel = robot.getProcessSubmodel();
			
			ConnectedDeviceAAS from = ConnectedDeviceAAS.getConnectedDeviceAAS(fromId);
			ConnectedDeviceAAS to = ConnectedDeviceAAS.getConnectedDeviceAAS(toId);
			
			ConnectedProcessSubmodel fromProcessSM = from.getProcessSubmodel();
			ConnectedProcessSubmodel toProcessSM = to.getProcessSubmodel();
			
			if(fromProcessSM.currentProductIsEmpty()) {
				throw new RuntimeException("Nothing to retrieve at device '" + fromId + "'.");
			}
			if(!toProcessSM.currentProductIsEmpty()) {
				throw new RuntimeException("Transfer point of device '" + toId + "' not empty.");
			}
			
			// Retrieve Product from "from"-device
			logger.info("TransportRobot '" + robotAASId + ": Collecting product from '" + fromId + "'.");
			actuatorPosition.setValue(fromId);
			robotProcessSubmodel.setCurrentProduct(fromProcessSM.getCurrentProduct());
			fromProcessSM.setCurrentProduct(null);
			
			// Transport Product to "to"-device
			logger.info("TransportRobot '" + robotAASId + ": Placing product at '" + toId + "'.");
			actuatorPosition.setValue("");
			actuatorPosition.setValue(toId);
			toProcessSM.setCurrentProduct(robotProcessSubmodel.getCurrentProduct());
			robotProcessSubmodel.setCurrentProduct(null);

			logger.info("TransportRobot '" + robotAASId + ": transportFromTo service completed.");
		};
		
		return invokable;
	}

}
