/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.line.servicebuilder;

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.aas.line.ConnectedTransportLineAAS;
import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author molina
 *
 */
public class DefaultTransportLineServiceBuilder implements ITransportLineServiceBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultTransportLineServiceBuilder.class);


	@Override
	public Runnable buildTransportAtoB(String transportLineAASId) {
		Runnable invokable = () -> {
			ConnectedTransportLineAAS line = ConnectedTransportLineAAS.getConnectedTransportLineAAS(transportLineAASId);
			ConnectedDeviceAAS devA = line.getLineContentSubmodel().getDeviceA();
			ConnectedDeviceAAS devB = line.getLineContentSubmodel().getDeviceB();
			transport(transportLineAASId, devA, devB);

		};
		
		return invokable;
	}
	
	@Override
	public Runnable buildTransportBtoA(String transportLineAASId) {
		Runnable invokable = () -> {
			ConnectedTransportLineAAS line = ConnectedTransportLineAAS.getConnectedTransportLineAAS(transportLineAASId);
			ConnectedDeviceAAS devA = line.getLineContentSubmodel().getDeviceA();
			ConnectedDeviceAAS devB = line.getLineContentSubmodel().getDeviceB();
			transport(transportLineAASId, devB, devA);
		};
		
		return invokable;
	}
	
	private void transport(String transportLineAASId, ConnectedDeviceAAS dev1, ConnectedDeviceAAS dev2) {
		String dev1Id = dev1.getIdentification().getId();
		String dev2Id = dev2.getIdentification().getId();
		
		
		logger.info("TransportLine "+transportLineAASId+": Transporting from "+dev1Id+" to "+dev2Id);
		ConnectedProductAAS product = dev1.getProcessSubmodel().getCurrentProduct();
		dev2.getProcessSubmodel().setCurrentProduct(product);
		dev1.getProcessSubmodel().setCurrentProduct(null);
		logger.info("TransportLine "+transportLineAASId+": Transport operation finished.");
	}
} 