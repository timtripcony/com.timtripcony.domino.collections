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
