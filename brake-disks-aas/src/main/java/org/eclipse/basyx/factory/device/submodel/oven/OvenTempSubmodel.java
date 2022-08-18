/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.oven;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Contains the current temperature of the oven
 * 
 * @author conradi
 *
 */
public class OvenTempSubmodel extends Submodel implements IOvenTempSubmodel {

	public static final String ID_SUFFIX = "_ovenTempSM";
	public static final String ID_SHORT_SUFFIX = "_ovenTempSMIdShort";
	
	public static final String CURRENTTEMP_ID_SHORT = "currentTemp";
	
	public String currentTempId;
	
	public OvenTempSubmodel(String aasIdShort, String aasId, Property currentTemp) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		this.addSubmodelElement(currentTemp);
		this.currentTempId = currentTemp.getIdShort();
	}

	public IProperty getCurrentTemp() {
		return (Property) this.getSubmodelElement(currentTempId);
	}
	
}
