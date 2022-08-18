/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.storage.servicebuilder;

import java.util.function.Consumer;

public interface IStorageServicesBuilder {

	public Consumer<Object[]> buildRetrieveConsumer(String storageId);
	
	public Consumer<Object[]> buildStoreConsumer(String storageId);
	
}