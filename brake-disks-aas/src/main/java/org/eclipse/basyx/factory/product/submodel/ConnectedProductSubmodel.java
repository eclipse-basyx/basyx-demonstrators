/**
 * 
 */
package org.eclipse.basyx.factory.product.submodel;

import java.util.List;

import org.eclipse.basyx.factory.blank.aas.ConnectedBlankAAS;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of Product Submodel
 * 
 * @author molina
 *
 */
public class ConnectedProductSubmodel extends ConnectedSubmodel implements IProductSubmodel {

	public ConnectedProductSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedProperty getCurrentStep() {
		return (ConnectedProperty) getSubmodelElement(ProductSubmodel.CURRENTSTEP_SHORT_ID);
	}

	@Override
	public ConnectedSubmodelElementCollection getSteps() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(ProductSubmodel.STEPS_SHORT_ID);

	}

	@Override
	public ConnectedBlankAAS getBlank() {
		
		IReference ref = getBlankReferenceElement().getValue();
		
		List<IKey> keys = ref.getKeys();
		
		if(keys.isEmpty()) {
			// Should never happen
			throw new RuntimeException("Product contains no blank!");
		}
		
		String key =  keys.get(0).getValue();
		VABElementProxy proxy = Util.getAASProxy(key);
		
		return new ConnectedBlankAAS(proxy);
	}

	private ConnectedReferenceElement getBlankReferenceElement() {
		return (ConnectedReferenceElement) getSubmodelElement(ProductSubmodel.BLANK_SHORT_ID);
	}
	
}
