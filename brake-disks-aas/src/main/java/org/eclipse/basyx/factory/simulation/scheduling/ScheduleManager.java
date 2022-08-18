/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation.scheduling;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.extensions.aas.registration.mqtt.MqttAASRegistryHelper;
import org.eclipse.basyx.factory.mqtt.DemonstratorMqttListener;
import org.eclipse.basyx.factory.mqtt.DemonstratorMqttObserver;
import org.eclipse.basyx.factory.mqtt.RegistryEventParser;
import org.eclipse.basyx.factory.producttype.aas.ConnectedProductTypeAAS;
import org.eclipse.basyx.factory.producttype.aas.IProductTypeAAS;
import org.eclipse.basyx.factory.producttype.aas.ProductTypeAAS;
import org.eclipse.basyx.factory.producttype.submodel.IRecipeSubmodel;
import org.eclipse.basyx.factory.producttype.submodel.RecipeSubmodel;
import org.eclipse.basyx.factory.simulation.Main;
import org.eclipse.basyx.factory.simulation.execution.ExecutionStep;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.moquette.broker.Server;

/**
 * ScheduleManager takes a List of ProductTypeAAS and executes their contained
 * recipes
 * 
 * @author conradi, jungjan
 *
 */
@SuppressWarnings("unused")
public class ScheduleManager implements DemonstratorMqttObserver {

	private static Logger logger = LoggerFactory.getLogger(ScheduleManager.class);

	private IExecutionEngine engine;
	private ExecutorService threadPool;

	private final Server mqttBroker;
	private final DemonstratorMqttListener listener;
	private boolean executionStatus = false;

	public ScheduleManager(IExecutionEngine engine, Server mqttBroker, DemonstratorMqttListener listener) {
		this.engine = engine;
		this.mqttBroker = mqttBroker;
		this.listener = listener;
		this.mqttBroker.addInterceptHandler(listener);
		this.threadPool = Executors.newFixedThreadPool(1);
	}

	@Override
	public void onUpdate() {
		switch (this.listener.lastTopic) {
		case (MqttAASRegistryHelper.TOPIC_REGISTERAAS):
			aasRegistered();
			break;
		case (MqttAASRegistryHelper.TOPIC_DELETEAAS):
			aasDeleted();
			break;
		case (MqttAASRegistryHelper.TOPIC_REGISTERSUBMODEL):
			submodelRegistered();
			break;
		case (MqttAASRegistryHelper.TOPIC_DELETESUBMODEL):
			submodelDeleted();
			break;
		}
	}

	private void submodelDeleted() {
		logger.info("Deleted Submodel {}", this.listener.lastPayload);
	}

	private void submodelRegistered() {
		logger.info("Registered Submodel {}", this.listener.lastPayload);
		RegistryEventParser submodelEventParser = new RegistryEventParser(this.listener.lastPayload);
		String parentShellIdentifierId = submodelEventParser.parseShellIdentifierId();
		String submodelIdentifierId = submodelEventParser.parseSubmodelIdentifierId();

		if (!isProductTypeAAS(parentShellIdentifierId))
			return;
		if (!isExpectedSubmodel(parentShellIdentifierId, submodelIdentifierId))
			return;

		onRecipeSubmodelEventSchedule(parentShellIdentifierId);
	}

	private void aasDeleted() {
		logger.info("Deleted AAS {}", this.listener.lastPayload);
	}

	private void aasRegistered() {
		logger.info("Registered AAS {}", this.listener.lastPayload);
		RegistryEventParser ShellEventParser = new RegistryEventParser(this.listener.lastPayload);
		String shellIdentifierId = ShellEventParser.parseShellIdentifierId();
		if (!hasShellExpectedSubmodel(shellIdentifierId)) {
			return;
		}
		onRecipeSubmodelEventSchedule(shellIdentifierId);
	}

	private void onRecipeSubmodelEventSchedule(String shellId) {
		Runnable scheduleTask = () -> {
			ConnectedProductTypeAAS aas = ConnectedProductTypeAAS.getConnectedProductTypeAAS(shellId);
			scheduleProducts(aas);
		};
		Thread t = new Thread(scheduleTask);
		t.start();
	}

	/**
	 * Schedules the given recipes for execution
	 * 
	 * @param productTypes
	 *            The products to be scheduled
	 */
	public synchronized void scheduleProducts(IProductTypeAAS... productTypes) {
		for (IProductTypeAAS productType : productTypes) {
			List<ExecutionStep> steps = getRecipeFromAAS(productType);
			logger.info("Scheduling recipe '" + productType.getIdentification().getId() + "' for execution");

			// Queue the new product for execution
			// Execution does not run in parallel, as the size of the thread pool is 1
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					logger.info("Starting execution of recipe '" + productType.getIdentification().getId() + "'");
					engine.execute(steps);
				}
			});
		}
	}

	/**
	 * Gets the ExecutionSteps from a ProductTypeAAS
	 * 
	 * @param productType
	 *            the AAS
	 * @return List of contained ExecutionStep
	 */
	private List<ExecutionStep> getRecipeFromAAS(IProductTypeAAS productType) {
		IRecipeSubmodel recipe = productType.getRecepieSubmodel();

		IBlob executionPlanData = recipe.getExecutionPlan();
		String executionPlanJson = new String(executionPlanData.getByteArrayValue(), StandardCharsets.UTF_8);

		List<ExecutionStep> steps = null;
		try {
			steps = getExecutionStepsFromJSON(executionPlanJson);
		} catch (IOException e) {
			throw new RuntimeException("Exception loading ExecutionSteps.", e);
		}

		return steps;
	}

	/**
	 * Parses a JSON-String into ExecutionStep objects
	 * 
	 * @param recipeJSON
	 *            the JSON-String
	 * @return the List of parsed ExecutionSteps
	 */
	private List<ExecutionStep> getExecutionStepsFromJSON(String recipeJSON) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		List<ExecutionStep> steps = mapper.readValue(recipeJSON, new TypeReference<List<ExecutionStep>>() {
		});
		return steps;
	}

	/**
	 * Checks if the given aasId belongs to a ProductTypeAAS
	 */
	private boolean isProductTypeAAS(String aasId) {
		return aasId.startsWith(ProductTypeAAS.ID_PREFIX);
	}

	private boolean isExpectedSubmodel(String shellIdentifierId, String submodelIdentifierId) {
		String expectedSubmodelIdentifierId = shellIdentifierId + RecipeSubmodel.ID_SUFFIX;
		return submodelIdentifierId.equals(expectedSubmodelIdentifierId);
	}

	private boolean hasShellExpectedSubmodel(String shellIdentifierId) {
		AASRegistryProxy proxy = new AASRegistryProxy(Main.REGISTRY_URL);
		ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(proxy);
		Identifier shellIdentifier = new ModelUrn(shellIdentifierId);
		Identifier expectedSubmodelIdentifier = new ModelUrn(shellIdentifierId + RecipeSubmodel.ID_SUFFIX);
		try {
			aasManager.retrieveSubmodel(shellIdentifier, expectedSubmodelIdentifier);
			return true;
		} catch (Exception e) {
			// Expected Submodel does not exist
		}
		return false;
	}
}
