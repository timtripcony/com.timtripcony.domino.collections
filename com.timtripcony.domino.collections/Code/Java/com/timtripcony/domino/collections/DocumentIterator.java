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

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.NoteCollection;

import com.ibm.commons.util.NotImplementedException;
import com.timtripcony.util.DominoUtil;
import com.timtripcony.util.LogUtil;

public class DocumentIterator extends AbstractDominoIterator<Document> {
	private static final long serialVersionUID = 1L;
	private int index_ = 0;
	private int[] idArray_;
	private transient Document current_;

	protected DocumentIterator(DocumentCollection collection) {
		setIdArray(getCollectionIds(collection));
	}

	protected int[] getCollectionIds(DocumentCollection collection) {
		LogUtil.startTimedOperation("Getting Note ID array for document collection");
		int[] result = null;
		if (collection != null) {
			NoteCollection nc = null;
			try {
				Database db = collection.getParent();
				setDatabase(db);
				nc = db.createNoteCollection(false);
				nc.add(collection);
				if (nc.getCount() > 0) {
					result = nc.getNoteIDs();
				}
			} catch (Throwable t) {
				LogUtil.trace(t, "Error retrieving ID array from document collection");
			} finally {
				DominoUtil.incinerate(nc);
			}
		}
		LogUtil.stopTimedOperation();
		return result;
	}

	protected int[] getIdArray() {
		return idArray_;
	}

	protected int getIndex() {
		return index_;
	}

	public boolean hasNext() {
		return !((getIndex() + 1) > getIdArray().length);
	}

	public Document next() {
		LogUtil.startTimedOperation("Retrieving next document in collection");
		Document result = null;
		if (hasNext()) {
			String noteId = Integer.toHexString(getIdArray()[getIndex()]);
			setIndex(getIndex() + 1);
			Database db = getDatabase();
			try {
				DominoUtil.incinerate(current_);
				result = db.getDocumentByID(noteId);
				current_ = result;
			} catch (Throwable t) {
				LogUtil.trace(t, "Error getting next document from parent database");
			}
		}
		LogUtil.stopTimedOperation();
		return result;
	}

	public void remove() {
		throw new NotImplementedException();
	}

	protected void setIdArray(int[] idArray) {
		idArray_ = idArray;
	}

	protected void setIndex(int index) {
		index_ = index;
	}
}
