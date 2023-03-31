package com.flash3388.flashlib.frc.io.devices;

import com.flash3388.flashlib.io.devices.SpeedController;

public interface CTRETalon extends SpeedController {

    CTREMagEncoder newMagEncoder(double revolutionToDistance);
}
