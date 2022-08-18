/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.oven.servicebuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.basyx.factory.device.aas.oven.ConnectedOvenAAS;
import org.eclipse.basyx.factory.device.submodel.ConnectedProcessSubmodel;
import org.eclipse.basyx.factory.product.aas.ConnectedProductAAS;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultOvenServiceBuilder implements IOvenServiceBuilder {

	private static Logger logger = LoggerFactory.getLogger(DefaultOvenServiceBuilder.class);
	
	/**
	 * Builds consumer for burn service
	 * Consumer takes a string which represents the burncurve by comma separated values (e.g. "20,25,30")
	 * 
	 * @param ovenAASId id of the oventAAS
	 * @return Consumer object for burn service
	 */
	@Override
	public Consumer<Object[]> buildBurn(String ovenAASId) {
		Consumer<Object[]> invokable = (Consumer<Object[]>) o -> {
			
			logger.info("Oven '" + ovenAASId + "': Starting burn service ...");
			
			String burnCurveString = (String) o[0];
			String[] burnCurvePoints = burnCurveString.split(",");
			
			List<Integer> burnCurve = new ArrayList<>();
			
			for(String tempPoint: burnCurvePoints) {
				burnCurve.add(Integer.parseInt(tempPoint));
			}
			
			logger.info("Oven '" + ovenAASId + "': Read burncurve " + burnCurve);
			
			ConnectedOvenAAS oven = ConnectedOvenAAS.getConnectedOvenAAS(ovenAASId);
			ConnectedProcessSubmodel ovenProcessSM = oven.getProcessSubmodel();
			ConnectedProperty tempProp = oven.getOvenTempSubmodel().getCurrentTemp();
			
			ConnectedProductAAS product = ovenProcessSM.getCurrentProduct();
			
			// Check if any product is inside
			if(ovenProcessSM.currentProductIsEmpty()) {
				throw new RuntimeException("No blank found inside oven. Can't execute burn operation");
			}
			
			List<Integer> actualBurnCurve = new ArrayList<>();
			Random rand = new Random();
			
			// Starting mocked burn process
			ovenProcessSM.getCurrentStep().setValue("Burning");
			
			for(int tempPoint: burnCurve) {
				int tempDeviation = -2 + rand.nextInt(5);
				int actualTemp = tempPoint + tempDeviation;
				actualBurnCurve.add(actualTemp);
				tempProp.setValue(actualTemp);
				logger.info("Oven '" + ovenAASId + "': Current oven temperature " + actualTemp);
			}
			
			ovenProcessSM.getCurrentStep().setValue("idle");
			tempProp.setValue(20);
			logger.info("Oven '" + ovenAASId + "': Burn service completed.");
			
			// Add burned step to Product
			Property stepBurned = new Property("burned", "burned");
			product.getProductSubmodel().getSteps().addSubmodelElement(stepBurned);
			
			// Add actual burncurve to Product
			Property acutalBurnCurveQA = new Property("actualBurnCurve", actualBurnCurve.toString());
			product.getQASubmodel().addQAProperty(acutalBurnCurveQA);
		};
		
		return invokable;
	}

}
