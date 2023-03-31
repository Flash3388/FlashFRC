package com.flash3388.flashlib.frc.io.devices.impl;

import com.flash3388.flashlib.io.devices.Accelerometer;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

import java.io.IOException;

public class AnalogAccelerometer implements Accelerometer {

    private final edu.wpi.first.wpilibj.AnalogAccelerometer mAccelerometer;

    public AnalogAccelerometer(edu.wpi.first.wpilibj.AnalogAccelerometer accelerometer) {
        mAccelerometer = accelerometer;
    }

    @DeviceConstructor
    public AnalogAccelerometer(
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.AnalogAccelerometer(channel));
    }

    @Override
    public double getAcceleration() {
        return mAccelerometer.getAcceleration();
    }

    @Override
    public void close() throws IOException {
        mAccelerometer.close();
    }
}
