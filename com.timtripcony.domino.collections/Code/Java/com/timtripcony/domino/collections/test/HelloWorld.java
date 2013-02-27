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
package com.timtripcony.domino.collections.test;

import lotus.domino.Database;
import lotus.domino.Document;

import com.timtripcony.domino.collections.DominoCollections;
import com.timtripcony.util.JSFUtil;
import com.timtripcony.util.LogUtil;

public class HelloWorld {
	/*
	 * This example loops through all documents in the database and prints the Note ID of each. The syntax used demonstrates that we can
	 * just use abbreviated loops to iterate the collection. There is no need to manually recycle each entry or remind the collection what
	 * the current entry is in order to retrieve the next. The iterable wrapper handles all of that automatically.
	 * 
	 * This example also demonstrates using timers to gather performance metrics. Timers, debug statements, and even stack traces can all be
	 * suppressed by simply toggling boolean constants in the LogUtil class.
	 */
	public HelloWorld() {
		LogUtil.startTimedOperation("Printing the Note ID of all documents in the current database");
		Database currentDatabase = JSFUtil.getCurrentDatabase();
		try {
			for (Document eachDoc : DominoCollections.iterateDocuments(currentDatabase.getAllDocuments())) {
				LogUtil.log("Document ID: " + eachDoc.getNoteID());
			}
		} catch (Throwable t) {
			LogUtil.trace(t, "Error iterating all documents in the database");
		}
		LogUtil.stopTimedOperation();
	}
}
