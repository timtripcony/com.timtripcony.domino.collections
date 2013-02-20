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
	private Document current_;

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

	public int[] getIdArray() {
		return idArray_;
	}

	public int getIndex() {
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

	public void setIdArray(int[] idArray) {
		idArray_ = idArray;
	}

	public void setIndex(int index) {
		index_ = index;
	}
}
