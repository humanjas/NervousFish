package com.nervousfish.nervousfish.modules.pairing;

import java.io.IOException;

/**
 * Defines the interface implemented by all pairing handlers
 */
public interface IPairingHandler {

    /**
     * Luxury method that calls writeContact() for each contact of the database.
     *
     * @throws IOException When deserialization doesn't go well.
     */
    void writeAllContacts() throws IOException;
}
