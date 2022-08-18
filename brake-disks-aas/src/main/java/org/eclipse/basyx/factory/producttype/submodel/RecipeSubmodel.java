/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.producttype.submodel;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;

/**
 * Local implementation of IRecipeSubmodel
 * 
 * @author conradi
 *
 */
public class RecipeSubmodel extends Submodel implements IRecipeSubmodel {

	public static final String ID_SUFFIX = "_recipeSM";
	public static final String ID_SHORT_SUFFIX = "_recipeSMIdShort";
	
	public static final String EXECUTION_PLAN_ID_SHORT = "executionPlan";
	
	
	public RecipeSubmodel(String aasIdShort, String aasId, Blob executionPlan) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		if(!executionPlan.getIdShort().equals(EXECUTION_PLAN_ID_SHORT)) {
			throw new RuntimeException("ExecutionPlan Blob needs to have '" + EXECUTION_PLAN_ID_SHORT + "' as idShort.");
		}
		
		addSubmodelElement(executionPlan);
	}
	
	public Blob getExecutionPlan() {
		throw new RuntimeException("getExecutionPlan on local copy is not supported");
	}
}
