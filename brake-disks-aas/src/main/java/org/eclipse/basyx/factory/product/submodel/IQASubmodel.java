package org.eclipse.basyx.factory.product.submodel;

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Interface for the QASubmodel
 * 
 * @author molina
 *
 */
public interface IQASubmodel extends ISubmodel {

	public ConnectedSubmodelElementCollection getQAData();
	
	public void addQAProperty(Property property); 
}
