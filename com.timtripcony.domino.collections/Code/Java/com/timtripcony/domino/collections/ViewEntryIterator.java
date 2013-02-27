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

import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

import com.ibm.commons.util.NotImplementedException;
import com.timtripcony.util.DominoUtil;
import com.timtripcony.util.LogUtil;

public class ViewEntryIterator extends AbstractDominoIterator<ViewEntry> {
	private transient ViewEntryCollection collection_;
	private transient ViewEntry currentEntry_;
	private boolean started_;
	private boolean done_;

	protected ViewEntryIterator(ViewEntryCollection collection) {
		setCollection(collection);
	}

	protected ViewEntryCollection getCollection() {
		return collection_;
	}

	public ViewEntry getCurrentEntry() {
		return currentEntry_;
	}

	public boolean hasNext() {
		boolean result = false;
		ViewEntry currentEntry = getCurrentEntry();
		ViewEntry nextEntry = null;
		try {
			nextEntry = ((currentEntry == null) ? (isDone() ? null : getCollection().getFirstEntry()) : getCollection().getNextEntry(
					currentEntry));
			result = (nextEntry != null);
		} catch (Throwable t) {
			LogUtil.trace(t, "Error seeking a next entry");
		} finally {
			DominoUtil.incinerate(nextEntry);
		}
		return result;
	}

	public boolean isDone() {
		return done_;
	}

	public boolean isStarted() {
		return started_;
	}

	public ViewEntry next() {
		LogUtil.startTimedOperation("Retrieving next view entry in collection");
		ViewEntry result = null;
		ViewEntry currentEntry = getCurrentEntry();
		try {
			result = ((currentEntry == null) ? getCollection().getFirstEntry() : getCollection().getNextEntry(currentEntry));
		} catch (Throwable t) {
			LogUtil.trace(t, "Error getting next view entry from collection");
		} finally {
			DominoUtil.incinerate(currentEntry);
			setCurrentEntry(result);
		}
		LogUtil.stopTimedOperation();
		return result;
	}

	public void remove() {
		throw new NotImplementedException();
	}

	protected void setCollection(ViewEntryCollection collection) {
		collection_ = collection;
	}

	public void setCurrentEntry(ViewEntry currentEntry) {
		currentEntry_ = currentEntry;
		setStarted(currentEntry != null);
		setDone(currentEntry == null);
	}

	public void setDone(boolean done) {
		done_ = done;
	}

	public void setStarted(boolean started) {
		started_ = started;
	}
}
