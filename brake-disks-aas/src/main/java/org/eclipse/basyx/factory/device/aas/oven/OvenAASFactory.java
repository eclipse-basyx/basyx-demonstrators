/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.oven;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAASFactory;
import org.eclipse.basyx.factory.device.aas.oven.servicebuilder.DefaultOvenServiceBuilder;
import org.eclipse.basyx.factory.device.aas.oven.servicebuilder.IOvenServiceBuilder;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.oven.OvenTempSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

/**
 * Factory for building a OvenAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class OvenAASFactory extends DeviceAASFactory {

	
	public static final String BURN_SERVICE_ID_SHORT = "burnService";
	
	public static AASBundle buildOvenAAS(String idShort, String id) {
		return buildOvenAAS(idShort, id, new DefaultOvenServiceBuilder());
	}

	public static AASBundle buildOvenAAS(String idShort, String id, IOvenServiceBuilder serviceBuilder) {
		ProcessSubmodel processSM = buildProcessSubmodel(idShort, id, buildParameters());
		ServiceSubmodel serviceSM = buildServiceSubmodel(idShort, id, buildServices());
		StatusSubmodel statusSM = buildStatusSubmodel(idShort, id);
		OvenTempSubmodel ovenTempSM = buildOvenTempSubmodel(idShort, id);
		
		Asset asset = Util.getAsset(idShort, id);
		OvenAAS ovenAAS = new OvenAAS(idShort, id, asset, ovenTempSM, processSM, serviceSM, statusSM);
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(processSM);
		smList.add(serviceSM);
		smList.add(statusSM);
		smList.add(ovenTempSM);

		serviceSM.addSubmodelElement(buildBurnOperation(serviceBuilder.buildBurn(id)));

		AASBundle bundle = new AASBundle(ovenAAS, smList);
		return bundle;
	}
	
	public static OvenTempSubmodel buildOvenTempSubmodel(String aasIdShort, String aasId) {
		Property currentTemp = new Property(0.0);
		currentTemp.setIdShort(OvenTempSubmodel.CURRENTTEMP_ID_SHORT);
		OvenTempSubmodel ovenTempSM = new OvenTempSubmodel(aasIdShort, aasId, currentTemp);
		return ovenTempSM;
	}
	
	
	private static SubmodelElementCollection buildParameters() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ProcessSubmodel.PARAMETERS_ID_SHORT);
		// TODO add parameters
		return collection;
	}
	
	private static SubmodelElementCollection buildServices() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ServiceSubmodel.SERVICES_ID_SHORT);
		Property serviceBurn = new Property(BURN_SERVICE_ID_SHORT, "Burn object using specified burncurve");
		collection.addSubmodelElement(serviceBurn);
		return collection;
	}
	
	private static Operation buildBurnOperation(Consumer<Object[]> invokable) {
		Operation op = new Operation(BURN_SERVICE_ID_SHORT);
		
		Property burnCurve = new Property("burnCurve", "");
		burnCurve.setKind(ModelingKind.TEMPLATE);
		OperationVariable burnCurveOpVar = new OperationVariable(burnCurve);
		
		op.setInputVariables(Arrays.asList(burnCurveOpVar));
		
		op.setInvokable(invokable);
		return op;
	}
}
