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
package com.timtripcony.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lotus.domino.Base;

@SuppressWarnings("unchecked")
public class DominoUtil {
	public static void incinerate(Object... objects) {
		for (Object object : objects) {
			if (object != null) {
				if (object instanceof Collection) {
					if (object instanceof Map) {
						Set<Map.Entry> entries = ((Map) object).entrySet();
						for (Map.Entry<?, ?> entry : entries) {
							incinerate(entry.getKey(), entry.getValue());
						}
					} else {
						Iterator i = ((Collection) object).iterator();
						while (i.hasNext()) {
							incinerate(i.next());
						}
					}
				} else if (object instanceof Base) {
					Base dominoObject = (Base) object;
					try {
						dominoObject.recycle();
					} catch (Throwable t) {
						LogUtil.trace(t, "Error recycling Domino object");
					}
				}
			}
		}
	}
}
