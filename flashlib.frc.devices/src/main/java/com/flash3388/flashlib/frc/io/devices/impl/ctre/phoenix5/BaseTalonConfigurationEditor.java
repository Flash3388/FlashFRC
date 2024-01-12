package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.ConfigurationEditor;

public class BaseTalonConfigurationEditor implements ConfigurationEditor {

    private final BaseTalon mTalon;

    private com.ctre.phoenix.motorcontrol.NeutralMode mNeutralMode;
    private boolean mIsInverted;

    public BaseTalonConfigurationEditor(BaseTalon talon) {
        mTalon = talon;
        // TODO: RECALL LAST SET NEUTRALMODE
        mNeutralMode = com.ctre.phoenix.motorcontrol.NeutralMode.Coast;
        mIsInverted = false;
    }

    @Override
    public ConfigurationEditor fromFactoryDefault() {
        mTalon.configFactoryDefault();
        return this;
    }

    @Override
    public ConfigurationEditor neutralMode(NeutralMode mode) {
        switch (mode) {
            case COAST:
                mNeutralMode = com.ctre.phoenix.motorcontrol.NeutralMode.Coast;
                break;
            case BRAKE:
                mNeutralMode = com.ctre.phoenix.motorcontrol.NeutralMode.Brake;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return this;
    }

    @Override
    public ConfigurationEditor inverted(boolean inverted) {
        mIsInverted = inverted;
        return this;
    }

    @Override
    public void save() {
        mTalon.setNeutralMode(mNeutralMode);
        mTalon.setInverted(mIsInverted);
    }
}
