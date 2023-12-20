package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.io.devices.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRESensors;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonSRX extends CTREBaseTalon {

    private CANTalonSRX(WPI_TalonSRX talonSRX) {
        super(talonSRX, talonSRX);
    }

    @DeviceConstructor
    public CANTalonSRX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonSRX(channel));
    }

    @Override
    public CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius) {
        mTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, slotIdx, 0);
        return new CTREEncoderImpl(mTalon, CTRESensors.SRX_ENCODER_PPR, slotIdx, gearRatio, wheelRadius);
    }
}
