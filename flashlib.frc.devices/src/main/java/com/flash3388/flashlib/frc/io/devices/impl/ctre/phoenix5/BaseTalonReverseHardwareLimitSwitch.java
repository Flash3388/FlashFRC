package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class BaseTalonReverseHardwareLimitSwitch implements CTRELimitSwitch {

    private final BaseTalon mMotor;

    public BaseTalonReverseHardwareLimitSwitch(BaseTalon motor) {
        mMotor = motor;
    }

    @Override
    public boolean isClosed() {
        return mMotor.isRevLimitSwitchClosed() == 1;
    }

    @Override
    public void configureIntegrated(boolean normallyOpen) {
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, normal);
    }

    @Override
    public void configure(Source source, boolean normallyOpen, int deviceId) {
        LimitSwitchSource nativeSource = Helper.getLimitSwitchSource(source);
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mMotor.configReverseLimitSwitchSource(nativeSource, normal, deviceId);
    }

    @Override
    public void disable() {
        mMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    }
}
