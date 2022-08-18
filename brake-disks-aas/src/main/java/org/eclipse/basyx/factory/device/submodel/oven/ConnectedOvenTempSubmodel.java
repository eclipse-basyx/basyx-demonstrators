package org.eclipse.basyx.factory.device.submodel.oven;

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the OvenTempSubmodel
 *
 */
public class ConnectedOvenTempSubmodel extends ConnectedSubmodel implements IOvenTempSubmodel {

	public ConnectedOvenTempSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedProperty getCurrentTemp() {
		return (ConnectedProperty) getSubmodelElement(OvenTempSubmodel.CURRENTTEMP_ID_SHORT);
	}

}
