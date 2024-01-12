package com.flash3388.flashlib.frc.io.devices.ctre;

import com.flash3388.flashlib.io.devices.SpeedController;

public interface CTRETalon extends SpeedController {

    /**
     * Opens the configuration editor for the device.
     * @return {@link TalonConfigurationEditor}.
     */
    TalonConfigurationEditor configure();

    /**
     * Gets the closed-loop slot. Allows querying and configuring the slot.
     *
     * @param slot slot
     * @return {@link TalonControlLoopSlot}
     * @throws IllegalArgumentException if the slot is not supported
     */
    TalonControlLoopSlot slot(TalonControlLoopSlot.Slot slot);

    /**
     * Gets the output configuration setter, allowing modifying the output
     * of the device.
     *
     * @return {@link TalonOutputSetter}
     */
    TalonOutputSetter output();

    /**
     * Gets the hardware forward limit switch. Allows querying and configuring the switch.
     *
     * @return {@link CTRELimitSwitch}
     */
    CTRELimitSwitch getForwardLimitSwitch();

    /**
     * Gets the hardware reverse limit switch. Allows querying and configuring the switch.
     *
     * @return {@link CTRELimitSwitch}
     */
    CTRELimitSwitch getReverseLimitSwitch();

    /**
     * Selects the feedback device to be the CTRE magnetic encoder.
     *
     * @param slot feedback slot
     * @param gearRatio gear ratio between the device output and the controlled mechanism.
     *                  Ratio of <em>driver / driven</em>
     * @param wheelRadius radius of the mechanism wheel, in meters.
     * @return {@link CTREEncoder} indicating the device.
     *
     * @implNote For <em>TalonFX</em> this is the integrated motor sensor.
     *      For <em>TalonSRX</em>, this is the connected <em>SRX Magnetic Encoder</em>.
     */
    CTREEncoder selectFeedbackSensorMagEncoder(TalonControlLoopSlot.Slot slot, double gearRatio, double wheelRadius);
}
