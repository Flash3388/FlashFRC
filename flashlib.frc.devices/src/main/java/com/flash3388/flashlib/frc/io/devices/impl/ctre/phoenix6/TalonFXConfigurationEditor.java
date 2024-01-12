package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.flash3388.flashlib.frc.io.devices.ctre.ConfigurationEditor;

public class TalonFXConfigurationEditor implements ConfigurationEditor {

    private final Phoenix6TalonFX mTalon;

    public TalonFXConfigurationEditor(Phoenix6TalonFX talon) {
        mTalon = talon;

        mTalon.mMotor.getConfigurator().refresh(talon.mConfiguration);
    }

    @Override
    public ConfigurationEditor fromFactoryDefault() {
        mTalon.mConfiguration = new TalonFXConfiguration();
        return this;
    }

    @Override
    public ConfigurationEditor neutralMode(NeutralMode mode) {
        NeutralModeValue modeValue;
        switch (mode) {
            case COAST:
                modeValue = NeutralModeValue.Coast;
                break;
            case BRAKE:
                modeValue = NeutralModeValue.Brake;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        mConfigs.MotorOutput.NeutralMode = modeValue;
        return this;
    }

    @Override
    public ConfigurationEditor inverted(boolean inverted) {
        mConfigs.MotorOutput.Inverted = inverted ?
                InvertedValue.Clockwise_Positive :
                InvertedValue.CounterClockwise_Positive;
        return this;
    }

    @Override
    public void save() {
        mTalon.mMotor.getConfigurator().apply(mTalon.mConfiguration);
    }
}
