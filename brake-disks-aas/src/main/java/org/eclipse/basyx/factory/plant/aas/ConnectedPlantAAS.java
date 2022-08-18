package org.eclipse.basyx.factory.plant.aas;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.plant.submodel.ConnectedTopologySubmodel;
import org.eclipse.basyx.factory.plant.submodel.TopologySubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of the PlantAAS
 * 
 * 
 * @author molina
 *
 */
public class ConnectedPlantAAS extends ConnectedAssetAdministrationShell implements IPlantAAS {

	public ConnectedPlantAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedTopologySubmodel getTopologySubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + TopologySubmodel.ID_SUFFIX));
		return new ConnectedTopologySubmodel(connectedSM.getProxy());
	}

}
