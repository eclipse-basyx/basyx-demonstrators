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

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;

/**
 * Interface for the ActuatorPositionSubmodel
 * CurrentPosition Property contains String with ID of DeviceAAS the arm is currently at.
 * String is empty if arm is currently in a position in between devices.
 * 
 * @author conradi
 *
 */
public interface IActuatorPositionSubmodel extends ISubmodel {

	public IProperty getCurrentPosition();
	
}