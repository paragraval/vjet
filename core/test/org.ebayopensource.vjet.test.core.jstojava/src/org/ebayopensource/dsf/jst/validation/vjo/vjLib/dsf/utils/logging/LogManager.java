/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: LogManager.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging;
import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P3;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;

import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import com.ebay.junitnexgen.category.ModuleInfo;

/**
 * LogManager.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
@Category( { P3, FAST, UNIT })
@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class LogManager extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                9, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                10, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                11, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                12, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 264, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 326,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 338,
                0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                353, 0));
        // ML 10.27 variable defined no type in for statement will not thrown
        // error now.
    }

    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test Vjo vj lib project, To validate false positive ")
    public void testLogManager() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER,
                "vjoPro.dsf.utils.logging.", "LogManager.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
