/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.blank.aas;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.factory.blank.submodel.ConnectedBlankSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

public class ConnectedBlankAAS extends ConnectedAssetAdministrationShell implements IBlankAAS {

	public ConnectedBlankAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedBlankSubmodel getBlankSubmodel() {
		Map<String, ISubmodel> submodels = getSubmodels();
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) submodels.values().iterator().next();
		return new ConnectedBlankSubmodel(connectedSM.getProxy());
	}

}
