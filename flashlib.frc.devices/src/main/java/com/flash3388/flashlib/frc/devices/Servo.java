package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;
import com.flash3388.flashlib.io.devices.PositionController;

public class Servo implements PositionController {

    private final edu.wpi.first.wpilibj.Servo mServo;

    public Servo(edu.wpi.first.wpilibj.Servo servo) {
        mServo = servo;
    }

    @DeviceConstructor
    public Servo(
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.Servo(channel));
    }

    @Override
    public void set(double position) {
        mServo.set(position);
    }

    @Override
    public double get() {
        return mServo.get();
    }

    @Override
    public void stop() {
        mServo.setSpeed(0);
    }
}
