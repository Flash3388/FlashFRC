package com.flash3388.flashlib.frc.robot.io;

import edu.flash3388.flashlib.robot.io.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class FrcCounter implements Counter {

    private edu.wpi.first.wpilibj.Counter mCounter;
    private final boolean mIsQuadrature;

    public FrcCounter(edu.wpi.first.wpilibj.Counter counter, boolean isQuadrature) {
        mCounter = counter;
        mIsQuadrature = isQuadrature;
    }

    public static FrcCounter pulseLength(DigitalInput port, double threshold) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(port);

        counter.setPulseLengthMode(threshold);

        return new FrcCounter(counter, false);
    }

    public static FrcCounter pulseCount(DigitalInput port) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(port);

        counter.setUpDownCounterMode();
        counter.setSemiPeriodMode(false);

        return new FrcCounter(counter, false);
    }

    public static FrcCounter quadrature(DigitalInput upPort, DigitalInput downPort) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(upPort);
        counter.setDownSource(downPort);

        counter.setUpDownCounterMode();
        counter.setSemiPeriodMode(false);

        return new FrcCounter(counter, true);
    }

    @Override
    public void reset() {
        mCounter.reset();
    }

    @Override
    public int get() {
        return mCounter.get();
    }

    @Override
    public double getPulseLength() {
        return mCounter.getPeriod();
    }

    @Override
    public double getPulsePeriod() {
        return mCounter.getPeriod();
    }

    @Override
    public boolean getDirection() {
        return mCounter.getDirection();
    }

    @Override
    public boolean isQuadrature() {
        return mIsQuadrature;
    }

    @Override
    public void free() {
        if (mCounter == null) {
            return;
        }

        mCounter.close();
        mCounter = null;
    }
}
