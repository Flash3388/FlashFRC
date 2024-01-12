package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.BaseTalonConfigurationEditor;

public class Phoenix5TalonConfigurationEditor extends BaseTalonConfigurationEditor {

    private final BaseTalon mTalon;

    public Phoenix5TalonConfigurationEditor(BaseTalon talon) {
        mTalon = talon;
    }

    @Override
    public void save() {
        if (mFactoryDefault) {
            mTalon.configFactoryDefault();
        }

        if (mNeutralMode != null) {
            mTalon.setNeutralMode(Helper.convertNeutralMode(mNeutralMode));
        }

        if (mInverted != null) {
            mTalon.setInverted(mInverted);
        }
    }
}
