package org.eclipse.basyx.factory.product.submodel;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Contains data gathered by QualityAssurance
 * 
 * @author molina
 *
 */
public class QASubmodel extends Submodel implements IQASubmodel {

	public static final String ID_SUFFIX = "qaSMId";
	public static final String ID_SHORT_SUFFIX = "qaSMIdShort";
	
	public static final String QADATA_SHORT_ID = "qaData";
	
	public QASubmodel(String aasIdShort, String aasId, SubmodelElementCollection qaData) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		this.addSubmodelElement(qaData);
	}

	@Override
	public ConnectedSubmodelElementCollection getQAData() {
		throw new RuntimeException("getQAData not supported on local copy");
	}
	
	public void addQAProperty(Property property) {
		throw new RuntimeException("addQAProperty not supported on local copy");
	}

}