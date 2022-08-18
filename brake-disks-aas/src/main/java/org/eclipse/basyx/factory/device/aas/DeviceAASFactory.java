/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas;

import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Factory for building a DeviceAAS including its Submodels
 * 
 * @author conradi
 *
 */
public abstract class DeviceAASFactory {
	
	protected static ProcessSubmodel buildProcessSubmodel(String aasIdShort, String aasId, SubmodelElementCollection parameters) {
		Property currentStep = new Property(ProcessSubmodel.CURRENTSTEP_ID_SHORT, "idle");
		ProcessSubmodel pSM = new ProcessSubmodel(aasIdShort, aasId, parameters, currentStep, null);
		return pSM;
	}
	
	protected static ServiceSubmodel buildServiceSubmodel(String aasIdShort, String aasId, SubmodelElementCollection services) {
		ServiceSubmodel sSM = new ServiceSubmodel(aasIdShort, aasId, services);
		return sSM;
	}
	
	protected static StatusSubmodel buildStatusSubmodel(String aasIdShort, String aasId) {
		Property status = new Property(StatusSubmodel.STATUS_ID_SHORT, "offline");
		StatusSubmodel statusSM = new StatusSubmodel(aasIdShort, aasId, status);
		return statusSM;
	}
	
}
