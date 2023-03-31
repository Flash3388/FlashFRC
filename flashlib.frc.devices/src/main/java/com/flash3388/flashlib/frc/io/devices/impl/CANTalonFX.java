package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.io.devices.CTREMagEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonFX extends FrcSpeedController implements CTRETalon {

    private final BaseTalon mBaseTalon;

    private CANTalonFX(WPI_TalonFX talonFX) {
        super(talonFX);
        mBaseTalon = talonFX;
    }

    @DeviceConstructor
    public CANTalonFX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonFX(channel));
    }

    @Override
    public CTREMagEncoder newMagEncoder(double revolutionToDistance) {
        return new CTREMagEncoderImpl(mBaseTalon, CTREMagEncoder.FX_PPR, revolutionToDistance);
    }
}
