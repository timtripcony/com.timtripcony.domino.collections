package com.timtripcony.domino.collections;

import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

public class DominoCollections {
	public static Iterable<Document> iterateDocuments(final DocumentCollection collection) {
		return new Iterable<Document>() {
			public DocumentIterator iterator() {
				return new DocumentIterator(collection);
			}
		};
	}

	public static Iterable<ViewEntry> iterateViewEntries(final ViewEntryCollection collection) {
		return new Iterable<ViewEntry>() {
			public ViewEntryIterator iterator() {
				return new ViewEntryIterator(collection);
			}
		};
	}
}
