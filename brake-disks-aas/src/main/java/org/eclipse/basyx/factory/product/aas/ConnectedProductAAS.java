/**
 * 
 */
package org.eclipse.basyx.factory.product.aas;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.factory.product.submodel.ConnectedProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.ConnectedQASubmodel;
import org.eclipse.basyx.factory.product.submodel.ProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.QASubmodel;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connected variant of the Product AAS
 * 
 * @author molina
 *
 */
public class ConnectedProductAAS extends ConnectedAssetAdministrationShell implements IProductAAS {

	public ConnectedProductAAS(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public ConnectedQASubmodel getQASubmodel() {
		Map<String, ISubmodel> submodels = getSubmodels();
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) submodels.get(getIdShort() + QASubmodel.ID_SHORT_SUFFIX);
		return new ConnectedQASubmodel(connectedSM.getProxy());
	}

	@Override
	public ConnectedProductSubmodel getProductSubmodel() {
		Map<String, ISubmodel> submodels = getSubmodels();
		ConnectedSubmodel connectedSM = (ConnectedSubmodel) submodels.get(getIdShort() + ProductSubmodel.ID_SHORT_SUFFIX);
		return new ConnectedProductSubmodel(connectedSM.getProxy());
	}

}
