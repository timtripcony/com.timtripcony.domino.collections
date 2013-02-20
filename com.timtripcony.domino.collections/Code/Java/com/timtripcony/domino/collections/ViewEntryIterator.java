package com.timtripcony.domino.collections;

import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

import com.ibm.commons.util.NotImplementedException;
import com.timtripcony.util.DominoUtil;
import com.timtripcony.util.LogUtil;

public class ViewEntryIterator extends AbstractDominoIterator<ViewEntry> {
	private transient ViewEntryCollection collection_;
	private transient ViewEntry currentEntry_;

	protected ViewEntryIterator(ViewEntryCollection collection) {
		setCollection(collection);
	}

	protected ViewEntryCollection getCollection() {
		return collection_;
	}

	public ViewEntry getCurrentEntry() {
		if (currentEntry_ == null) {
			try {
				currentEntry_ = getCollection().getFirstEntry();
			} catch (Throwable t) {
				LogUtil.trace(t, "Error getting first entry from collection");
			}
		}
		return currentEntry_;
	}

	public boolean hasNext() {
		boolean result = false;
		ViewEntry currentEntry = getCurrentEntry();
		if (currentEntry != null) {
			ViewEntry nextEntry = null;
			try {
				nextEntry = getCollection().getNextEntry(currentEntry);
				result = (nextEntry != null);
			} catch (Throwable t) {
				LogUtil.trace(t, "Error seeking a next entry");
			} finally {
				DominoUtil.incinerate(nextEntry);
			}
		}
		return result;
	}

	public ViewEntry next() {
		LogUtil.startTimedOperation("Retrieving next view entry in collection");
		ViewEntry result = null;
		ViewEntry currentEntry = getCurrentEntry();
		try {
			result = getCollection().getNextEntry(currentEntry);
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
	}
}
