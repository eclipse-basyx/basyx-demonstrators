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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.plant.submodel.TopologySubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

/**
 * Factory for building a PlantAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class PlantAASFactory {
	
	public static final String ID = "plant";
	public static final String ID_SHORT = "plant_idShort";
	
	public static AASBundle buildPlantAAS(SubmodelElementCollection topology) {
		TopologySubmodel topologySM = buildTopologySubmodel(ID_SHORT, ID, topology);
		
		Asset asset = Util.getAsset(ID_SHORT, ID);

		PlantAAS plantAAS = new PlantAAS(ID_SHORT, ID, asset, topologySM);

		Set<ISubmodel> smList = new HashSet<>();
		smList.add(topologySM);

		AASBundle bundle = new AASBundle(plantAAS, smList);
		return bundle;
		
	}
	
	private static TopologySubmodel buildTopologySubmodel(String aasIdShort, String aasId, SubmodelElementCollection topology) {
		TopologySubmodel tpSM = new TopologySubmodel(aasIdShort, aasId, topology);
		return tpSM;
		
	}
	
}
