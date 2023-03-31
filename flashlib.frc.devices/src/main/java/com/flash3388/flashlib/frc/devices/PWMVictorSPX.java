package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class PWMVictorSPX extends FrcSpeedController {

    @DeviceConstructor
    public PWMVictorSPX(
            @NamedArg("channel") int channel
    ) {
        super(new edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX(channel));
    }
}
