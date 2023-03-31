package com.flash3388.flashlib.frc.devices;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANVictorSPX extends FrcSpeedController {

    @DeviceConstructor
    public CANVictorSPX(
            @NamedArg("channel") int channel
    ) {
        super(new WPI_VictorSPX(channel));
    }
}
