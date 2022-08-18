/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.storage;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAAS;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.IStorageSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.StorageSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * AAS for the storage device
 * 
 * @author conradi
 *
 */
public class StorageAAS extends DeviceAAS implements IStorageAAS {

	private IIdentifier storageSMId;
	
	public StorageAAS(String aasIdShort, String aasId, Asset asset, StorageSubmodel storageSM,
			ProcessSubmodel processSM, ServiceSubmodel serviceSM, StatusSubmodel statusSM) {
		
		super(aasIdShort, aasId, asset, processSM, serviceSM, statusSM);
		
		addSubmodel(storageSM);
		
		this.storageSMId = storageSM.getIdentification();
	}

	@Override
	public IStorageSubmodel getStorageSubmodel() {
		return (StorageSubmodel) getSubmodel(storageSMId);
	}
	
}
