package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.PulseLengthCounter;
import edu.wpi.first.wpilibj.DigitalSource;

import java.io.IOException;

public class RoboRioPulseLengthCounter implements PulseLengthCounter {

    private edu.wpi.first.wpilibj.Counter mCounter;

    public RoboRioPulseLengthCounter(int channel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(channel);
        mCounter.setSemiPeriodMode(true);
    }

    public RoboRioPulseLengthCounter(DigitalSource channel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(channel);
        mCounter.setSemiPeriodMode(true);
    }

    @Override
    public double getLengthSeconds() {
        return mCounter.getPeriod();
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
