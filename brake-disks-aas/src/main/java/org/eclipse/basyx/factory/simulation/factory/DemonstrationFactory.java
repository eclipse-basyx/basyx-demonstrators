/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.factory.blank.aas.BlankAAS;
import org.eclipse.basyx.factory.blank.aas.BlankAASFactory;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.device.aas.storage.ConnectedStorageAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.StorageContainerAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.StorageContainerAASFactory;
import org.eclipse.basyx.factory.device.submodel.storage.ConnectedStorageSubmodel;
import org.eclipse.basyx.factory.product.aas.ProductAAS;
import org.eclipse.basyx.factory.product.aas.ProductAASFactory;
import org.eclipse.basyx.factory.simulation.Main;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;

public abstract class DemonstrationFactory {

	private IAASAggregator aggregator;
	
	public DemonstrationFactory(IAASAggregator aggregator) {
		this.aggregator = aggregator;
	}
	
	public abstract List<IDeviceAAS> buildScenario();

	/**
	 * Pushes an AASBundle to the AASServer and registers it
	 * 
	 * @param bundle
	 */
	protected void pushBundle(AASBundle bundle) {
		AssetAdministrationShell aas = (AssetAdministrationShell) bundle.getAAS();
		aggregator.createAAS(aas);
		MultiSubmodelProvider smProvider = (MultiSubmodelProvider) aggregator.getAASProvider(aas.getIdentification());
		AASRegistryProxy registry = new AASRegistryProxy(Main.REGISTRY_URL);
		registry.register(new AASDescriptor(aas, Main.SERVER_URL + "/" + aas.getIdentification().getId() + "/aas"));
		
		for (ISubmodel sm : bundle.getSubmodels()) {
			smProvider.addSubmodel(new SubmodelProvider((Submodel) sm));
			registry.register(aas.getIdentification(), new SubmodelDescriptor(sm, Main.SERVER_URL + "/" + aas.getIdentification().getId() + "/aas/submodels/" + sm.getIdShort() + "/submodel"));
		}
	}
	
	/**
	 * Builds a Relationship for the topology
	 * 
	 * @param idShort idShort of the new Relationship
	 * @param from the AAS from which the Relation is going
	 * @param to the AAS to which the Relation is going
	 * @return
	 */
	protected RelationshipElement getRelationship(String idShort, AASBundle from, AASBundle to) {
		IAssetAdministrationShell fromAAS = from.getAAS();
		IAssetAdministrationShell toAAS = to.getAAS();
		return new RelationshipElement(idShort, fromAAS.getReference(), toAAS.getReference());
	}
	
	protected void buildAndPushFilledStorageContainers(int amount, String storageAASId) {
		ConnectedStorageAAS storage = new ConnectedStorageAAS(Util.getAASProxy(storageAASId));
		ConnectedStorageSubmodel storageSm = storage.getStorageSubmodel();
		int startIndex = storageSm.getContainers().getSubmodelElements().size();
		
		for(int i = startIndex; i < startIndex + amount; i++) {
			AASBundle storageContainer = StorageContainerAASFactory.buildStorageContainerAAS(i);
			storageSm.addContainer((StorageContainerAAS) storageContainer.getAAS());
			pushBundle(storageContainer);
			
			ProductAAS product = buildAndPushProduct(i);
			storageSm.getContainer(i).getContainerSubmodel().putContainedProduct(product);
		}
	}
	
	protected void buildAndPushEmptyStorageContainers(int amount, String storageAASId) {
		ConnectedStorageAAS storage = new ConnectedStorageAAS(Util.getAASProxy(storageAASId));
		ConnectedStorageSubmodel storageSm = storage.getStorageSubmodel();
		int startIndex = storageSm.getContainers().getSubmodelElements().size();
		for(int i = startIndex; i < startIndex + amount; i++) {
			AASBundle storageContainer = StorageContainerAASFactory.buildStorageContainerAAS(startIndex + i);
			storageSm.addContainer((StorageContainerAAS) storageContainer.getAAS());
			pushBundle(storageContainer);
		}
	}
	
	protected ProductAAS buildAndPushProduct(int index) {
		BlankAAS blank = buildBlank(index, "steel");
		AASBundle product = ProductAASFactory.buildProductAAS(new ArrayList<>(), index, blank);
		pushBundle(product);
		return (ProductAAS) product.getAAS();
	}
	
	protected BlankAAS buildBlank(int index, String material) {
		BlankAASFactory factory = new BlankAASFactory();
		AASBundle blank = factory.buildBlankAAS(material, index);
		pushBundle(blank);
		return (BlankAAS) blank.getAAS();
	}
	
	public abstract IExecutionEngine getEngine(List<IDeviceAAS> devices);
}
