package org.eclipse.basyx.factory.product.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.factory.product.submodel.IProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.IQASubmodel;
import org.eclipse.basyx.factory.product.submodel.ProductSubmodel;
import org.eclipse.basyx.factory.product.submodel.QASubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Contains production steps and QA data for a specific product
 * 
 * @author molina
 *
 */
public class ProductAAS extends AssetAdministrationShell implements IProductAAS {
	
	private IIdentifier qaSMId;
	private IIdentifier productSMId;
	
	public static final String PRODUCT_AAS_ID = "productAAS";
	public static final String PRODUCT_AAS_ID_SHORT = "productAASIdShort";
		
	public ProductAAS(QASubmodel qaSubmodel, ProductSubmodel productSubmodel, IIdentifier id, String idShort) {
		super();
		setIdShort(idShort);
		setIdentification(id);
		this.addSubmodel(qaSubmodel);
		this.addSubmodel(productSubmodel);
		
		// Store Identifier to be able to find SM later
		this.qaSMId = qaSubmodel.getIdentification();
		this.productSMId = productSubmodel.getIdentification();
	}

	@Override
	public IQASubmodel getQASubmodel() {
		return (QASubmodel) getSubmodel(qaSMId);
	}

	@Override
	public IProductSubmodel getProductSubmodel() {
		return (ProductSubmodel) getSubmodel(productSMId);
	}

}
