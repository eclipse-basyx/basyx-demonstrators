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
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * StatusSubmodel contains the current status of the machine
 * e.g. "offline", "idle", "busy", "faulty"
 * 
 * @author conradi
 *
 */
public class StatusSubmodel extends Submodel implements IStatusSubmodel {

	public static final String ID_SUFFIX = "_statusSM";
	public static final String ID_SHORT_SUFFIX = "_statusSMIdShort";
	
	public static final String STATUS_ID_SHORT = "status";

	
	public StatusSubmodel(String aasIdShort, String aasId, Property status) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		addSubmodelElement(status);
	}

	@Override
	public IProperty getStatus() {
		throw new RuntimeException("getStatus on local copy is not supported");
	}

}
