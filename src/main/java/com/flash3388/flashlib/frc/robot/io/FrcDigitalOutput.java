package com.flash3388.flashlib.frc.robot.io;

import edu.flash3388.flashlib.robot.io.DigitalOutput;

public class FrcDigitalOutput implements DigitalOutput {

    private edu.wpi.first.wpilibj.DigitalOutput mDigitalOutput;

    public FrcDigitalOutput(edu.wpi.first.wpilibj.DigitalOutput digitalOutput) {
        mDigitalOutput = digitalOutput;
    }

    public FrcDigitalOutput(int port) {
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
    public void free() {
        if (mDigitalOutput == null) {
            return;
        }

        mDigitalOutput.close();
        mDigitalOutput = null;
    }
}
