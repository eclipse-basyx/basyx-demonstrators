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

import org.eclipse.basyx.factory.device.aas.storage.ConnectedStorageAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.ConnectedStorageContainerAAS;
import org.eclipse.basyx.factory.device.submodel.ConnectedProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.ConnectedContainerSubmodel;
import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultStorageServiceBuilder implements IStorageServicesBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultStorageServiceBuilder.class);
	
	/**
	 * Builds consumer for retrieve service
	 * Consumer takes one int as container index
	 * 
	 * @param storageId id of the storageAAS
	 * @return Consumer object for retrieve service
	 */
	@Override
	public Consumer<Object[]> buildRetrieveConsumer(String storageId) {
		Consumer<Object[]> invokable = (Consumer<Object[]>) o -> {
			
			logger.info("Storage '" + storageId + "': Starting retrieve service ...");
			
			ConnectedStorageAAS storage = 
					ConnectedStorageAAS.getConnectedStorageAAS(storageId);
			
			ConnectedProcessSubmodel pSM = storage.getProcessSubmodel();
			
			int index = (int) o[0];

			logger.info("Storage '" + storageId + "': Retrieving item from container '" + index + "'.");
			
			if(pSM.getCurrentProduct() != null) {
				throw new RuntimeException("Warehouse has already a product at transfer point.");
			}
			
			ConnectedStorageContainerAAS container = storage.getStorageSubmodel().getContainer(index);
			
			ConnectedProductAAS product = container.getContainerSubmodel().removeContainedProduct();
			
			if(product == null) {
				throw new RuntimeException("Nothing in specified container.");
			}
			
			pSM.setCurrentProduct(product);
			logger.info("Storage '" + storageId + "': Placed item at transfer point.");
			logger.info("Storage '" + storageId + "': Retrieve service completed.");
			
		};
		
		return invokable;
	}
	
	/**
	 * Builds consumer for store service
	 * Consumer takes one int as container index
	 * 
	 * @param storageId id of the storageAAS
	 * @return Consumer object for store service
	 */
	@Override
	public Consumer<Object[]> buildStoreConsumer(String storageId) {
		Consumer<Object[]> invokable = (Consumer<Object[]>) o -> {
			
			logger.info("Storage '" + storageId + "': Starting store service ...");
			
			ConnectedStorageAAS storage = 
					ConnectedStorageAAS.getConnectedStorageAAS(storageId);
			
			ConnectedProcessSubmodel pSM = storage.getProcessSubmodel();
			
			int index = (int) o[0];

			logger.info("Storage '" + storageId + "': Storing item in container '" + index + "'.");
			
			if(pSM.currentProductIsEmpty()) {
				throw new RuntimeException("Warehouse has no product at transfer point.");
			}
			
			
			
			ConnectedContainerSubmodel container = storage.getStorageSubmodel().getContainer(index).getContainerSubmodel();
			
			if(!container.isEmpty()) {
				throw new RuntimeException("Specified contiener is not empty.");
			}
			
			ConnectedProductAAS product = pSM.getCurrentProduct();
			
			container.putContainedProduct(product);
			
			pSM.setCurrentProduct(null);
			
			logger.info("Storage '" + storageId + "': Placed item in container.");
			logger.info("Storage '" + storageId + "': Store service completed.");
		};
		
		return invokable;
	}
}
