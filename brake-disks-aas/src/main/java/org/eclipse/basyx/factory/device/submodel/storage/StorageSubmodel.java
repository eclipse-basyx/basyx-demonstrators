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

import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.IStorageContainerAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.StorageContainerAAS;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

/**
 * Contains a Map with all StorageContainers
 * 
 * @author conradi
 *
 */
public class StorageSubmodel extends Submodel implements IStorageSubmodel {

	
	public static final String ID_SUFFIX = "_storageSM";
	public static final String ID_SHORT_SUFFIX = "_storageSMIdShort";
	
	public static final String ID_CONTAINERS = "containersId";
	
	public StorageSubmodel(String aasIdShort, String aasId, List<StorageContainerAAS> containers) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		SubmodelElementCollection containerRefs = createSMCFromStorageContainers(containers);

		addSubmodelElement(containerRefs);
	}

	@Override
	public SubmodelElementCollection getContainers() {
		return (SubmodelElementCollection) getSubmodelElement(ID_CONTAINERS);
	}

	@Override
	public StorageContainerAAS getContainer(int index) {
		throw new RuntimeException("getContainer on local copy is not supported");
	}

	// TODO: make it more generic and put inside a helper class?
	public static SubmodelElementCollection createSMCFromStorageContainers(List<StorageContainerAAS> containers) {
		SubmodelElementCollection containerRefs = new SubmodelElementCollection(ID_CONTAINERS);
		containerRefs.setOrdered(true);
		
		for(StorageContainerAAS aas: containers) {
			Reference ref = (Reference) aas.getReference();
			ReferenceElement refElem = new ReferenceElement(aas.getIdShort() + "ref", ref);
			containerRefs.addSubmodelElement(refElem);
		}
		
		return containerRefs;
	}

	@Override
	public void addContainer(IStorageContainerAAS containerSm) {
		throw new RuntimeException("addContainer on local copy is not supported");
	}
	
}
