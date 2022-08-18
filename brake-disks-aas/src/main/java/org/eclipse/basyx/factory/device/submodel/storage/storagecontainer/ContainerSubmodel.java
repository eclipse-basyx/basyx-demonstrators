/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.storage.storagecontainer;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.product.aas.IProductAAS;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

/**
 * Can contain a product
 * 
 * @author conradi
 *
 */
public class ContainerSubmodel extends Submodel implements IContainerSubmodel {
	
	public static final String CONTAINED_REF_ID = "containedProduct";
	public static final String ID_SUFFIX = "_containerSM";
	public static final String ID_SHORT_SUFFIX = "_containerSMIdShort";
	
	public ContainerSubmodel(String aasIdShort, String aasId, ReferenceElement containedProduct) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		containedProduct = containedProduct == null ? new ReferenceElement(CONTAINED_REF_ID, null) : containedProduct;
		
		containedProduct.setIdShort(CONTAINED_REF_ID);
		
		this.addSubmodelElement(containedProduct);
	}

	@Override
	public IProductAAS removeContainedProduct() {
		throw new RuntimeException("removeContainedProduct on local copy is not supported");
	}

	@Override
	public void putContainedProduct(IProductAAS product) {
		throw new RuntimeException("putContainedProduct on local copy is not supported");
		
	}

}