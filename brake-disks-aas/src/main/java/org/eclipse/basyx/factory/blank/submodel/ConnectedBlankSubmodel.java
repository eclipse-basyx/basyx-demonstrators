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

import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * ConnectedSubmodel for IBlankSubmodel
 * 
 * @author conradi
 *
 */
public class ConnectedBlankSubmodel extends ConnectedSubmodel implements IBlankSubmodel {

	public ConnectedBlankSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedProperty getBlankType() {
		return (ConnectedProperty) getSubmodelElement(BlankSubmodel.BLANK_TYPE_ID_SHORT);
	}
	
}
