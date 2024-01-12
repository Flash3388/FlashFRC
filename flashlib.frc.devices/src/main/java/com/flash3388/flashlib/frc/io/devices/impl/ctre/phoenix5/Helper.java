package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class Helper {

    public static LimitSwitchSource getLimitSwitchSource(CTRELimitSwitch.Source source) {
        switch (source) {
            case REMOTE_TALON:
                return LimitSwitchSource.RemoteTalon;
            case REMOTE_CANIFIER:
                return LimitSwitchSource.RemoteCANifier;
            default:
                throw new IllegalArgumentException("unknown limit switch source: " + source);
        }
    }
}