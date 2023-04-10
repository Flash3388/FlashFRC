package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.io.devices.CTREMagEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonSRX extends CTREBaseTalon {

    private CANTalonSRX(WPI_TalonSRX talonSRX, int pidLoopIndex) {
        super(talonSRX, talonSRX, pidLoopIndex);
    }

    private CANTalonSRX(WPI_TalonSRX talonSRX) {
        super(talonSRX, talonSRX);
    }

    @DeviceConstructor
    public CANTalonSRX(
            @NamedArg("channel") int channel,
            @NamedArg("pidLoopIndex") int pidLoopIndex
    ) {
        this(new WPI_TalonSRX(channel), pidLoopIndex);
    }

    @DeviceConstructor
    public CANTalonSRX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonSRX(channel));
    }

    @Override
    public CTREMagEncoder newMagEncoder(double revolutionToDistance) {
        return new CTREMagEncoderImpl(mTalon, CTREMagEncoder.SRX_PPR, revolutionToDistance);
    }
}
