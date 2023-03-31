package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class VictorSP extends FrcSpeedController {

    @DeviceConstructor
    public VictorSP(
            @NamedArg("channel") int channel
    ) {
        super(new edu.wpi.first.wpilibj.motorcontrol.VictorSP(channel));
    }
}
