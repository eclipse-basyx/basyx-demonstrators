/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.eclipse.basyx.factory.product.aas.IProductAAS;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * ConnectedSubmodel for IProcessSubmodel
 * 
 * @author conradi
 *
 */
public class ConnectedProcessSubmodel extends ConnectedSubmodel implements IProcessSubmodel {

	public ConnectedProcessSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedSubmodelElementCollection getParameters() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(ProcessSubmodel.PARAMETERS_ID_SHORT);
	}

	@Override
	public ConnectedProperty getCurrentStep() {
		return (ConnectedProperty) getSubmodelElement(ProcessSubmodel.CURRENTSTEP_ID_SHORT);
	}

	@Override
	public ConnectedProductAAS getCurrentProduct() {
		IReference ref = getProductReferenceElement().getValue();
		
		if(currentProductIsEmpty()) {
			return null;
		}
		
		List<IKey> keys = ref.getKeys();
		
		String key =  keys.get(0).getValue();
		VABElementProxy proxy = Util.getAASProxy(key);
		
		return new ConnectedProductAAS(proxy);
	}

	@Override
	public void setCurrentProduct(IProductAAS product) {

		ConnectedReferenceElement refElem = getProductReferenceElement();
		
		if(product == null) {
			refElem.setValue(new Reference(new ArrayList<>()));
		} else {
			if(!currentProductIsEmpty()) {
				throw new RuntimeException("ProcessSubmodel " + getIdShort() + " already occupied with a product!");
			}
			refElem.setValue(product.getReference());
		}
	}
	
	public boolean currentProductIsEmpty() {
		ConnectedReferenceElement refElem = getProductReferenceElement();
		return refElem.getValue() == null || refElem.getValue().getKeys().size() == 0;
	}
	
	private ConnectedReferenceElement getProductReferenceElement() {
		return (ConnectedReferenceElement) getSubmodelElement(ProcessSubmodel.PRODUCT_ID_SHORT);
	}
	
}