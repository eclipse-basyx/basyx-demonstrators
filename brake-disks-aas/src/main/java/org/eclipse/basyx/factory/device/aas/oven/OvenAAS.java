/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.oven;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAAS;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.oven.IOvenTempSubmodel;
import org.eclipse.basyx.factory.device.submodel.oven.OvenTempSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * AAS for the oven
 * 
 * @author conradi
 *
 */
public class OvenAAS extends DeviceAAS implements IOvenAAS {

	private IIdentifier ovenTempSMId;
	
	public OvenAAS(String idShort, String id, Asset asset, OvenTempSubmodel ovenTempSM,
			ProcessSubmodel processSM, ServiceSubmodel serviceSM, StatusSubmodel statusSM) {
		
		super(idShort, id, asset, processSM, serviceSM, statusSM);
		addSubmodel(ovenTempSM);
		this.ovenTempSMId = ovenTempSM.getIdentification();
	}

	@Override
	public IOvenTempSubmodel getOvenTempSubmodel() {
		return (OvenTempSubmodel) getSubmodel(ovenTempSMId);
	}
}
