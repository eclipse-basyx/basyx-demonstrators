package org.eclipse.basyx.factory.plant.submodel;

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of the Topology Submodel
 * 
 * @author molina
 *
 */
public class ConnectedTopologySubmodel extends ConnectedSubmodel implements ITopologySubmodel {

	public ConnectedTopologySubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedSubmodelElementCollection getTopology() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(TopologySubmodel.TOPOLOGY_COLLECTION_ID_SHORT);
	}

}
