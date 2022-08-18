package org.eclipse.basyx.factory.device.submodel.storage;

import java.util.Collection;

import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.ConnectedStorageContainerAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.IStorageContainerAAS;
import org.eclipse.basyx.factory.device.aas.storage.storagecontainer.StorageContainerAAS;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of the StorageSubmodel
 * 
 * @author molina
 *
 */
public class ConnectedStorageSubmodel extends ConnectedSubmodel implements IStorageSubmodel {

	public ConnectedStorageSubmodel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedSubmodelElementCollection getContainers() {
		return (ConnectedSubmodelElementCollection) getSubmodelElement(StorageSubmodel.ID_CONTAINERS);
	}

	@Override
	public ConnectedStorageContainerAAS getContainer(int index) {

		ConnectedSubmodelElementCollection containers = getContainers();
		
		Collection<ISubmodelElement> elements = containers.getSubmodelElements().values();
		for (ISubmodelElement cont : elements) {
			String key = ((ConnectedReferenceElement) cont).getValue().getKeys().get(0).getValue();
			if (key.equals(StorageContainerAAS.ID+index)) {
				VABElementProxy proxy = Util.getAASProxy(key);
				return new ConnectedStorageContainerAAS(proxy);
			}
		}
		throw new RuntimeException("No containers found with given index");
	}

	@Override
	public void addContainer(IStorageContainerAAS containerAAS) {

		ReferenceElement referenceElement = new ReferenceElement(containerAAS.getIdShort() + "ref", (Reference) containerAAS.getReference());
		
		ConnectedSubmodelElementCollection containers = getContainers();
		containers.addSubmodelElement(referenceElement);
		
	}
	

}
