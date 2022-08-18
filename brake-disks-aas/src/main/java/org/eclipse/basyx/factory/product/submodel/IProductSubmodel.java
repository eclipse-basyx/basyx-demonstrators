package org.eclipse.basyx.factory.product.submodel;

import org.eclipse.basyx.factory.blank.aas.IBlankAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;

/**
 * Interface for ProductSubmodel
 * 
 * @author molina
 *
 */
public interface IProductSubmodel extends ISubmodel {
	
	public IProperty getCurrentStep();
	
	public ISubmodelElementCollection getSteps();
	
	public IBlankAAS getBlank();
	
}
