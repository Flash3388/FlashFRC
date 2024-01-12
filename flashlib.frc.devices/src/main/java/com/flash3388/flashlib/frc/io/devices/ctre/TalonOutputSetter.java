package com.flash3388.flashlib.frc.io.devices.ctre;

/**
 * Configures output for {@link CTRETalon}
 */
public interface TalonOutputSetter {

    /**
     * Configures next output to Neutral, i.e. not moving.
     *
     * @return this
     */
    TalonOutputSetter neutral();

    /**
     * Configures next output to PercentVBus output.
     *
     * @param value wanted PercentVBus value [-1, 1]
     * @return this
     */
    TalonOutputSetter precentVBus(double value);

    /**
     * Configures next output to Velocity output.
     * When activated, runs a closed-loop with the configured sensor and slot.
     *
     * @param value wanted velocity in degrees/second
     *              This value refers to the one at the motor output shaft.
     * @return this
     *
     * @implNote for <em>phoenix5</em> implementations, the set value is
     *  according to the expected integrated sensor for <em>TalonFX</em>,
     *  and <em>SRX Magnetic</em> for <em>TalonSRX</em>. If a different sensor
     *  is configured as the feedback device, then this will not function properly.
     */
    TalonOutputSetter velocity(double value);

    /**
     * Configures next output to Velocity output.
     * When activated, runs a closed-loop with the configured sensor and slot.
     *
     * @param value wanted velocity in raw device units.
     *              This value refers to the one at the motor output shaft.
     * @return this
     *
     * @implNote for <em>phoenix5</em> implementations, the set value is
     *  according to the raw sensor units, which changes according to the feedback
     *  sensor. For <em>phoenix6</em>, this value is rotations/second
     */
    TalonOutputSetter velocityRaw(double value);

    /**
     * Configures next output to Position output.
     * When activated, runs a closed-loop with the configured sensor and slot.
     *
     * @param value wanted Position in degrees.
     *              This value refers to the one at the motor output shaft.
     * @return this
     * @implNote for <em>phoenix5</em> implementations, the set value is
     *   according to the expected integrated sensor for <em>TalonFX</em>,
     *   and <em>SRX Magnetic</em> for <em>TalonSRX</em>. If a different sensor
     *   is configured as the feedback device, then this will not function properly.
     */
    TalonOutputSetter position(double value);

    /**
     * Configures next output to Position output.
     * When activated, runs a closed-loop with the configured sensor and slot.
     *
     * @param value wanted position in raw device units.
     *              This value refers to the one at the motor output shaft.
     * @return this
     *
     * @implNote for <em>phoenix5</em> implementations, the set value is
     *  according to the raw sensor units, which changes according to the feedback
     *  sensor. For <em>phoenix6</em>, this value is rotations
     */
    TalonOutputSetter positionRaw(double value);

    /**
     * Configures next output to Voltage output.
     *
     * @param value wanted Voltage in volts.
     * @return this
     */
    TalonOutputSetter voltage(double value);

    /**
     * Configures next output to use FeedForward.
     * Only supported for with closed-loop output modes.
     *
     * @param value wanted FeedForward [-1, 1]
     * @return this
     */
    TalonOutputSetter feedForward(double value);

    /**
     * Configures the motor according to the last set output configuration.
     */
    void set();
}
