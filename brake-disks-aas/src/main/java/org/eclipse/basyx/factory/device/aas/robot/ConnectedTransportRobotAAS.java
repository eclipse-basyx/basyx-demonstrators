package org.eclipse.basyx.factory.device.aas.robot;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.factory.device.submodel.robot.ActuatorPositionSubmodel;
import org.eclipse.basyx.factory.device.submodel.robot.ConnectedActuatorPositionSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 *
 * Connected variant of the TransportRobotAAS
 */
public class ConnectedTransportRobotAAS extends ConnectedDeviceAAS implements ITransportRobotAAS {

	public ConnectedTransportRobotAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedActuatorPositionSubmodel getActuatorPositionSubmodel() {
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) getSubmodel(new ModelUrn(getIdentification().getId() + ActuatorPositionSubmodel.ID_SUFFIX));
		return new ConnectedActuatorPositionSubmodel(connectedSM.getProxy());
	}
	
	public static ConnectedTransportRobotAAS getConnectedTransportRobotAAS(String id) {
		VABElementProxy proxy = Util.getAASProxy(id);
		return new ConnectedTransportRobotAAS(proxy);
	}

}
