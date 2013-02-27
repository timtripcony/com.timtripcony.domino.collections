/*
 * © Copyright Tim Tripcony 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.timtripcony.util;

import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;

import lotus.domino.Database;
import lotus.domino.Session;

import com.ibm.xsp.application.ApplicationEx;

public class JSFUtil {
	public static ApplicationEx getApplication() {
		return ApplicationEx.getInstance();
	}

	public static Database getCurrentDatabase() {
		return (Database) resolveVariable("database");
	}

	public static Session getSession() {
		return (Session) resolveVariable("session");
	}

	public static Session getSessionAsSigner() {
		return (Session) resolveVariable("sessionAsSigner");
	}

	public static VariableResolver getVariableResolver() {
		return getApplication().getVariableResolver();
	}

	public static Object resolveVariable(String variable) {
		return getVariableResolver().resolveVariable(FacesContext.getCurrentInstance(), variable);
	}
}
