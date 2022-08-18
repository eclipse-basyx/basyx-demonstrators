/**
 * 
 */
package org.eclipse.basyx.factory.device.submodel.storage.storagecontainer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.eclipse.basyx.factory.product.aas.IProductAAS;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * @author molina
 * 
 * Connected variant of the ContainerSubmodel
 *
 */
public class ConnectedContainerSubmodel extends ConnectedSubmodel implements IContainerSubmodel {
	

	public ConnectedContainerSubmodel(VABElementProxy proxy) {
		super(proxy);
	}
	

	@Override
	public ConnectedProductAAS removeContainedProduct() {
		
		IReference ref = getProductReferenceElement().getValue();
		
		if(isEmpty()) {
			return null;
		}
		
		List<IKey> keys = ref.getKeys();
		
		String key = keys.get(0).getValue();
		VABElementProxy proxy = Util.getAASProxy(key);
		
		putContainedProduct(null);
		
		return new ConnectedProductAAS(proxy);
	}
	

	@Override
	public void putContainedProduct(IProductAAS product) {
		
		ConnectedReferenceElement refElem = getProductReferenceElement();
		
		if(product == null) {
			refElem.setValue(new Reference(new ArrayList<>()));
		} else {
			if(!isEmpty()) {
				throw new RuntimeException("Container " + getIdShort() + " is not empty!");
			}
			refElem.setValue(product.getReference());
		}
	}
	
	public boolean isEmpty() {
		ConnectedReferenceElement refElem = getProductReferenceElement();
		return refElem.getValue() == null || refElem.getValue().getKeys().size() == 0;
	}
	
	
	private ConnectedReferenceElement getProductReferenceElement() {
		return (ConnectedReferenceElement) getSubmodelElement(ContainerSubmodel.CONTAINED_REF_ID);
	}

}
