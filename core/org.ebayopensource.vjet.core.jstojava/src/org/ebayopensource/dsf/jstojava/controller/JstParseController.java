/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.io.File;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstParser;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefResolver;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.IWritableScriptUnit;
import org.ebayopensource.dsf.jst.ResolutionResult;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.lib.IJstLibProvider;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;

public class JstParseController implements IJstParseController {
	
	private IJstParser m_parser;
	private IJstRefResolver m_resolver;	
	private JstTypeSpaceMgr m_tsMgr;
	
	private IJstLibProvider m_libProvider = new NativeJsLibProvider();
	
	public JstParseController(IJstParser parser) {
		if (parser == null) {
			throw new RuntimeException("parser can't be null");
		}
		m_parser = parser;
		m_resolver = new JstExpressionBindingResolver(this);
		initialize();
	}	
	
	private void initialize() {		
		// load JsNative types into JstCache for vjo parsing
		m_libProvider.getAll();		
	}

	public synchronized IScriptUnit parse(String groupName, String fileName, String source) {
		return m_parser.parse(groupName, fileName, source);
	}
	
	public synchronized IScriptUnit parse(String groupName, File sourceFile) {
		return m_parser.parse(groupName, sourceFile);
	}

	public synchronized IScriptUnit parseAndResolve(String groupName, String fileName,
			String source) {
		if (source == null) {
			throw new DsfRuntimeException("missing source for " + fileName);
		}
		
		IWritableScriptUnit unit = null;
		ParseResultHolder holder = getCacheHolder(groupName, fileName, source);			
		if (holder.isLoaded()) {
			//System.out.println("Using Cache Result");
			unit = holder.getResult();
		}
		else {
			unit = m_parser.parse(groupName, fileName, source);
			resolve(groupName, unit);
			holder.setResult(unit);
		}	
		return unit;
	}
	
	public synchronized IScriptUnit parseAndResolve(String groupName, File sourceFile) {
		if (sourceFile == null) {
			throw new DsfRuntimeException("missing source file!");
		}
		String fileName = sourceFile.getAbsolutePath();
		String source = VjoParser.getContent(sourceFile);
		return parseAndResolve(groupName, fileName, source);		
	}

	public void resolve(IJstType type) {
		m_resolver.resolve(type);
	}
	
	public void resolve(String groupName, IWritableScriptUnit su) {
		addResolutionResultToSU(su, m_resolver.resolve(groupName, su.getType()));
		for (JstBlock block : su.getJstBlockList()) {
			addResolutionResultToSU(su, m_resolver.resolve(null, block));			
		}
	}
	
	private void addResolutionResultToSU(IWritableScriptUnit su, ResolutionResult resolve) {
		su.getProblems().addAll(resolve.getProblems());
		if(resolve.getType()!=null){
			su.setType(resolve.getType());
		}
	}

	public void resolve(String groupName, IJstType type) {
		m_resolver.resolve(groupName, type);
	}
	
	public void resolve(IJstProperty property) {
		m_resolver.resolve(property);
	}
	
	public void resolve(String groupName, IJstProperty property) {
		m_resolver.resolve(groupName, property);
	}
	
	public void resolve(IJstMethod method) {
		m_resolver.resolve(method);
	}
	
	public void resolve(String groupName, IJstMethod method) {
		m_resolver.resolve(groupName, method);
	}
	
	public void setRefResolver(IJstRefResolver resolver) {
		m_resolver = resolver;
	}
	
	public JstTypeSpaceMgr getJstTypeSpaceMgr() {
		return m_tsMgr;
	}

	public void setJstTSMgr(JstTypeSpaceMgr jstTSMgr) {
		m_tsMgr = jstTSMgr;
	}

	public void resolve(IJstType type, IJstNode node) {
		m_resolver.resolve(type, node);
	}

	/**
	 * Optimistic caching support for most current parsing result
	 */
	private ParseResultHolder m_currentHolder= null;
	
	private ParseResultHolder getCacheHolder
		(String group, String fileName, String source) {
		if (m_currentHolder == null || !m_currentHolder.isMatch(group, fileName, source)) {
			m_currentHolder = new ParseResultHolder(group, fileName, source);
		}		
		return m_currentHolder;
	}
	
	private static class ParseResultHolder {
		private final String m_group;
		private final String m_fileName;
		private final String m_source;
		private IWritableScriptUnit m_result = null;
		private boolean m_loaded = false;
		
		ParseResultHolder(String group, String fileName, String source) {
			m_group = group;
			m_fileName = fileName;
			m_source = source;
		}
		
		boolean isMatch(String group, String fileName, String source) {
			return m_group.equals(group) &&
				m_fileName.equals(fileName) &&
				m_source.equals(source);
		}
		
		boolean isLoaded() {
			return m_loaded;
		}
		
		IWritableScriptUnit getResult() {
			return m_result;
		}
		
		void setResult(IWritableScriptUnit result) {
			m_result = result;
			m_loaded = true;
		}
	}
}
