package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;
import com.flash3388.flashlib.io.devices.RangeFinder;

import java.io.IOException;

public class Ultrasonic implements RangeFinder {

    private final edu.wpi.first.wpilibj.Ultrasonic mUltrasonic;

    public Ultrasonic(edu.wpi.first.wpilibj.Ultrasonic ultrasonic) {
        mUltrasonic = ultrasonic;
    }

    @DeviceConstructor
    public Ultrasonic(
            @NamedArg("pingChannel") int pingChannel,
            @NamedArg("echoChannel") int echoChannel
    ) {
        this(new edu.wpi.first.wpilibj.Ultrasonic(pingChannel, echoChannel));
    }

    @Override
    public double getRangeCm() {
        return mUltrasonic.getRangeMM() / 10.0;
    }

    @Override
    public void close() throws IOException {
        mUltrasonic.close();
    }
}
