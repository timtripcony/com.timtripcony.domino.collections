package com.timtripcony.domino.collections;

import java.util.Iterator;

import lotus.domino.Database;
import lotus.domino.Session;

import com.timtripcony.util.JSFUtil;
import com.timtripcony.util.LogUtil;

@SuppressWarnings("unchecked")
public abstract class AbstractDominoIterator<T> implements Iterator {
	private static final long serialVersionUID = 1L;
	private String serverName_;
	private String filePath_;

	protected Database getDatabase() {
		LogUtil.startTimedOperation("Getting handle on parent database");
		Database result = null;
		Session session = JSFUtil.getSession();
		try {
			result = session.getDatabase(getServerName(), getFilePath());
		} catch (Throwable t) {
			LogUtil.trace(t, "Error getting parent database for iterable collection");
		}
		LogUtil.stopTimedOperation();
		return result;
	}

	protected String getFilePath() {
		return filePath_;
	}

	protected String getServerName() {
		return serverName_;
	}

	protected void setDatabase(Database database) {
		try {
			setFilePath(database.getFilePath());
			setServerName(database.getServer());
		} catch (Throwable t) {
			LogUtil.trace(t, "Error setting parent database for iterable collection");
		}
	}

	protected void setFilePath(String filePath) {
		filePath_ = filePath;
	}

	protected void setServerName(String serverName) {
		serverName_ = serverName;
	}
}
