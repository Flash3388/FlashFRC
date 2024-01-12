package com.flash3388.flashlib.frc.io.devices.ctre;

public interface ConfigurationEditor {

    enum NeutralMode {
        COAST,
        BRAKE
    }

    ConfigurationEditor fromFactoryDefault();
    ConfigurationEditor neutralMode(NeutralMode mode);
    ConfigurationEditor inverted(boolean inverted);

    void save();
}