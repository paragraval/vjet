/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *

 *******************************************************************************/
package org.eclipse.dltk.mod.evaluation.types;

import org.eclipse.dltk.mod.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.mod.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.mod.ti.types.IEvaluatedType;

public class OldClassType implements IClassType {
	private TypeDeclaration fClass;
	private ModuleDeclaration fModule;

	public OldClassType(ModuleDeclaration module, TypeDeclaration method) {
		this.fClass = method;
		this.fModule = module;
	}

	public boolean equals(Object obj) {

		if (obj instanceof OldClassType) {
			OldClassType m = (OldClassType) obj;
			if (this.fClass == m.fClass && this.fModule == m.fModule) {
				return true;
			}
		}
		return false;
	}
	

	public int hashCode() {
		return this.fClass.hashCode() * 10 + this.fModule.hashCode();
	}

	public String getTypeName() {
		if (fClass != null) {
			return "class:" + fClass.getName(); //$NON-NLS-1$
		}
		return "class: !!unknown!!"; //$NON-NLS-1$
	}

	public TypeDeclaration getTypeDeclaration() {

		return this.fClass;
	}

	public boolean subtypeOf(IEvaluatedType type) {
		// TODO Auto-generated method stub
		return false;
	}

}
