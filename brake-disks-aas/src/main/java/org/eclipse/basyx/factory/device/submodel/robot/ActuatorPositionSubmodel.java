/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.robot;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Contains the current position of the actuator of the transport robot
 * 
 * @author conradi
 *
 */
public class ActuatorPositionSubmodel extends Submodel implements IActuatorPositionSubmodel {

	public static final String ID_SUFFIX = "_actuatorPositionSM";
	public static final String ID_SHORT_SUFFIX = "_actuatorPositionSMIdShort";
	
	public static final String CURRENTPOSITION_ID_SHORT = "currentPosition";
	
	public ActuatorPositionSubmodel(String aasIdShort, String aasId, Property currentPosition) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		this.addSubmodelElement(currentPosition);
	}

	public IProperty getCurrentPosition() {
		throw new RuntimeException("getCurrentPosition not supported in local copy.");
	}

}
