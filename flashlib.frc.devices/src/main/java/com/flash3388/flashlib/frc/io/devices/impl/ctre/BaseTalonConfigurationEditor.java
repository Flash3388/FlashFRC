package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.TalonConfigurationEditor;

public abstract class BaseTalonConfigurationEditor implements TalonConfigurationEditor {

    protected boolean mFactoryDefault;
    protected NeutralMode mNeutralMode;
    protected Boolean mInverted;

    public BaseTalonConfigurationEditor() {
        mFactoryDefault = false;
        mNeutralMode = null;
        mInverted = null;
    }

    @Override
    public TalonConfigurationEditor fromFactoryDefault() {
        mFactoryDefault = true;
        return this;
    }

    @Override
    public TalonConfigurationEditor setNeutralMode(NeutralMode mode) {
        mNeutralMode = mode;
        return this;
    }

    @Override
    public TalonConfigurationEditor setInverted(boolean inverted) {
        mInverted = inverted;
        return this;
    }
}
