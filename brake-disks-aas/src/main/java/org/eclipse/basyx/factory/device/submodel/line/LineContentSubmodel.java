/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.factory.device.submodel.line;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.factory.device.aas.IDeviceAAS;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

/**
 * Contains the reference to the devices on each end of the transport line
 * 
 * @author conradi, molina
 *
 */
public class LineContentSubmodel extends Submodel implements ILineContentSubmodel {

	public static final String ID_SUFFIX = "_lineContentSM";
	public static final String ID_SHORT_SUFFIX = "_lineContentSMIdShort";
	
	public static final String DEVA_REF_ID_SHORT = "devA_ref";
	public static final String DEVB_REF_ID_SHORT = "devB_ref";
	
	public LineContentSubmodel(String aasIdShort, String aasId, IDeviceAAS devAAAS, IDeviceAAS devBAAS) {
		super(aasIdShort + ID_SHORT_SUFFIX, new ModelUrn(aasId + ID_SUFFIX));	
		
		this.addSubmodelElement(createRefElFromAAS(devAAAS, DEVA_REF_ID_SHORT));
		this.addSubmodelElement(createRefElFromAAS(devBAAS, DEVB_REF_ID_SHORT));
		
	}
	
	private static ReferenceElement createRefElFromAAS(IAssetAdministrationShell aas, String refEl_idshort) {
		ReferenceElement ref = new ReferenceElement((Reference) aas.getReference());
		ref.setIdShort(refEl_idshort);
		return ref;
	}
	
	@Override
	public IDeviceAAS getDeviceA() {
		throw new RuntimeException("getDeviceA on local copy is not supported");

	}
	@Override
	public IDeviceAAS getDeviceB() {
		throw new RuntimeException("getDeviceB on local copy is not supported");

	}
}
