package org.eclipse.basyx.factory.device.submodel.robot;

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 *
 * Connected variant of the ActuatorPositionSubmodel
 *
 */

public class ConnectedActuatorPositionSubmodel extends ConnectedSubmodel implements IActuatorPositionSubmodel {

	public ConnectedActuatorPositionSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedProperty getCurrentPosition() {
		return (ConnectedProperty) getSubmodelElement(ActuatorPositionSubmodel.CURRENTPOSITION_ID_SHORT);
	}

}
