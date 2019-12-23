package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.robot.io.AnalogAccumulator;
import com.flash3388.flashlib.robot.io.AnalogInput;

public class FrcAnalogInput implements AnalogInput {

    private edu.wpi.first.wpilibj.AnalogInput mAnalogInput;
    private FrcAnalogAccumulator mAnalogAccumulator;

    public FrcAnalogInput(edu.wpi.first.wpilibj.AnalogInput analogInput) {
        mAnalogInput = analogInput;
        mAnalogAccumulator = new FrcAnalogAccumulator(analogInput);
    }

    public FrcAnalogInput(int port) {
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
    public AnalogAccumulator getAccumulator() {
        return mAnalogAccumulator;
    }

    @Override
    public double getSampleRate() {
        return edu.wpi.first.wpilibj.AnalogInput.getGlobalSampleRate();
    }

    @Override
    public double getMaxVoltage() {
        return FrcAnalogPort.MAX_VOLTAGE;
    }

    @Override
    public int getMaxValue() {
        return FrcAnalogPort.MAX_VALUE;
    }

    @Override
    public void free() {
        if (mAnalogInput == null) {
            return;
        }

        mAnalogInput.close();
        mAnalogAccumulator.disable();

        mAnalogInput = null;
        mAnalogAccumulator = null;
    }
}