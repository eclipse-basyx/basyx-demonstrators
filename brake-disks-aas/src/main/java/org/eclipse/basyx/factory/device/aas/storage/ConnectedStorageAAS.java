/**
 * 
 */
package org.eclipse.basyx.factory.device.aas.storage;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.storage.ConnectedStorageSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.StorageSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the StorageAAS
 *
 */
public class ConnectedStorageAAS extends ConnectedDeviceAAS implements IStorageAAS {

	public ConnectedStorageAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedStorageSubmodel getStorageSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + StorageSubmodel.ID_SUFFIX));
		return new ConnectedStorageSubmodel(connectedSM.getProxy());
	}
	
	public static ConnectedStorageAAS getConnectedStorageAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedStorageAAS(proxy);
	}

}
