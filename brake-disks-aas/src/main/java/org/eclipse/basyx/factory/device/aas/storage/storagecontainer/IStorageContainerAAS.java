/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.storage.storagecontainer;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.IContainerSubmodel;

/**
 * Interface for the StorageContainerAAS
 * 
 * @author conradi
 *
 */
public interface IStorageContainerAAS extends IAssetAdministrationShell {

	public IContainerSubmodel getContainerSubmodel();
	
}
