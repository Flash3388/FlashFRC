package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.robot.io.AnalogAccumulator;
import com.flash3388.flashlib.robot.io.AnalogInput;

import java.util.concurrent.atomic.AtomicReference;

public class RoboRioAnalogInput implements AnalogInput {

    private edu.wpi.first.wpilibj.AnalogInput mAnalogInput;
    private volatile AnalogAccumulator mAccumulator;

    public RoboRioAnalogInput(edu.wpi.first.wpilibj.AnalogInput analogInput) {
        mAnalogInput = analogInput;
        mAccumulator = null;
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
    public AnalogAccumulator getAccumulator() {
        if (mAccumulator == null) {
            synchronized (this) {
                if (mAccumulator == null) {
                    mAnalogInput.initAccumulator();
                    mAccumulator = new RoboRioAnalogAccumulator(mAnalogInput);
                }
            }
        }

        return mAccumulator;
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
    public void free() {
        if (mAnalogInput == null) {
            return;
        }

        mAnalogInput.close();
        mAnalogInput = null;

        if (mAccumulator != null) {
            mAccumulator.disable();
            mAccumulator = null;
        }
    }
}
