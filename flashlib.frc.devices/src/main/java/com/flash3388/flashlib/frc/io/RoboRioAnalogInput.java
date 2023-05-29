package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.AnalogInput;

public class RoboRioAnalogInput implements AnalogInput {

    private edu.wpi.first.wpilibj.AnalogInput mAnalogInput;

    public RoboRioAnalogInput(edu.wpi.first.wpilibj.AnalogInput analogInput) {
        mAnalogInput = analogInput;
    }

    public RoboRioAnalogInput(int port) {
        this(new edu.wpi.first.wpilibj.AnalogInput(port));
    }

    @Override
    public int getValue() {
        return mAnalogInput.getValue();
    }

    @Override
    public double getVoltage() {
        return mAnalogInput.getVoltage();
    }

    @Override
    public double getSampleRate() {
        return edu.wpi.first.wpilibj.AnalogInput.getGlobalSampleRate();
    }

    @Override
    public double getMaxVoltage() {
        return RoboRioAnalogPort.MAX_VOLTAGE;
    }

    @Override
    public int getMaxValue() {
        return RoboRioAnalogPort.MAX_VALUE;
    }

    @Override
    public void close() {
        if (mAnalogInput == null) {
            return;
        }

        mAnalogInput.close();
        mAnalogInput = null;
    }
}
