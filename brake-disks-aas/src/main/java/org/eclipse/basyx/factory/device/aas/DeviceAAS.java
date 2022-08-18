/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.submodel.IProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.IServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.IStatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Generic AAS for devices
 * 
 * @author conradi
 *
 */
public class DeviceAAS extends AssetAdministrationShell implements IDeviceAAS {

	private IIdentifier processSMId;
	private IIdentifier serviceSMId;
	private IIdentifier statusSMId;
	
	
	public DeviceAAS(String idShort, String id, Asset asset,
			ProcessSubmodel processSM, ServiceSubmodel serviceSM, StatusSubmodel statusSM) {
		
		super(idShort, new ModelUrn(id), asset);
		
		this.addSubmodel(processSM);
		this.addSubmodel(serviceSM);
		this.addSubmodel(statusSM);
		
		this.processSMId = processSM.getIdentification();
		this.serviceSMId = serviceSM.getIdentification();
		this.statusSMId = statusSM.getIdentification();
	}

	@Override
	public IProcessSubmodel getProcessSubmodel() {
		return (ProcessSubmodel) getSubmodel(processSMId);
	}

	@Override
	public IServiceSubmodel getServiceSubmodel() {
		return (ServiceSubmodel) getSubmodel(serviceSMId);
	}

	@Override
	public IStatusSubmodel getStatusSubmodel() {
		return (StatusSubmodel) getSubmodel(statusSMId);
	}

}
