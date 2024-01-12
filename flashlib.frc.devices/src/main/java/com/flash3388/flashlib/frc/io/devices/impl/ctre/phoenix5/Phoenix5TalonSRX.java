package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class Phoenix5TalonSRX extends Phoenix5TalonBase {

    public Phoenix5TalonSRX(WPI_TalonSRX talon) {
        super(talon);
    }

    @DeviceConstructor
    public Phoenix5TalonSRX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonSRX(channel));
    }

    @Override
    public CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius) {
        mTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, slotIdx, 0);
        return new Phoenix5EncoderImpl(mTalon, CTRESensors.SRX_ENCODER_PPR, slotIdx, gearRatio, wheelRadius);
    }
}
