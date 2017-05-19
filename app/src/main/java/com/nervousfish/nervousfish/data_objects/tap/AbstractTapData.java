package com.nervousfish.nervousfish.data_objects.tap;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class denotes a touch event on the screen.
 */
public abstract class AbstractTapData implements Serializable {
    private final Timestamp timestamp;

    /**
     * Creates a new TapData object.
     */
    public AbstractTapData() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * <b>Should only be used for testing purposes!</b>
     * @param timestamp The timestamp of the tap data
     */
    public AbstractTapData(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return The time on which the AbstractTapData was instantiated
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
}