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

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.oven.ConnectedOvenTempSubmodel;
import org.eclipse.basyx.factory.device.submodel.oven.OvenTempSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the OvenAAS
 *
 */
public class ConnectedOvenAAS extends ConnectedDeviceAAS implements IOvenAAS {

	public ConnectedOvenAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedOvenTempSubmodel getOvenTempSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + OvenTempSubmodel.ID_SUFFIX));
		return new ConnectedOvenTempSubmodel(connectedSM.getProxy());
	}
	
	public static ConnectedOvenAAS getConnectedOvenAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedOvenAAS(proxy);
	}

}
