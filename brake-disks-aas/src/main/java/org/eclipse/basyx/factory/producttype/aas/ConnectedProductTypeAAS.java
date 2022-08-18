/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.producttype.aas;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.producttype.submodel.ConnectedRecipeSubmodel;
import org.eclipse.basyx.factory.producttype.submodel.RecipeSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected implementation of IProductTypeAAS
 * 
 * @author conradi
 *
 */
public class ConnectedProductTypeAAS extends ConnectedAssetAdministrationShell implements IProductTypeAAS {

	public ConnectedProductTypeAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedRecipeSubmodel getRecepieSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + RecipeSubmodel.ID_SUFFIX));
		return new ConnectedRecipeSubmodel(connectedSM.getProxy());
	}

	
	public static ConnectedProductTypeAAS getConnectedProductTypeAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedProductTypeAAS(proxy);
	}
}
