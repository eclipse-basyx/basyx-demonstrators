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

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.ContainerSubmodel;
import org.eclipse.basyx.factory.device.submodel.storage.storagecontainer.IContainerSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * StorageContianer can contain a blank and is part of the storage warehouse
 * 
 * @author conradi
 *
 */
public class StorageContainerAAS extends AssetAdministrationShell implements IStorageContainerAAS {

	private IIdentifier containerSMId;
	public static final String ID = "container";
	public static final String ID_SHORT = "containerIdShort";
	
	public StorageContainerAAS(String idShort, String id, Asset asset, ContainerSubmodel containerSM) {
		super(idShort, new ModelUrn(id), asset);
		addSubmodel(containerSM);
		
		this.containerSMId = containerSM.getIdentification();
	}

	@Override
	public IContainerSubmodel getContainerSubmodel() {
		return (ContainerSubmodel) getSubmodel(containerSMId);
	}
	

}
