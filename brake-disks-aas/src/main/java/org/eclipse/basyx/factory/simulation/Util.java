/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

public class Util {

	public static VABElementProxy getAASProxy(String id) {
		ConnectedAssetAdministrationShellManager manager = getManager();

		return manager.retrieveAAS(new ModelUrn(id)).getProxy();
	}

	public static VABElementProxy getSMProxy(IIdentifier aasId, IIdentifier smId) {
		ConnectedAssetAdministrationShellManager manager = getManager();

		return ((ConnectedSubmodel) manager.retrieveSubmodel(aasId, smId)).getProxy();
	}

	public static ConnectedAssetAdministrationShellManager getManager() {
		AASRegistryProxy registryProxy = new AASRegistryProxy(Main.REGISTRY_URL);

		return new ConnectedAssetAdministrationShellManager(registryProxy);
	}

	public static Asset getAsset(String aasIdShort, String aasId) {
		return new Asset(aasIdShort + "_Asset", new ModelUrn(aasId + "_Asset"), AssetKind.INSTANCE);
	}

}
