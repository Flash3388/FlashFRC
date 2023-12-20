package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.QuadratureCounter;
import edu.wpi.first.wpilibj.DigitalSource;

import java.io.IOException;

public class RoboRioQuadratureCounter implements QuadratureCounter {

    private edu.wpi.first.wpilibj.Counter mCounter;

    public RoboRioQuadratureCounter(int upChannel, int downChannel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(upChannel);
        mCounter.setDownSource(downChannel);
        mCounter.setUpDownCounterMode();
    }

    public RoboRioQuadratureCounter(DigitalSource upChannel, DigitalSource downChannel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(upChannel);
        mCounter.setDownSource(downChannel);
        mCounter.setUpDownCounterMode();
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
    public void reset() {
        mCounter.reset();
    }

    @Override
    public int get() {
        return mCounter.get();
    }

    @Override
    public void close() throws IOException {
        if (mCounter == null) {
            return;
        }

        mCounter.close();
        mCounter = null;
    }
}
