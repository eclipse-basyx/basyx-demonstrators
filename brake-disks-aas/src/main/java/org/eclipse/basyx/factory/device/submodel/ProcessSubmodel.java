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

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.product.aas.IProductAAS;
import org.eclipse.basyx.factory.product.aas.ProductAAS;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Process Submodel contains the current step of the
 * working process of the machine and its parameters
 * 
 * @author conradi
 *
 */
public class ProcessSubmodel extends Submodel implements IProcessSubmodel {

	public static final String ID_SUFFIX = "_processSM";
	public static final String ID_SHORT_SUFFIX = "_processSMIdShort";
	
	public static final String CURRENTSTEP_ID_SHORT = "currentStep";
	public static final String PARAMETERS_ID_SHORT = "parameters";
	public static final String PRODUCT_ID_SHORT = "product";
	
	
	public ProcessSubmodel(String aasIdShort, String aasId, SubmodelElementCollection parameters, Property currentStep, ReferenceElement product) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		product = product == null ? new ReferenceElement(PRODUCT_ID_SHORT, null) : product;
		
		addSubmodelElement(parameters);
		addSubmodelElement(currentStep);
		addSubmodelElement(product);
	}

	@Override
	public SubmodelElementCollection getParameters() {
		throw new RuntimeException("getParameters on local copy is not supported");
	}

	@Override
	public Property getCurrentStep() {
		throw new RuntimeException("getCurrentStep on local copy is not supported");
	}
	
	public ProductAAS getCurrentProduct() {
		throw new RuntimeException("getCurrentProduct on local copy is not supported");
	}

	@Override
	public void setCurrentProduct(IProductAAS product) {
		throw new RuntimeException("setCurrentProduct on local copy is not supported");
	}

}
