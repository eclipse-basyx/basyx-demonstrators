/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.plant.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.plant.submodel.ITopologySubmodel;
import org.eclipse.basyx.factory.plant.submodel.TopologySubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * AAS showing the relationships between machines of a plant
 * 
 * @author conradi
 *
 */
public class PlantAAS extends AssetAdministrationShell implements IPlantAAS {
	
	private IIdentifier topologySMId;
	
	public PlantAAS(String idShort, String id, Asset asset, TopologySubmodel topologySM) {
		super(idShort, new ModelUrn(id), asset);
		addSubmodel(topologySM);
		
		this.topologySMId = topologySM.getIdentification();
	}

	@Override
	public ITopologySubmodel getTopologySubmodel() {
		return (TopologySubmodel) getSubmodel(topologySMId);
	}

}
