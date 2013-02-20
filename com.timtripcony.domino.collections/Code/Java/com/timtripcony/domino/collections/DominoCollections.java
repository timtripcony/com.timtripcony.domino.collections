package com.timtripcony.domino.collections;

import lotus.domino.Document;
import lotus.domino.DocumentCollection;

public class DominoCollections {
	public static Iterable<Document> iterateDocuments(final DocumentCollection collection) {
		return new Iterable<Document>() {
			public DocumentIterator iterator() {
				return new DocumentIterator(collection);
			}
		};
	}
}
