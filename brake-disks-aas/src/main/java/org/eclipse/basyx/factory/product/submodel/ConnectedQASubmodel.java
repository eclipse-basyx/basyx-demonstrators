/**
 * 
 */
package org.eclipse.basyx.factory.product.submodel;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected Variant of QASubmodel
 * 
 * @author molina
 *
 */
public class ConnectedQASubmodel extends ConnectedSubmodel implements IQASubmodel {

	public ConnectedQASubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedSubmodelElementCollection getQAData() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(QASubmodel.QADATA_SHORT_ID);
	}

	@Override
	public void addQAProperty(Property property) {
		ISubmodelElementCollection qaData = getQAData();
		qaData.addSubmodelElement(property);
	}

}
