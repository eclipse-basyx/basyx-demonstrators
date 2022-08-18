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

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.submodel.ConnectedProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ConnectedServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.ConnectedStatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * ConnectedAAS for DeviceAAS
 * 
 * @author conradi
 *
 */
public class ConnectedDeviceAAS extends ConnectedAssetAdministrationShell implements IDeviceAAS {

	public ConnectedDeviceAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedProcessSubmodel getProcessSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + ProcessSubmodel.ID_SUFFIX));
		return new ConnectedProcessSubmodel(connectedSM.getProxy());
	}

	@Override
	public ConnectedServiceSubmodel getServiceSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + ServiceSubmodel.ID_SUFFIX));
		return new ConnectedServiceSubmodel(connectedSM.getProxy());
	}

	@Override
	public ConnectedStatusSubmodel getStatusSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + StatusSubmodel.ID_SUFFIX));
		return new ConnectedStatusSubmodel(connectedSM.getProxy());
	}
	
	public static ConnectedDeviceAAS getConnectedDeviceAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedDeviceAAS(proxy);
	}

}
