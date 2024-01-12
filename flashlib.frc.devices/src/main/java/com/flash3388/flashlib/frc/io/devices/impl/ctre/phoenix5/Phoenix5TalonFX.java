package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRESensors;
import com.flash3388.flashlib.frc.io.devices.impl.CTREEncoderImpl;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class Phoenix5TalonFX extends Phoenix5TalonBase {

    public Phoenix5TalonFX(WPI_TalonFX talon) {
        super(talon);
    }

    @DeviceConstructor
    public Phoenix5TalonFX(
            @NamedArg("channel") int channel
    ) {
        this(new WPI_TalonFX(channel));
    }

    @Override
    public CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius) {
        mTalon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, slotIdx, 0);
        return new CTREEncoderImpl(mTalon, CTRESensors.TALONFX_PPR, slotIdx, gearRatio, wheelRadius);
    }
}
