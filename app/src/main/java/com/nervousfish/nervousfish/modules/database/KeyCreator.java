package com.nervousfish.nervousfish.modules.database;

import com.google.gson.stream.JsonReader;
import com.nervousfish.nervousfish.data_objects.IKey;

import java.io.IOException;

public class KeyCreator {
    final KeyCreatorCallable creator;

    public KeyCreator(final KeyCreatorCallable creator) {
        this.creator = creator;
    }

    IKey fromJSON(final JsonReader reader) {
        try {
            return this.creator.call(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface KeyCreatorCallable {
        IKey call(final JsonReader reader) throws IOException;
    }
}
