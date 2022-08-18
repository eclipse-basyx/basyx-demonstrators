/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.plant.submodel;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

/**
 * The TopologySubmodel holds data about the Relationships between machines
 * 
 * @author conradi
 *
 */
public class TopologySubmodel extends Submodel implements ITopologySubmodel {
	
	
	public static final String ID_SUFFIX = "_topologySM";
	public static final String ID_SHORT_SUFFIX = "_topologySMIdShort";
	
	public static final String TOPOLOGY_COLLECTION_ID_SHORT = "topologyCollection";
	
	private String topologyId;
	
	public TopologySubmodel(String aasIdShort, String aasId, SubmodelElementCollection topology) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		this.addSubmodelElement(topology);
		
		this.topologyId = topology.getIdShort();
	}

	@Override
	public ISubmodelElementCollection getTopology() {
		return (SubmodelElementCollection) this.getSubmodelElement(topologyId);
	}

}
