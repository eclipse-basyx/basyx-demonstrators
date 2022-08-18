/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.blank.submodel;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * BlankSubmodel contain the type of the blank
 * e.g. "Ceramic", "Steel"
 * 
 * 
 * @author conradi
 *
 */
public class BlankSubmodel extends Submodel implements IBlankSubmodel {
	
	public static final String BLANK_TYPE_ID_SHORT = "blankType";
	
	public BlankSubmodel(Property blankType, IIdentifier id, String idShort) {
		super(idShort, id);
		addSubmodelElement(blankType);
	}

	@Override
	public Property getBlankType() {
		return (Property) getSubmodelElement(BLANK_TYPE_ID_SHORT);
	}

}
