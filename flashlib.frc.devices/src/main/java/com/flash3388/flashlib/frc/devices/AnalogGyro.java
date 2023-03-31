package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.Gyro;
import com.flash3388.flashlib.io.devices.NamedArg;

import java.io.IOException;

public class AnalogGyro implements Gyro {

    private final edu.wpi.first.wpilibj.AnalogGyro mGyro;

    public AnalogGyro(edu.wpi.first.wpilibj.AnalogGyro gyro) {
        mGyro = gyro;
    }

    @DeviceConstructor
    public AnalogGyro(
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.AnalogGyro(channel));
    }

    @Override
    public void reset() {
        mGyro.reset();
    }

    @Override
    public double getAngle() {
        return mGyro.getAngle();
    }

    @Override
    public double getRate() {
        return mGyro.getRate();
    }

    @Override
    public void close() throws IOException {
        mGyro.close();
    }
}
