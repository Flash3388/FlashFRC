package com.flash3388.flashlib.frc.io.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.flash3388.flashlib.io.devices.SpeedController;

public interface CTRETalon extends SpeedController {

    void set(ControlMode controlMode, double value);

    @Override
    default void set(double speed) {
        set(ControlMode.PercentOutput, speed);
    }

    void configureNeutralMode(NeutralMode mode);

    CTREPidController getPidController(int slotIdx);
    CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius);
    CTRELimitSwitch getForwardLimitSwitch();
    CTRELimitSwitch getReverseLimitSwitch();
}
