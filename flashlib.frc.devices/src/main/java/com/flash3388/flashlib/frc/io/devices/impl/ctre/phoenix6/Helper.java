package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.configs.HardwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitSourceValue;
import com.ctre.phoenix6.signals.ReverseLimitSourceValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class Helper {

    public static ForwardLimitSourceValue getForwardLimitSwitchSource(CTRELimitSwitch.Source source) {
        switch (source) {
            case REMOTE_TALON:
                return ForwardLimitSourceValue.RemoteTalonFX;
            case REMOTE_CANIFIER:
                return ForwardLimitSourceValue.RemoteCANifier;
            case REMOTE_CANCODER:
                return ForwardLimitSourceValue.RemoteCANcoder;
            default:
                throw new IllegalArgumentException("unknown limit switch source: " + source);
        }
    }

    public static ReverseLimitSourceValue getReverseLimitSwitchSource(CTRELimitSwitch.Source source) {
        switch (source) {
            case REMOTE_TALON:
                return ReverseLimitSourceValue.RemoteTalonFX;
            case REMOTE_CANIFIER:
                return ReverseLimitSourceValue.RemoteCANifier;
            case REMOTE_CANCODER:
                return ReverseLimitSourceValue.RemoteCANcoder;
            default:
                throw new IllegalArgumentException("unknown limit switch source: " + source);
        }
    }

    public static HardwareLimitSwitchConfigs getConfigs(TalonFX motor) {
        HardwareLimitSwitchConfigs limitSwitchConfigs = new HardwareLimitSwitchConfigs();
        motor.getConfigurator().refresh(limitSwitchConfigs);

        return limitSwitchConfigs;
    }
}
