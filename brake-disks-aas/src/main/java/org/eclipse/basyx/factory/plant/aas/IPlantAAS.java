/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.plant.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.factory.plant.submodel.ITopologySubmodel;

/**
 * Interface for PlantAAS
 * 
 * @author conradi
 *
 */
public interface IPlantAAS extends IAssetAdministrationShell {

	public ITopologySubmodel getTopologySubmodel();
	
}
