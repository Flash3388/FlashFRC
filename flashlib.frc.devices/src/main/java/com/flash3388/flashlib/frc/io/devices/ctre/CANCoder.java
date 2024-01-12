package com.flash3388.flashlib.frc.io.devices.ctre;

public interface CANCoder extends CTREEncoder {

    /**
     * Opens the configuration editor for the device.
     * @return {@link CANCoderConfigurationEditor}.
     */
    CANCoderConfigurationEditor configure();

    /**
     * Gets the absolute position reported by the device.
     *
     * @return degrees [0, 360]
     */
    double getAbsolutePosition();
}
