package com.flash3388.flashlib.frc.io.devices.impl;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class PWMTalonSRX extends FrcSpeedController {

    @DeviceConstructor
    public PWMTalonSRX(
            @NamedArg("channel") int channel
    ) {
        super(new edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX(channel));
    }
}
