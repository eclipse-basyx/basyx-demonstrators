/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.storage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAASFactory;
import org.eclipse.basyx.factory.device.aas.storage.servicebuilder.DefaultStorageServiceBuilder;
import org.eclipse.basyx.factory.device.aas.storage.servicebuilder.IStorageServicesBuilder;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.StorageContainerAAS;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.StorageSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

/**
 * Factory for building a StorageAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class StorageAASFactory extends DeviceAASFactory {
	
	
	public static final String ITEM_RETRIEVE_ID_SHORT = "retrieve"; 
	public static final String ITEM_STORE_ID_SHORT = "store"; 

	public static AASBundle buildStorageAAS(String idShort, String id, List<StorageContainerAAS> storageContainers) {
		return buildStorageAAS(idShort, id, storageContainers, new DefaultStorageServiceBuilder());
	}
	
	public static AASBundle buildStorageAAS(String idShort, String id, List<StorageContainerAAS> storageContainers, IStorageServicesBuilder serviceBuilder) {
		ProcessSubmodel processSM = buildProcessSubmodel(idShort, id, buildParameters());
		ServiceSubmodel serviceSM = buildServiceSubmodel(idShort, id, buildServices());
		StatusSubmodel statusSM = buildStatusSubmodel(idShort, id);
		StorageSubmodel storageSM = new StorageSubmodel(idShort, id, storageContainers);
		
		serviceSM.addSubmodelElement(buildRetrieveOperation(serviceBuilder.buildRetrieveConsumer(id)));
		serviceSM.addSubmodelElement(buildStoreOperation(serviceBuilder.buildStoreConsumer(id)));
		Asset asset = Util.getAsset(idShort, id);
		
		StorageAAS storageAAS = new StorageAAS(idShort, id, asset, storageSM, processSM, serviceSM, statusSM);
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(processSM);
		smList.add(serviceSM);
		smList.add(statusSM);
		smList.add(storageSM);
		
		AASBundle bundle = new AASBundle(storageAAS, smList);
		return bundle;
	}
	
	private static SubmodelElementCollection buildParameters() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ProcessSubmodel.PARAMETERS_ID_SHORT);
		// TODO add parameters
		return collection;
	}
	
	private static SubmodelElementCollection buildServices() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ServiceSubmodel.SERVICES_ID_SHORT);
		Property serviceStore = new Property(ITEM_STORE_ID_SHORT, "Store item in warehouse");
		collection.addSubmodelElement(serviceStore);
		Property serviceRetrieve = new Property(ITEM_RETRIEVE_ID_SHORT, "Retrieve item from warehouse");
		collection.addSubmodelElement(serviceRetrieve);
		return collection;
	}
	
	
	private static Operation buildRetrieveOperation(Consumer<Object[]> invokable) {
		Operation op = new Operation(ITEM_RETRIEVE_ID_SHORT);
		Property indexProperty = new Property("index", 0);
		indexProperty.setKind(ModelingKind.TEMPLATE);
		op.setInputVariables(Arrays.asList(new OperationVariable(indexProperty)));
		
		op.setInvokable(invokable);
		return op;
	}
	
	
	private static Operation buildStoreOperation(Consumer<Object[]> invokable) {
		Operation op = new Operation(ITEM_STORE_ID_SHORT);
		Property indexProperty = new Property("index", 0);
		indexProperty.setKind(ModelingKind.TEMPLATE);
		op.setInputVariables(Arrays.asList(new OperationVariable(indexProperty)));		
		
		op.setInvokable(invokable);
		return op;
	}
	
}
