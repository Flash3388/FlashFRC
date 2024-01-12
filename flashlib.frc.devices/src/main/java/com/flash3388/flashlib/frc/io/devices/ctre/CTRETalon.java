package com.flash3388.flashlib.frc.io.devices.ctre;

import com.flash3388.flashlib.io.devices.SpeedController;

public interface CTRETalon extends SpeedController {

    ConfigurationEditor configure();
    ControlLoopSlot slot(ControlLoopSlot.Slot slot);
    OutputSetter output();

    CTRELimitSwitch getForwardLimitSwitch();
    CTRELimitSwitch getReverseLimitSwitch();

    CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius);
}
