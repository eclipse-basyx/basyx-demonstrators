/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.storage;

import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.IStorageContainerAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;

/**
 * Interface for the StroageSubmodel
 * 
 * @author conradi
 *
 */
public interface IStorageSubmodel extends ISubmodel {
	
	public ISubmodelElementCollection getContainers();
	
	public IStorageContainerAAS getContainer(int index);
	
	public void addContainer(IStorageContainerAAS containerSm);

}
