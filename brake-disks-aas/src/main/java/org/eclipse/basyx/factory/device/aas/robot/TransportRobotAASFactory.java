/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.aas.robot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.device.aas.DeviceAASFactory;
import org.eclipse.basyx.factory.device.aas.robot.servicebuilder.DefaultTransportRobotServiceBuilder;
import org.eclipse.basyx.factory.device.aas.robot.servicebuilder.ITransportRobotServiceBuilder;
import org.eclipse.basyx.factory.device.submodel.ProcessSubmodel;
import org.eclipse.basyx.factory.device.submodel.ServiceSubmodel;
import org.eclipse.basyx.factory.device.submodel.StatusSubmodel;
import org.eclipse.basyx.factory.device.submodel.robot.ActuatorPositionSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

/**
 * Factory for building a TransportRobotAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class TransportRobotAASFactory extends DeviceAASFactory {

	
	public static final String TRANSPORT_FROM_TO_SERVICE_ID_SHORT = "transportFromToService";

	public static AASBundle buildTransportRobotAAS(String idShort, String id) {
		return buildTransportRobotAAS(idShort, id, new DefaultTransportRobotServiceBuilder());
	}
	
	public static AASBundle buildTransportRobotAAS(String idShort, String id, ITransportRobotServiceBuilder serviceBuilder) {
		ProcessSubmodel processSM = buildProcessSubmodel(idShort, id, buildParameters());
		ServiceSubmodel serviceSM = buildServiceSubmodel(idShort, id, buildServices());
		StatusSubmodel statusSM = buildStatusSubmodel(idShort, id);
		ActuatorPositionSubmodel actuatorPositionSM = buildActuatorPostionSubmodel(idShort, id);
		Asset asset = Util.getAsset(idShort, id);
		TransportRobotAAS transportRobotAAS = new TransportRobotAAS(idShort, id, asset, actuatorPositionSM, processSM, serviceSM, statusSM);
		
		serviceSM.addSubmodelElement(buildTransportFromToOperation(serviceBuilder.buildTransportFromToConsumer(id)));
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(processSM);
		smList.add(serviceSM);
		smList.add(statusSM);
		smList.add(actuatorPositionSM);

		AASBundle bundle = new AASBundle(transportRobotAAS, smList);
		return bundle;
	}
	
	public static ActuatorPositionSubmodel buildActuatorPostionSubmodel(String aasIdShort, String aasId) {
		Property currentPosition = new Property("");
		currentPosition.setIdShort(ActuatorPositionSubmodel.CURRENTPOSITION_ID_SHORT);
		ActuatorPositionSubmodel apSM = new ActuatorPositionSubmodel(aasIdShort, aasId, currentPosition);
		return apSM;
	}
	
	
	private static SubmodelElementCollection buildParameters() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ProcessSubmodel.PARAMETERS_ID_SHORT);
		// TODO add parameters
		return collection;
	}
	
	private static SubmodelElementCollection buildServices() {
		SubmodelElementCollection collection = new SubmodelElementCollection(ServiceSubmodel.SERVICES_ID_SHORT);
		Property serviceMove = new Property(TRANSPORT_FROM_TO_SERVICE_ID_SHORT, "Move object from to");
		collection.addSubmodelElement(serviceMove);
		return collection;
	}
	
	private static Operation buildTransportFromToOperation(Consumer<Object[]> invokable) {
		Operation op = new Operation(TRANSPORT_FROM_TO_SERVICE_ID_SHORT);
		
		Property fromProperty = new Property("from", "");
		fromProperty.setKind(ModelingKind.TEMPLATE);
		OperationVariable fromOpVar = new OperationVariable(fromProperty);
		
		Property toProperty = new Property("to", "");
		toProperty.setKind(ModelingKind.TEMPLATE);
		OperationVariable toOpVar = new OperationVariable(toProperty);
		
		op.setInputVariables(Arrays.asList(fromOpVar, toOpVar));
		
		op.setInvokable(invokable);
		return op;
	}
	
}
