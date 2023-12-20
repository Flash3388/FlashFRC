package com.flash3388.flashlib.frc.io.devices;

import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;

public interface CTRELimitSwitch {

    boolean isClosed();

    void configure(LimitSwitchSource source, boolean normallyOpen);
    void configure(RemoteLimitSwitchSource source, boolean normallyOpen, int deviceId);
}
