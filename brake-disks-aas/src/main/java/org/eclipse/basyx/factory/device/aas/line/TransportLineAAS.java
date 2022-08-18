/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.line;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAAS;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.line.LineContentSubmodel;

/**
 * AAS for the transport line
 * 
 * @author conradi, molina
 *
 */
public class TransportLineAAS extends DeviceAAS implements ITransportLineAAS {

	
	public TransportLineAAS(String idShort, String id, Asset asset, LineContentSubmodel lineContentSM,
			ProcessSubmodel processSM, ServiceSubmodel serviceSM, StatusSubmodel statusSM) {
		
		super(idShort, id, asset, processSM, serviceSM, statusSM);
		addSubmodel(lineContentSM);
	}

	@Override
	public LineContentSubmodel getLineContentSubmodel() {
		throw new RuntimeException("getLineContentSubmodel() not supported on local copy.");
	}
}
