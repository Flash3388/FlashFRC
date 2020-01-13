package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.robot.io.AnalogAccumulator;

public class RoboRioAnalogAccumulator implements AnalogAccumulator {

    private edu.wpi.first.wpilibj.AnalogInput mAnalogInput;

    public RoboRioAnalogAccumulator(edu.wpi.first.wpilibj.AnalogInput analogInput) {
        mAnalogInput = analogInput;
        mAnalogInput.initAccumulator();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void reset() {
        mAnalogInput.resetAccumulator();
    }

    @Override
    public void setCenter(int center) {
        mAnalogInput.setAccumulatorCenter(center);
    }

    @Override
    public long getValue() {
        return mAnalogInput.getAccumulatorValue();
    }

    @Override
    public int getCount() {
        return (int) mAnalogInput.getAccumulatorCount();
    }
}