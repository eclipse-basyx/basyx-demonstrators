/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.storage.storagecontainer;

import org.eclipse.basyx.factory.product.aas.IProductAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;

/**
 * Interface for the ContainerSubmodel
 * 
 * @author conradi
 *
 */
public interface IContainerSubmodel extends ISubmodel {

	/**
	 * Gets the currently contained Blank from the Container and removes its reference from it.
	 * Container is empty after this call.
	 * 
	 * 
	 * @return Contained BlankAAS; null if empty
	 */
	public IProductAAS removeContainedProduct();
	
	/**
	 * Puts given BlankAAS into container.
	 * Empties container if null is passed.
	 * Throws RuntimeException if container is not empty and new Blank is passed
	 * 
	 * @param product
	 */
	public void putContainedProduct(IProductAAS product);
	
}
