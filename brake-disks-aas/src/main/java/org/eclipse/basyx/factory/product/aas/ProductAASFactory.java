/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.product.aas;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.blank.aas.BlankAAS;
import org.eclipse.basyx.factory.product.submodel.ProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.QASubmodel;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Factory for building a ProductAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class ProductAASFactory {
	
	
	public static AASBundle buildProductAAS(Collection<ISubmodelElement> steps, int index, BlankAAS blank) {
		ProductSubmodel productSubmodel = buildProductSubmodel(steps, index, blank);
		QASubmodel qaSubmodel = buildQASubmodel(index);
		
		String id = getProductAASId(index);
		String idShort = getProductAASIdShort(index);
		
		ProductAAS productAAS = new ProductAAS(qaSubmodel, productSubmodel, new ModelUrn(id), idShort);
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(productSubmodel);
		smList.add(qaSubmodel);

		AASBundle bundle = new AASBundle(productAAS, smList);
		return bundle;
	}
	
	private static ProductSubmodel buildProductSubmodel(Collection<ISubmodelElement> steps, int index, BlankAAS blank) {
		Property currentStep = new Property(0);
		currentStep.setIdShort(ProductSubmodel.CURRENTSTEP_SHORT_ID);
		
		ReferenceElement blankRef = new ReferenceElement(ProductSubmodel.BLANK_SHORT_ID, (Reference) blank.getReference());
		
		ProductSubmodel prodSm = new ProductSubmodel(getProductAASIdShort(index), getProductAASId(index), currentStep, steps, blankRef);
		
		return prodSm;
	}
	
	private static QASubmodel buildQASubmodel(int index) {
		SubmodelElementCollection qaData = new SubmodelElementCollection(QASubmodel.QADATA_SHORT_ID);
		
		
		QASubmodel qaSm = new QASubmodel(getProductAASIdShort(index), getProductAASId(index), qaData);
		
		return qaSm;
	}
	
	public static String getProductAASId(int index) {
		return ProductAAS.PRODUCT_AAS_ID + index;
	}
	
	public static String getProductAASIdShort(int index) {
		return ProductAAS.PRODUCT_AAS_ID_SHORT + index;
	}
	
	
}
