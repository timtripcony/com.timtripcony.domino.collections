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
