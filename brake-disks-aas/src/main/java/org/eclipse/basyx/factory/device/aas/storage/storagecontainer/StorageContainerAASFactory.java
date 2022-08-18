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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.ContainerSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;

/**
 * Factory for building a StorageContainerAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class StorageContainerAASFactory {

	// TODO an idea is to make this method not-static. We have to always initialize the factory to create
	// new storage containers, thus enforcing an unique index to each container
	public static AASBundle buildStorageContainerAAS(int index) {
		
		String id = StorageContainerAAS.ID+index;
		String idShort = StorageContainerAAS.ID_SHORT+index;
		
		ContainerSubmodel cSM = buildContainerSubmodel(id, idShort);
		Asset asset = Util.getAsset(idShort, id);
		
		StorageContainerAAS scAAS = new StorageContainerAAS(idShort, id, asset, cSM);
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(cSM);

		AASBundle bundle = new AASBundle(scAAS, smList);
		return bundle;
	
	}
	
	private static ContainerSubmodel buildContainerSubmodel(String id, String idShort) {	
		ContainerSubmodel contSM = new ContainerSubmodel(idShort, id, null);
		return contSM;
	}
	
}
