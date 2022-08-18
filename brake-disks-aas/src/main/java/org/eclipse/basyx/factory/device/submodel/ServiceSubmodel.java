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
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

/**
 * ServiceSubmodel contains all services a machine can perform
 * 
 * @author conradi
 *
 */
public class ServiceSubmodel extends Submodel implements IServiceSubmodel {

	public static final String ID_SUFFIX = "_serviceSM";
	public static final String ID_SHORT_SUFFIX = "_serviceSMIdShort";
	
	public static final String SERVICES_ID_SHORT = "services";
	
	public ServiceSubmodel(String aasIdShort, String aasId, SubmodelElementCollection services) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		this.addSubmodelElement(services);
	}

	@Override
	public ISubmodelElementCollection getServices() {
		throw new RuntimeException("getServices on local copy is not supported");
	}

	@Override
	public void invokeService(String id, Object... params) {
		throw new RuntimeException("invokeService on local copy is not supported");
	}
}