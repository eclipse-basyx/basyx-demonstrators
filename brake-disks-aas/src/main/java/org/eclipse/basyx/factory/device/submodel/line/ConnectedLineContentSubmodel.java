/**
 * 
 */
package org.eclipse.basyx.factory.device.submodel.line;

import org.eclipse.basyx.factory.device.aas.ConnectedDeviceAAS;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the LineContentSubmodel
 *
 */
public class ConnectedLineContentSubmodel extends ConnectedSubmodel implements ILineContentSubmodel{

	public ConnectedLineContentSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedDeviceAAS getDeviceA() {
		String devId = ((ConnectedReferenceElement) getSubmodelElement(LineContentSubmodel.DEVA_REF_ID_SHORT))
				.getValue()
				.getKeys()
				.get(0)
				.getValue();
		return ConnectedDeviceAAS.getConnectedDeviceAAS(devId);
	}

	@Override
	public ConnectedDeviceAAS getDeviceB() {
		String devId = ((ConnectedReferenceElement) getSubmodelElement(LineContentSubmodel.DEVB_REF_ID_SHORT))
				.getValue()
				.getKeys()
				.get(0)
				.getValue();
		return ConnectedDeviceAAS.getConnectedDeviceAAS(devId);
	}
	

}
