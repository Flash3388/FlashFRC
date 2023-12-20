package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTRELimitSwitch;

public class CTREReverseLimitSwitch implements CTRELimitSwitch {
    private final BaseTalon mTalon;

    public CTREReverseLimitSwitch(BaseTalon talon) {
        mTalon = talon;
    }

    @Override
    public boolean isClosed() {
        return mTalon.isRevLimitSwitchClosed() == 1;
    }

    @Override
    public void configure(LimitSwitchSource source, boolean normallyOpen) {
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mTalon.configReverseLimitSwitchSource(source, normal);
    }

    @Override
    public void configure(RemoteLimitSwitchSource source, boolean normallyOpen, int deviceId) {
        LimitSwitchNormal normal = normallyOpen ? LimitSwitchNormal.NormallyOpen : LimitSwitchNormal.NormallyClosed;
        mTalon.configReverseLimitSwitchSource(source, normal, deviceId);
    }
}
