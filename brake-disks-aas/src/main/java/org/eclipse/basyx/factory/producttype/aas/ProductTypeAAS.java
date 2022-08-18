/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.producttype.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.producttype.submodel.IRecipeSubmodel;
import org.eclipse.basyx.factory.producttype.submodel.RecipeSubmodel;

/**
 * Local implementation of IProductTypeAAS
 * 
 * @author conradi
 *
 */
public class ProductTypeAAS extends AssetAdministrationShell implements IProductTypeAAS {
	
	public static final String ID_PREFIX = "recipe_";
	
	public ProductTypeAAS(String aasIdShort, String aasId, Asset asset, RecipeSubmodel recipeSm) {
		super(aasIdShort, new ModelUrn(aasId), asset);
		
		if(!recipeSm.getIdShort().equals(aasIdShort + RecipeSubmodel.ID_SHORT_SUFFIX)) {
			throw new RuntimeException("RecipeSubmodel needs to have '" + aasIdShort + RecipeSubmodel.ID_SHORT_SUFFIX + "' as idShort.");
		}
		if(!recipeSm.getIdentification().getId().equals(aasId + RecipeSubmodel.ID_SUFFIX)) {
			throw new RuntimeException("RecipeSubmodel needs to have '" + aasId + RecipeSubmodel.ID_SUFFIX + "' as id.");
		}
		
		addSubmodel(recipeSm);
		
	}

	@Override
	public IRecipeSubmodel getRecepieSubmodel() {
		throw new RuntimeException("getRecepieSubmodel on local copy is not supported");
	}

}
