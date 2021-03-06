package com.nervousfish.nervousfish.modules.pairing;

import com.nervousfish.nervousfish.service_locator.IServiceLocator;
import com.nervousfish.nervousfish.service_locator.ModuleWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An handler doing nothing.
 */
public final class DummyQRHandler extends APairingHandler implements IQRHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("DummyQRHandler");

    /**
     * Prevents construction from outside the class.
     *
     * @param serviceLocator Can be used to get access to other modules
     */
    // This servicelocator will be used later on probably
    private DummyQRHandler(final IServiceLocator serviceLocator) {
        super(serviceLocator);
        LOGGER.info("Initialized");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void write(final byte[] buffer) {
        //dummy
    }

    /**
     * Creates a new instance of itself and wraps it in a {@link ModuleWrapper} so that only an
     * {@link IServiceLocator}
     *
     * @param serviceLocator The new service locator
     * @return A wrapper around a newly created instance of this class
     */
    public static ModuleWrapper<DummyQRHandler> newInstance(final IServiceLocator serviceLocator) {
        return new ModuleWrapper<>(new DummyQRHandler(serviceLocator));
    }
}
