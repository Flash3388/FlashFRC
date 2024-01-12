package com.flash3388.flashlib.frc.io.devices.ctre;

public interface CTRELimitSwitch {

    enum Source {
        REMOTE_TALON,
        REMOTE_CANIFIER,
        REMOTE_CANCODER
    }

    /**
     * Gets the configured limit switch state.
     * This value is affected by the configuration of <em>normallyOpen</em>.
     *
     * @return <b>true</b> if closed, <b>false</b> if open.
     */
    boolean isClosed();

    /**
     * Configures the limit switch to the integrated device limit switch connector.
     *
     * @param normallyOpen <b>true</b> if the sensor should be normally open, <b>false</b>
     *                     for normally closed.
     */
    void configureIntegrated(boolean normallyOpen);

    /**
     * Configures the limit switch to a specified source.
     *
     * @param source the source of the limit switch.
     * @param normallyOpen <b>true</b> if the sensor should be normally open, <b>false</b>
     *                     for normally closed.
     * @param deviceId device id of the source
     */
    void configure(Source source, boolean normallyOpen, int deviceId);

    /**
     * Disables the limit switch.
     */
    void disable();
}
