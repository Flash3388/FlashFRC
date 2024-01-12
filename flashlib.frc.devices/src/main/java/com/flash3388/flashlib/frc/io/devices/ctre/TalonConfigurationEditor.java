package com.flash3388.flashlib.frc.io.devices.ctre;

public interface TalonConfigurationEditor {

    enum NeutralMode {
        COAST,
        BRAKE
    }

    /**
     * Configures the device to return to factory default. Upon saving,
     * any untouched configuration will be returned to factory default.
     * This will affect all device configuration, which can include values
     * not accessible here.
     *
     * @return this
     */
    TalonConfigurationEditor fromFactoryDefault();

    /**
     * Configures the neutral mode used by the device. This will affect the behaviour
     * of the device when entering neutral, i.e. stopping motion.
     *
     * @param mode {@link NeutralMode}
     * @return this
     */
    TalonConfigurationEditor setNeutralMode(NeutralMode mode);

    /**
     * Configures the device direction. Inverted device will move in an opposite
     * manner, i.e. backwards instead of forwards.
     *
     * @param inverted <b>true</b> for inverted, <b>false</b> for normal.
     * @return this
     */
    TalonConfigurationEditor setInverted(boolean inverted);

    /**
     * Saves the changed configuration.
     */
    void save();
}
