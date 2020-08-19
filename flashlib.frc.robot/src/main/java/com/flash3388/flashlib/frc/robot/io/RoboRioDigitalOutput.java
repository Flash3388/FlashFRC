package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.io.DigitalOutput;

public class RoboRioDigitalOutput implements DigitalOutput {

    private edu.wpi.first.wpilibj.DigitalOutput mDigitalOutput;

    public RoboRioDigitalOutput(edu.wpi.first.wpilibj.DigitalOutput digitalOutput) {
        mDigitalOutput = digitalOutput;
    }

    public RoboRioDigitalOutput(int port) {
        this(new edu.wpi.first.wpilibj.DigitalOutput(port));
    }

    @Override
    public boolean get() {
        return mDigitalOutput.get();
    }

    @Override
    public void set(boolean value) {
        mDigitalOutput.set(value);
    }

    @Override
    public void pulse(double pulseLength) {
        mDigitalOutput.pulse(pulseLength);
    }

    @Override
    public void close() {
        if (mDigitalOutput == null) {
            return;
        }

        mDigitalOutput.close();
        mDigitalOutput = null;
    }
}
