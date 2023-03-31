package com.flash3388.flashlib.frc.devices;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonFX extends FrcSpeedController {

    @DeviceConstructor
    public CANTalonFX(
            @NamedArg("channel") int channel
    ) {
        super(new WPI_TalonFX(channel));
    }
}
