package org.eclipse.basyx.factory.device.aas.qa;

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of the QaAAS
 *
 * @author molina, conradi
 *
 */
public class ConnectedQaAAS extends ConnectedDeviceAAS implements IQaAAS {

	public ConnectedQaAAS(VABElementProxy proxy) {
		super(proxy);
	}

	public static ConnectedQaAAS getConnectedQaAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedQaAAS(proxy);
	}

}
