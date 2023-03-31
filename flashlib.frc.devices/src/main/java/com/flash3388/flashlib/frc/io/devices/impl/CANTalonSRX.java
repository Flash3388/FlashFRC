package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.io.devices.CTREMagEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonSRX extends FrcSpeedController implements CTRETalon {

    private final BaseTalon mBaseTalon;

    private CANTalonSRX(WPI_TalonSRX talonSRX) {
        super(talonSRX);
        mBaseTalon = talonSRX;
    }

    @DeviceConstructor
    public CANTalonSRX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonSRX(channel));
    }

    @Override
    public CTREMagEncoder newMagEncoder(double revolutionToDistance) {
        return new CTREMagEncoderImpl(mBaseTalon, CTREMagEncoder.FX_PPR, revolutionToDistance);
    }
}
