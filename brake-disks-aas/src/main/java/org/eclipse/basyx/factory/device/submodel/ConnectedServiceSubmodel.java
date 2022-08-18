/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel;

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * ConnectedSubmodel for IServiceSubmodel
 * 
 * @author conradi
 *
 */
public class ConnectedServiceSubmodel extends ConnectedSubmodel implements IServiceSubmodel {

	public ConnectedServiceSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedSubmodelElementCollection getServices() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(ServiceSubmodel.SERVICES_ID_SHORT);
	}

	@Override
	public void invokeService(String serviceId, Object... params) {
		ConnectedOperation op = (ConnectedOperation) getSubmodelElement(serviceId);
		op.invokeSimple(params);
	}
	
}
