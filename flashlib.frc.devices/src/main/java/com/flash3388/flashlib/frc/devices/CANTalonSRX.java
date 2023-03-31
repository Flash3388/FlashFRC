package com.flash3388.flashlib.frc.devices;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonSRX extends FrcSpeedController {

    @DeviceConstructor
    public CANTalonSRX(
            @NamedArg("channel") int channel
    ) {
        super(new WPI_TalonSRX(channel));
    }
}
