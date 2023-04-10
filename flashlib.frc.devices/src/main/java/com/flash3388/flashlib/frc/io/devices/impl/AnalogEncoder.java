package com.flash3388.flashlib.frc.io.devices.impl;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;
import com.flash3388.flashlib.io.devices.RelativeEncoder;

import java.io.IOException;

public class AnalogEncoder implements RelativeEncoder {

    private final edu.wpi.first.wpilibj.AnalogEncoder mEncoder;

    public AnalogEncoder(edu.wpi.first.wpilibj.AnalogEncoder encoder) {
        mEncoder = encoder;
    }

    @DeviceConstructor
    public AnalogEncoder(
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.AnalogEncoder(channel));
    }

    @Override
    public void reset() {
        mEncoder.reset();
    }

    @Override
    public double getPosition() {
        return mEncoder.get() * 360;
    }

    @Override
    public double getRate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getVelocity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getDistancePassed() {
        return mEncoder.getDistance();
    }

    @Override
    public void close() throws IOException {
        mEncoder.close();
    }
}
