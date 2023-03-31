package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.io.devices.CTREMagEncoder;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonFX extends CTREBaseTalon {

    private CANTalonFX(WPI_TalonFX talonFX, int pidLoopIndex) {
        super(talonFX, talonFX, pidLoopIndex);
    }

    private CANTalonFX(WPI_TalonFX talonFX) {
        super(talonFX, talonFX);
    }

    @DeviceConstructor
    public CANTalonFX(
            @NamedArg("channel") int channel,
            @NamedArg("pidLoopIndex") int pidLoopIndex
    ) {
        this(new WPI_TalonFX(channel), pidLoopIndex);
    }

    @DeviceConstructor
    public CANTalonFX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonFX(channel));
    }

    @Override
    public CTREMagEncoder newMagEncoder(double revolutionToDistance) {
        return new CTREMagEncoderImpl(mTalon, CTREMagEncoder.FX_PPR, revolutionToDistance);
    }
}
