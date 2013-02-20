package com.timtripcony.util;

import java.util.Collection;

import lotus.domino.Base;

@SuppressWarnings("unchecked")
public class DominoUtil {
	public static void incinerate(Object... objects) {
		for (Object object : objects) {
			if (object != null) {
				if (object instanceof Collection) {
					for (Object eachMember : (Collection) object) {
						incinerate(eachMember);
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
