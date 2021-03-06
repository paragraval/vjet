/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jstojava.parser.comments.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.JsParam;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class DefsTranslator extends BasePropsProtosTranslator {
	public DefsTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.DEFS;
	}

	@Override
	public void process(Expression expr, JstType jstType) {
		// TODO Auto-generated method stub
		super.process(expr, jstType);

		if (m_ctx.hasFunctionTypeRefReplacements()) {
			JstDepthFirstTraversal.accept(jstType, new FunctionTypeRefVisitor(
					m_ctx.getFunctionTypeRefReplacements()));
		}

	}

	protected void translateExpr(Expression expr, JstType jstType) {
		getCtx().enterBlock(type);
		try {
			BaseAst2JstTranslator translator;
			// if (expr instanceof ObjectLiteral) {
			// translator = new VjoOLTranslator(getCtx());
			// } else {
			// TODO - Throw error here...
			translator = TranslatorFactory.getTranslator(expr, getCtx());
			// }
			translator.setParent(jstType);
			Object node = translator.translate(expr);
			// post process Object Literal
			
			if (node instanceof ObjLiteral) {
				ObjLiteral literal = ((ObjLiteral) node);
				for (NV field : literal.getNVs()) {
					processDef(jstType, literal, field);
				}
				
			}

		} finally {
			getCtx().exitBlock();
		}
	}

	private void processDef(JstType jstType, ObjLiteral literal, NV field) {

		IExpr value = field.getValue();
		if (value != null) {
			
			if (value instanceof ObjLiteral) {
				processObjLiteralDef(jstType, field.getName(),
						(ObjLiteral) value);
			}else if (value instanceof FuncExpr) {
				processFunctionDef(jstType,
						((FuncExpr) value).getFunc());

			}
			else if (value instanceof BaseJstNode){
				processObjLiteralDef(jstType, field.getName(),
						(BaseJstNode)value, field);
			}
			
			
		}

	}

	

	// take the object literal and construct
	// JstObjLiteralType
	private void processObjLiteralDef(JstType jstType, String name,
			ObjLiteral value) {

		JstObjectLiteralType otype = new JstObjectLiteralType(name);
		otype.setPackage(new JstPackage(jstType.getName()));
		// TODO add source info
		JstCache.getInstance().addOType(otype);
		jstType.addProperty(new JstProperty(otype, name));
		for (NV field : value.getNVs()) {
			JstProperty prop = createPropertyFromNV(field);
			
			otype.addProperty(prop);
			if(field.isOptional()){
				otype.addOptionalField(prop);
			}
			
		}
		jstType.addOType(otype);
	}

	private void processObjLiteralDef(JstType jstType, String name,
			BaseJstNode value, NV field) {
		if (value instanceof FieldAccessExpr) {
			FieldAccessExpr fieldAccessExpr = (FieldAccessExpr) value;
			IJstType type = fieldAccessExpr.getType();
			IJstType fnType = JstCache.getInstance().getType("Function");
			if (fnType != null && fnType.equals(type)) {
				processFunction(jstType, name, fieldAccessExpr,field);		
			}
		}else{
		JstObjectLiteralType otype = new JstObjectLiteralType(name);
		otype.setPackage(new JstPackage(jstType.getName()));
		// TODO add source info
		JstCache.getInstance().addOType(otype);
		jstType.addProperty(new JstProperty(otype, name));
		
		jstType.addOType(otype);
		}
	}

	private void processFunction(JstType jstType, String name,
			FieldAccessExpr fieldAccessExpr, NV nv) {
		List<IJsCommentMeta> commentMeta = TranslateHelper.findMetaFromExpr(fieldAccessExpr);
		if(commentMeta!=null){
			JstMethod meth = (JstMethod)TranslateHelper.MethodTranslateHelper.createJstSynthesizedMethod( commentMeta, m_ctx,
				name);
			meth.setSource(nv.getSource());
			jstType.addMethod(meth);
		}
			

	}
	
		private IJsCommentMeta getLongestArgList(List<IJsCommentMeta> metaArr) {
				
				IJsCommentMeta maxMeta = null;
				int maxParamCount = 0;
				List<JsParam> params = null;
				for (IJsCommentMeta meta : metaArr) {
					if (maxMeta == null) {
						maxMeta = meta;
						params = TranslateHelper.getParams(meta);
						if (params != null) {
							maxParamCount = params.size();
						}
					}
					else {
						params = TranslateHelper.getParams(meta);
						if (params != null && params.size() > maxParamCount) {
							maxParamCount = params.size();
							maxMeta = meta;
						}
					}
				}
				return maxMeta;
			}
	
	private JstProperty createPropertyFromNV(NV nv){
		IExpr value = nv.getValue();
	
		if ((nv.getName() != null) && (nv.getName().length() > 0)
				&& (value instanceof JstLiteral)) {
			JstLiteral literal = (JstLiteral)value;
			// create the JstProperty
			// problem with result type 
			List<IJsCommentMeta> commentMeta = TranslateHelper.findMetaFromExpr(literal);
			IJsCommentMeta meta = null;
			if(commentMeta!=null){
				meta = commentMeta.get(0);
			}
			IJstType jstType = null;
			if(meta !=null){
				jstType = TranslateHelper.findType(m_ctx, meta.getTyping(), meta);
			}
			
			if(jstType==null){
				jstType = value.getResultType();
			}
			if(jstType==null){
				jstType = JstCache.getInstance().getType("Object");
			}
			
			JstProperty jstProperty = new JstProperty(jstType, nv.getName(),
					(JstLiteral) value, new JstModifiers().setPublic());
			jstProperty.setSource(nv.getSource());
			jstProperty.setComments(nv.getComments());
			return jstProperty;
		}
		return null;
	}
	
	private void processFunctionDef(JstType type, JstMethod mtd) {
		if (mtd != null) {
			type.addMethod(mtd);
			JstFunctionRefType ref = new JstFunctionRefType(mtd);
			ref.setPackage(new JstPackage(type.getName()));
			type.addOType(ref);
		}

	}

}
