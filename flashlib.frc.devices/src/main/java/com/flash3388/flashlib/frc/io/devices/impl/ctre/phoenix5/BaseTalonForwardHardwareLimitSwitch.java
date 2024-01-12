package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class BaseTalonForwardHardwareLimitSwitch implements CTRELimitSwitch {

    private final BaseTalon mMotor;

    public BaseTalonForwardHardwareLimitSwitch(BaseTalon motor) {
        mMotor = motor;
    }

    @Override
    public boolean isClosed() {
        return mMotor.isFwdLimitSwitchClosed() == 1;
    }

    @Override
    public void configureIntegrated(boolean normallyOpen) {
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, normal);
    }

    @Override
    public void configure(Source source, boolean normallyOpen, int deviceId) {
        LimitSwitchSource nativeSource = Helper.getLimitSwitchSource(source);
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mMotor.configForwardLimitSwitchSource(nativeSource, normal, deviceId);
    }

    @Override
    public void disable() {
        mMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    }
}
