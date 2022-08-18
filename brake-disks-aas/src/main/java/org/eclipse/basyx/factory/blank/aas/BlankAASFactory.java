/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.blank.aas;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.factory.blank.submodel.BlankSubmodel;
import org.eclipse.basyx.factory.simulation.Util;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Factory for building a BlankAAS including its Submodels
 * 
 * @author conradi
 *
 */
public class BlankAASFactory {
	
	public AASBundle buildBlankAAS(String blankType, int blankNumber) {
		
		String id = BlankAAS.ID_PREFIX + blankNumber;
		String idShort = BlankAAS.ID_SHORT_PREFIX + blankNumber;
		
		Asset asset = Util.getAsset(idShort, id);
		BlankSubmodel blankSM = buildBlankSubmodel(blankType, id, idShort);
		BlankAAS blankAAS = new BlankAAS(idShort, new ModelUrn(id), asset, blankSM);
		
		Set<ISubmodel> smList = new HashSet<>();
		smList.add(blankSM);
		
		AASBundle bundle = new AASBundle(blankAAS, smList);
		return bundle;
	}
	
	public static String getBlankAASId(int index) {
		return BlankAAS.ID_PREFIX + index;
	}
	
	private static BlankSubmodel buildBlankSubmodel(String blankType, String id, String idShort) {
		Property blankTypeProperty = new Property(blankType);
		blankTypeProperty.setIdShort(BlankSubmodel.BLANK_TYPE_ID_SHORT);
		
		id = id + "SM";
		idShort = idShort + "SMIdShort";
		
		BlankSubmodel blankSM = new BlankSubmodel(blankTypeProperty, new ModelUrn(id), idShort);
		return blankSM;
	}
	
}