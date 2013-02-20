com.timtripcony.domino.collections
==================================

Convenience wrappers for Domino collection objects to make them behave more like the Java Collections API

This library provides two key advantages over using the native Domino API to iterate collections:

1. It takes care of all recycling for you. You don't have to remember to recycle each collection member before retrieving the next.
2. Iterable wrappers allow collections to be very tersely iterated:

        Database currentDatabase = JSFUtil.getCurrentDatabase();
        for (Document eachDoc : DominoCollections.iterateDocuments(currentDatabase.getAllDocuments())) {
            LogUtil.log(eachDoc.getItemValueString("Subject"));
        }

It really is that easy.

- No need to call a method that needs to be told the current member to give you the next... the loop knows what it's looping, so it automatically gets the next one for you.
- No need to create a duplicate "temp" variable to assign the next collection member to in order to be able to recycle the current member. The collection cleans up after itself, so you can focus on the document content, not on manually deallocating memory.
