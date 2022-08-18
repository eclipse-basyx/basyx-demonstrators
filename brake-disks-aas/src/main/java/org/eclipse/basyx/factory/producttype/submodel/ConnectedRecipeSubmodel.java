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

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedBlob;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected implementation of IRecipeSubmodel
 * 
 * @author conradi
 *
 */
public class ConnectedRecipeSubmodel extends ConnectedSubmodel implements IRecipeSubmodel {

	public ConnectedRecipeSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedBlob getExecutionPlan() {
		return (ConnectedBlob) getSubmodelElement(RecipeSubmodel.EXECUTION_PLAN_ID_SHORT);
	}

}
