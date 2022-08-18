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

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.factory.device.submodel.IProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.IServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.IStatusSubmodel;

/**
 * Interface for the DeviceAAS
 * 
 * @author conradi
 *
 */
public interface IDeviceAAS extends IAssetAdministrationShell {

	public IProcessSubmodel getProcessSubmodel();
	
	public IServiceSubmodel getServiceSubmodel();
	
	public IStatusSubmodel getStatusSubmodel();
	
}
