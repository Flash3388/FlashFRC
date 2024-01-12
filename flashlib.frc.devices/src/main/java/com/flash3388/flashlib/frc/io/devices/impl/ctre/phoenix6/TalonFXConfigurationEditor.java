package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.BaseTalonConfigurationEditor;

public class TalonFXConfigurationEditor extends BaseTalonConfigurationEditor {

    private final Phoenix6TalonFX mTalon;

    public TalonFXConfigurationEditor(Phoenix6TalonFX talon) {
        mTalon = talon;
    }

    @Override
    public void save() {
        if (mFactoryDefault) {
            mTalon.mConfiguration = new TalonFXConfiguration();
        } else {
            mTalon.mMotor.getConfigurator().refresh(mTalon.mConfiguration);
        }

        if (mNeutralMode != null) {
            mTalon.mConfiguration.MotorOutput.NeutralMode = Helper.convertNeutralMode(mNeutralMode);
        }

        if (mInverted != null) {
            mTalon.mConfiguration.MotorOutput.Inverted = mInverted ?
                    InvertedValue.Clockwise_Positive :
                    InvertedValue.CounterClockwise_Positive;
        }

        mTalon.mMotor.getConfigurator().apply(mTalon.mConfiguration);
    }
}
