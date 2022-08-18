/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.qa.servicebuilder;

import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.basyx.factory.device.aas.qa.ConnectedQaAAS;
import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default QA Service Builder
 * 
 * @author molina, conradi
 *
 */
public class DefaultQaServiceBuilder implements IQaServiceBuilder {

	private static Logger logger = LoggerFactory.getLogger(DefaultQaServiceBuilder.class);
	
	public static final String QARESULTPROP = "QARESULT";
	
	/**
	 * Builds consumer for analyse service
	 * Consumer adds the ending QA Result to the product's QA Data
	 * 
	 * @return Consumer object for analyse service
	 */
	@Override
	public Consumer<Object[]> buildAnalyse(String qaAASId) {
		Consumer<Object[]> invokable = (Consumer<Object[]>) o -> {
			ConnectedQaAAS qa = ConnectedQaAAS.getConnectedQaAAS(qaAASId);
			ConnectedProductAAS product = qa.getProcessSubmodel().getCurrentProduct();
			ConnectedSubmodelElementCollection qaData = product.getQASubmodel().getQAData();
			
			// Here the analysis of the part would take place. In this demonstration the outcome is just random.
			
			Random rand = new Random();
			int qaOutcome = rand.nextInt(3);
			
			switch (qaOutcome) {
			case 0:
				qaData.addSubmodelElement(new Property(QARESULTPROP, "APPROVED"));
				logger.info(qaAASId + ": Product " + product.getIdentification().getId() + " approved.");
				break;
			case 1:
				qaData.addSubmodelElement(new Property(QARESULTPROP, "REJECTED"));
				logger.info(qaAASId + ": Product " + product.getIdentification().getId() + " rejected.");
				break;
			case 2:
				qaData.addSubmodelElement(new Property(QARESULTPROP, "INCONCLUSIVE"));
				logger.info(qaAASId + ": Product " + product.getIdentification().getId() + " result inconclusive.");
				break;

			default:
				break;
			}
			
			
		};
		
		return invokable;
	}

}
