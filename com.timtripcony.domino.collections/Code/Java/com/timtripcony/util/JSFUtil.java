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
