/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.simulation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.bundle.AASBundleHelper;
import org.eclipse.basyx.aas.factory.aasx.AASXToMetamodelConverter;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMqttConfiguration;
import org.eclipse.basyx.components.configuration.MqttPersistence;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.factory.mqtt.DemonstratorMqttListener;
import org.eclipse.basyx.factory.mqtt.MqttBrokerSuite;
import org.eclipse.basyx.factory.simulation.execution.IExecutionEngine;
import org.eclipse.basyx.factory.simulation.factory.DefaultDemonstrationFactory;
import org.eclipse.basyx.factory.simulation.scheduling.ScheduleManager;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.xml.sax.SAXException;

import io.moquette.broker.Server;

public class Main {
	
	public static final String REGISTRY_URL = "http://localhost:8080";
	public static final String SERVER_URL = "http://localhost:8081/demonstrator/shells";
	
	public static final String RECIPE_AASX_PATH = "resources/recipe.aasx";
	
	private RegistryComponent component;
	
	private BaSyxHTTPServer server;
	
	private IExecutionEngine engine;
	
	public static void main(String[] args) {
		AASAggregator aggregator = new AASAggregator(new AASRegistryProxy(REGISTRY_URL));
		new Main(new DefaultDemonstrationFactory(aggregator), aggregator);
	}
	
	public Main(DefaultDemonstrationFactory factory, AASAggregator aggregator) {
		Server mqttBroker = MqttBrokerSuite.createAndStartMqttBroker();
		
		DemonstratorMqttListener listener = new DemonstratorMqttListener();
		
		startRegistry(mqttBroker);
		
		AASAggregatorProvider provider = new AASAggregatorProvider(aggregator);
		startupAASServer(provider);
		
		List<IDeviceAAS> devices = factory.buildScenario();
		
		engine = factory.getEngine(devices);
		
		ScheduleManager manager = new ScheduleManager(engine, mqttBroker, listener);
		
		listener.addObserver(manager);
		
		try {
			loadRecipeAASX(RECIPE_AASX_PATH, aggregator);
		} catch (Exception e) {
			throw new RuntimeException("Could not load recipe AASX.", e);
		}
		
		addShutdownHook();
	}
	
	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
	}

	/**
	 * Pushes the recipe AASX to the server
	 * 
	 * @param aasxPath the path of the .aasx
	 * @param aggregator the aggregator of the server to be pushed to
	 */
	private void loadRecipeAASX(String aasxPath, IAASAggregator aggregator) throws IOException, InvalidFormatException, ParserConfigurationException, SAXException {
		AASXToMetamodelConverter packageManager = new AASXToMetamodelConverter(aasxPath);
		Set<AASBundle> bundles = packageManager.retrieveAASBundles();
		AASBundleHelper.integrate(aggregator, bundles);
		AASRegistryProxy proxy = new AASRegistryProxy(REGISTRY_URL);
		AASBundleHelper.register(proxy, bundles, SERVER_URL);
	}
	
	/**
	 * Startup an empty AAS server at "http://localhost:8081/demonstrator"
	 * 
	 */
	private void startupAASServer(IModelProvider provider) {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8081, "/demonstrator");
		BaSyxContext context = contextConfig.createBaSyxContext();
		HttpServlet modelServlet = new VABHTTPInterface<IModelProvider>(provider);
		context.addServletMapping("/*", modelServlet);

		server = new BaSyxHTTPServer(context);
		server.start();
	}
	
	/**
	 * Startup an empty registry server at "http://localhost:8080"
	 */
	private void startRegistry(Server mqttBroker) {
		// Start an InMemory registry server with a direct configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8080, "");
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		BaSyxMqttConfiguration mqttConfig = createMqttConfig(mqttBroker);
		component = new RegistryComponent(contextConfig, registryConfig);
		component.enableMQTT(mqttConfig);
		component.startComponent();
	}

	private BaSyxMqttConfiguration createMqttConfig(Server mqttBroker) {
		BaSyxMqttConfiguration config = new BaSyxMqttConfiguration();
		config.setServer("tcp://localhost:" + mqttBroker.getPort());
		config.setPersistenceType(MqttPersistence.INMEMORY);
		config.setQoS(0);
		return config;
	}
	
	public IExecutionEngine getEngine() {
		return engine;
	}
	
	private void stop() {
		component.stopComponent();
		server.shutdown();
	}
}
