/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.line;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAASFactory;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.device.aas.line.servicebuilder.DefaultTransportLineServiceBuilder;
import org.eclipse.basyx.factory.device.aas.line.servicebuilder.ITransportLineServiceBuilder;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.line.LineContentSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

/**
 * Factory for building a TransportLineAAS including its Submodels
 * 
 * @author conradi, molina
 *
 */
public class TransportLineAASFactory extends DeviceAASFactory {

	
	public static final String TRANSPORTATOB_SERVICE_ID_SHORT = "transportAtoBService";
	public static final String TRANSPORTBTOA_SERVICE_ID_SHORT = "transportBtoAService";
	
	public static AASBundle buildTransportLineAAS(String idShort, String id, IDeviceAAS devA, IDeviceAAS devB) {
		return buildTransportLineAAS(idShort, id, new DefaultTransportLineServiceBuilder(), devA, devB);
	}

	public static AASBundle buildTransportLineAAS(String idShort, String id, ITransportLineServiceBuilder serviceBuilder, IDeviceAAS devA, IDeviceAAS devB) {
		ProcessSubmodel processSM = buildProcessSubmodel(idShort, id, buildParameters());
		ServiceSubmodel serviceSM = buildServiceSubmodel(idShort, id, buildServices());
		StatusSubmodel statusSM = buildStatusSubmodel(idShort, id);
		LineContentSubmodel lineContentSM = buildLineContentSubmodel(idShort, id, devA, devB);
		
		Asset asset = Util.getAsset(idShort, id);
		TransportLineAAS transportLineAAS = new TransportLineAAS(idShort, id, asset, lineContentSM, processSM, serviceSM, statusSM);
		
		serviceSM.addSubmodelElement(buildTransportAtoB(serviceBuilder.buildTransportAtoB(id)));
		serviceSM.addSubmodelElement(buildTransportBtoA(serviceBuilder.buildTransportBtoA(id)));

		Set<ISubmodel> smList = new HashSet<>();
		smList.add(processSM);
		smList.add(serviceSM);
		smList.add(statusSM);
		smList.add(lineContentSM);

		AASBundle bundle = new AASBundle(transportLineAAS, smList);
		return bundle;
	}
	
	public static LineContentSubmodel buildLineContentSubmodel(String aasIdShort, String aasId, IDeviceAAS devA, IDeviceAAS devB) {
		LineContentSubmodel lineContentSM = new LineContentSubmodel(aasIdShort, aasId, devA, devB);
		return lineContentSM;
	}
	
	
	private static SubmodelElementCollection buildParameters() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ProcessSubmodel.PARAMETERS_ID_SHORT);
		// TODO add parameters
		return collection;
	}
	
	private static SubmodelElementCollection buildServices() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ServiceSubmodel.SERVICES_ID_SHORT);
		Property serviceTransportAB = new Property(TRANSPORTATOB_SERVICE_ID_SHORT, "Transport object from device A to device B");
		Property serviceTransportBA = new Property(TRANSPORTBTOA_SERVICE_ID_SHORT, "Transport object from device B to device A");
		collection.addSubmodelElement(serviceTransportAB);
		collection.addSubmodelElement(serviceTransportBA);
		return collection;
	}
	
	private static Operation buildTransportAtoB(Runnable invokable) {
		Operation op = new Operation(TRANSPORTATOB_SERVICE_ID_SHORT);

		op.setInvokable(invokable);
		return op;
	}
	private static Operation buildTransportBtoA(Runnable invokable) {
		Operation op = new Operation(TRANSPORTBTOA_SERVICE_ID_SHORT);		
		op.setInvokable(invokable);
		return op;
	}
	
}
