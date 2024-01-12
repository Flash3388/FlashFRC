package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.frc.io.devices.impl.FrcSpeedController;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class Phoenix5VictorSPX extends FrcSpeedController {

    @DeviceConstructor
    public Phoenix5VictorSPX(
            @NamedArg("channel") int channel
    ) {
        super(new WPI_VictorSPX(channel));
    }
}
