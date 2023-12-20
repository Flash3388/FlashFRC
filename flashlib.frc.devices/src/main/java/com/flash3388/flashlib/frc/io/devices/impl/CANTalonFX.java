package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.io.devices.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRESensors;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class CANTalonFX extends CTREBaseTalon {

    private CANTalonFX(WPI_TalonFX talonFX) {
        super(talonFX, talonFX);
    }

    @DeviceConstructor
    public CANTalonFX(
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
