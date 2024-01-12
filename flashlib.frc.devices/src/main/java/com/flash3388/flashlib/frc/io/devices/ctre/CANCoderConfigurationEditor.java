package com.flash3388.flashlib.frc.io.devices.ctre;

public interface CANCoderConfigurationEditor {

    /**
     * Configures the offset of the encoder's absolute value, from the original zero
     * position.
     *
     * @param value offset, in degrees.
     * @return this
     */
    CANCoderConfigurationEditor setAbsoluteSensorOffset(double value);

    /**
     * Configures the gear ratio between the device and the mechanism, used in the calculation
     * of relative positioning and velocity.
     *
     * @param value gear ratio, driver / driven.
     * @return this.
     */
    CANCoderConfigurationEditor setGearRatio(double value);

    /**
     * Configures the radius of the wheel of the attached mechanism, used in the calculation
     * of relative positioning and velocity.
     *
     * @param value wheel radius, in meters.
     * @return this.
     */
    CANCoderConfigurationEditor setWheelRadius(double value);

    void save();
}
