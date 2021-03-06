/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.ui.typehierarchy;

import org.eclipse.jface.action.Action;

/**
 * Refocuses the type hierarchy on a type selection from a all types dialog.
 */
public class FocusOnTypeAction extends Action {

	private TypeHierarchyViewPart fViewPart;

	public FocusOnTypeAction(TypeHierarchyViewPart part) {
		super(TypeHierarchyMessages.FocusOnTypeAction_label);
		setDescription(TypeHierarchyMessages.FocusOnTypeAction_description);
		setToolTipText(TypeHierarchyMessages.FocusOnTypeAction_tooltip);

		fViewPart = part;
	}

	public void run() {
// Shell parent= fViewPart.getSite().getShell();
// TypeSelectionDialog2 dialog= new TypeSelectionDialog2(parent, false,
// PlatformUI.getWorkbench().getProgressService(),
// SearchEngine.createWorkspaceScope(...),
// IDLTKSearchConstants.TYPE);
//	
// dialog.setTitle(TypeHierarchyMessages.FocusOnTypeAction_dialog_title);
// dialog.setMessage(TypeHierarchyMessages.FocusOnTypeAction_dialog_message);
// if (dialog.open() != IDialogConstants.OK_ID) {
// return;
// }
//		
// Object[] types= dialog.getResult();
// if (types != null && types.length > 0) {
// IType type= (IType)types[0];
// fViewPart.setInputElement(type);
// }
	}
}
