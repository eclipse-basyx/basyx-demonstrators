package org.eclipse.basyx.factory.product.submodel;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.blank.aas.IBlankAAS;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Contains the work steps taken on the product
 * 
 * @author molina
 *
 */
public class ProductSubmodel extends Submodel implements IProductSubmodel {
	
	public static final String ID_SUFFIX = "ProductSM";
	public static final String ID_SHORT_SUFFIX = "ProductSMIdShort";

	public static final String CURRENTSTEP_SHORT_ID = "currentStep";
	public static final String STEPS_SHORT_ID = "steps";
	public static final String BLANK_SHORT_ID = "blank";
	
	
	private String currentStepId;
	private String stepsId;
	
	public ProductSubmodel(String aasIdShort, String aasId, Property currentStep, Collection<ISubmodelElement> steps, ReferenceElement blank) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));
		
		if(blank == null) {
			throw new RuntimeException("ProductSubmodel needs a BlankAAS reference.");
		}
		
		this.addSubmodelElement(blank);
		this.addSubmodelElement(currentStep);
		SubmodelElementCollection stepSMC = new SubmodelElementCollection(steps, true, true);
		stepSMC.setIdShort(STEPS_SHORT_ID);
		this.addSubmodelElement(stepSMC);
		this.currentStepId = currentStep.getIdShort();
		this.stepsId = "steps";
	}
	
	public ProductSubmodel(Property currentStep, SubmodelElementCollection steps) {
		super();
		this.addSubmodelElement(currentStep);
		this.addSubmodelElement(steps);
		this.currentStepId = currentStep.getIdShort();
		this.stepsId = steps.getIdShort();
	}

	@Override
	public Property getCurrentStep() {
		return (Property) getSubmodelElement(currentStepId);
	}

	@Override
	public SubmodelElementCollection getSteps() {
		return (SubmodelElementCollection) getSubmodelElement(stepsId);
	}

	@Override
	public IBlankAAS getBlank() {
		throw new RuntimeException("getBlank on local copy is not supported");
	}

}
