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
