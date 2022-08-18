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

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;

/**
 * RecipeSubmodel contains the SubmodelElement holding the recipe
 * 
 * @author conradi
 *
 */
public interface IRecipeSubmodel extends ISubmodel {

	public IBlob getExecutionPlan();
	
}
