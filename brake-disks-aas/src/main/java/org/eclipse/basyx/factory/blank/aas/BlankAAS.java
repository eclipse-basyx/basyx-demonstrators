/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.blank.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.blank.submodel.BlankSubmodel;
import org.eclipse.basyx.factory.blank.submodel.IBlankSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Represents the blank
 * 
 * @author conradi
 *
 */
public class BlankAAS extends AssetAdministrationShell implements IBlankAAS {
	
	public static final String ID_PREFIX = "blankID";
	public static final String ID_SHORT_PREFIX = "blankIdShort";
	
	private IIdentifier blankSMId;
		
	public BlankAAS(String idShort, IIdentifier identifier, Asset asset, BlankSubmodel blankSM) {
		super(idShort, identifier, asset);
		addSubmodel(blankSM);
		
		this.blankSMId = blankSM.getIdentification();
	}


	@Override
	public IBlankSubmodel getBlankSubmodel() {
		return (BlankSubmodel) getSubmodel(blankSMId);
	}

}
