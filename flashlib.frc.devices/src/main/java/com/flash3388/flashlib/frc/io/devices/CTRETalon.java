package com.flash3388.flashlib.frc.io.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.flash3388.flashlib.io.devices.SpeedController;

public interface CTRETalon extends SpeedController, CTREPidController {

    void setControlMode(ControlMode controlMode);
    ControlMode getControlMode();

    CTREMagEncoder newMagEncoder(double revolutionToDistance);
}
