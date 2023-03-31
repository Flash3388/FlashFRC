package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class RoboRioCounter implements Counter {

    private edu.wpi.first.wpilibj.Counter mCounter;
    private final boolean mIsQuadrature;

    public RoboRioCounter(edu.wpi.first.wpilibj.Counter counter, boolean isQuadrature) {
        mCounter = counter;
        mIsQuadrature = isQuadrature;
    }

    public static RoboRioCounter pulseLength(DigitalInput port, double threshold) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(port);

        counter.setPulseLengthMode(threshold);

        return new RoboRioCounter(counter, false);
    }

    public static RoboRioCounter pulseCount(DigitalInput port) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(port);

        counter.setUpDownCounterMode();
        counter.setSemiPeriodMode(false);

        return new RoboRioCounter(counter, false);
    }

    public static RoboRioCounter quadrature(DigitalInput upPort, DigitalInput downPort) {
        edu.wpi.first.wpilibj.Counter counter = new edu.wpi.first.wpilibj.Counter();
        counter.setUpSource(upPort);
        counter.setDownSource(downPort);

        counter.setUpDownCounterMode();
        counter.setSemiPeriodMode(false);

        return new RoboRioCounter(counter, true);
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
    public void close() {
        if (mCounter == null) {
            return;
        }

        mCounter.close();
        mCounter = null;
    }
}
