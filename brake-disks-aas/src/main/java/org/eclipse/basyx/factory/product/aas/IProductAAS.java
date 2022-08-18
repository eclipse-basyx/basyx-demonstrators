package org.eclipse.basyx.factory.product.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.factory.product.submodel.IProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.IQASubmodel;

/**
 * Interface for the ProductAAS
 * 
 * @author molina
 *
 */
public interface IProductAAS extends IAssetAdministrationShell {

	public IQASubmodel getQASubmodel();
	
	public IProductSubmodel getProductSubmodel();
	
}
