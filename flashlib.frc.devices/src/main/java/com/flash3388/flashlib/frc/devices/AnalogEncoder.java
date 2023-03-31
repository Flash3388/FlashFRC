package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.Encoder;
import com.flash3388.flashlib.io.devices.NamedArg;

import java.io.IOException;

public class AnalogEncoder implements Encoder {

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
    public int getRaw() {
        return (int) mEncoder.get();
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
    public double getDistance() {
        return mEncoder.getDistance();
    }

    @Override
    public boolean getDirection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        mEncoder.close();
    }
}
