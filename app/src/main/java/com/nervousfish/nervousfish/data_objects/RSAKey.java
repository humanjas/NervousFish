package com.nervousfish.nervousfish.data_objects;

import java.math.BigInteger;

/**
 * RSA variant of {@link IKey}.
 */
public final class RSAKey implements IKey {

    public final static String type = "RSA";

    public final BigInteger exponent;
    public final BigInteger modulus;

    /**
     * Constructor for a RSA key.
     *
     * @param modulus The modules of the RSA key.
     * @param exponent The exponent of the RSA key.
     */
    public RSAKey(final BigInteger modulus, final BigInteger exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString() {
        return this.modulus.toString() + " " + this.exponent.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final RSAKey that = (RSAKey) o;
        return this.modulus.equals(that.modulus)
            && this.exponent.equals(that.exponent);
    }

}
