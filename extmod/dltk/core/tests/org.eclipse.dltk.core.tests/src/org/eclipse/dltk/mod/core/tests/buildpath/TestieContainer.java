/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.core.tests.buildpath;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuildpathContainer;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.core.IBuiltinModuleProvider;
import org.eclipse.dltk.mod.core.IScriptProject;

public class TestieContainer implements IBuildpathContainer {
	private IPath fPath;

	public TestieContainer(IPath srcPath) {
		this.fPath = srcPath;
	}

	public IBuildpathEntry[] getBuildpathEntries(IScriptProject project) {
		return new IBuildpathEntry[] { DLTKCore.newExtLibraryEntry(this.fPath) };
	}

	public String getDescription(IScriptProject project) {
		return "Testie Buildpath Container";
	}

	public int getKind() {
		return IBuildpathContainer.K_DEFAULT_SYSTEM;
	}

	public IPath getPath() {
		return fPath;
	}

	public IBuiltinModuleProvider getBuiltinProvider(IScriptProject project) {
		// TODO Auto-generated method stub
		return null;
	}
}
