package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.PulseCounter;
import edu.wpi.first.wpilibj.DigitalSource;

import java.io.IOException;

public class RoboRioPulseCounter implements PulseCounter {

    private edu.wpi.first.wpilibj.Counter mCounter;

    public RoboRioPulseCounter(int channel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(channel);
        mCounter.setUpDownCounterMode();
    }

    public RoboRioPulseCounter(DigitalSource channel) {
        mCounter = new edu.wpi.first.wpilibj.Counter();
        mCounter.setUpSource(channel);
        mCounter.setUpDownCounterMode();
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
