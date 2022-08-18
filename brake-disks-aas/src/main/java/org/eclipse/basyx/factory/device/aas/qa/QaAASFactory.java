/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.qa;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAASFactory;
import org.eclipse.basyx.factory.device.aas.qa.servicebuilder.DefaultQaServiceBuilder;
import org.eclipse.basyx.factory.device.aas.qa.servicebuilder.IQaServiceBuilder;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

/**
 * Factory for building a QaAAS including its Submodels
 * 
 * @author molina
 *
 */
public class QaAASFactory extends DeviceAASFactory {

	
	public static final String ANALYSE_SERVICE_ID_SHORT = "analyseService";
	
	
	public static AASBundle buildQaAAS(String idShort, String id) {
		return buildQaAAS(idShort, id, new DefaultQaServiceBuilder());
	}

	public static AASBundle buildQaAAS(String idShort, String id, IQaServiceBuilder serviceBuilder) {
		ProcessSubmodel processSM = buildProcessSubmodel(idShort, id, buildParameters());
		ServiceSubmodel serviceSM = buildServiceSubmodel(idShort, id, buildServices());
		StatusSubmodel statusSM = buildStatusSubmodel(idShort, id);
		
		Asset asset = Util.getAsset(idShort, id);
		QaAAS qaAAS = new QaAAS(idShort, id, asset, processSM, serviceSM, statusSM);
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(processSM);
		smList.add(serviceSM);
		smList.add(statusSM);

		serviceSM.addSubmodelElement(buildAnalyseOperation(serviceBuilder.buildAnalyse(id)));

		AASBundle bundle = new AASBundle(qaAAS, smList);
		return bundle;
	}
	
	private static SubmodelElementCollection buildParameters() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ProcessSubmodel.PARAMETERS_ID_SHORT);
		collection.addSubmodelElement(new Property(ANALYSE_SERVICE_ID_SHORT, "Analyse current product on Process Submodel"));
		return collection;
	}
	
	private static SubmodelElementCollection buildServices() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ServiceSubmodel.SERVICES_ID_SHORT);
		Property serviceAnalyse = new Property(ANALYSE_SERVICE_ID_SHORT, "Analyse current product and store result in QASubmodel");
		collection.addSubmodelElement(serviceAnalyse);
		return collection;
	}
	
	private static Operation buildAnalyseOperation(Consumer<Object[]> invokable) {
		Operation op = new Operation(ANALYSE_SERVICE_ID_SHORT);
		op.setInvokable(invokable);
		return op;
	}
}
