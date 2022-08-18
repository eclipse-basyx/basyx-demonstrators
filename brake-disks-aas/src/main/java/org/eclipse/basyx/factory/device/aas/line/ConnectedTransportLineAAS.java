package org.eclipse.basyx.factory.device.aas.line;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.line.ConnectedLineContentSubmodel;
import org.eclipse.basyx.factory.device.submodel.line.LineContentSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected Variant of the TransportLineAAS
 * 
 * @author molina
 *
 */
public class ConnectedTransportLineAAS extends ConnectedDeviceAAS implements ITransportLineAAS{

	public ConnectedTransportLineAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedLineContentSubmodel getLineContentSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + LineContentSubmodel.ID_SUFFIX));
		return new ConnectedLineContentSubmodel(connectedSM.getProxy());
	}
	
	public static ConnectedTransportLineAAS getConnectedTransportLineAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedTransportLineAAS(proxy);
	}

}
