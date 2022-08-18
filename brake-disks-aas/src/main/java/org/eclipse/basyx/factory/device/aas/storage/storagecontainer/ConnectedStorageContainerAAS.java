/**
 * 
 */
package org.eclipse.basyx.factory.device.aas.storage.storagecontainer;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.ConnectedContainerSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.ContainerSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the StorageContainerAAS
 *
 */
public class ConnectedStorageContainerAAS extends ConnectedAssetAdministrationShell implements IStorageContainerAAS {

	
	public ConnectedStorageContainerAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedContainerSubmodel getContainerSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + ContainerSubmodel.ID_SUFFIX));
		return new ConnectedContainerSubmodel(connectedSM.getProxy());
	}

}
