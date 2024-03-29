package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.DigitalInput;
import com.flash3388.flashlib.io.PulseCounter;
import com.flash3388.flashlib.io.PulseLengthCounter;

public class RoboRioDigitalInput implements DigitalInput {

    private edu.wpi.first.wpilibj.DigitalInput mDigitalInput;

    public RoboRioDigitalInput(edu.wpi.first.wpilibj.DigitalInput digitalInput) {
        mDigitalInput = digitalInput;
    }

    public RoboRioDigitalInput(int port) {
        this(new edu.wpi.first.wpilibj.DigitalInput(port));
    }

    @Override
    public boolean get() {
        return mDigitalInput.get();
    }

    @Override
    public PulseCounter createCounter() {
        return new RoboRioPulseCounter(mDigitalInput);
    }

    @Override
    public PulseLengthCounter createLengthCounter() {
        return new RoboRioPulseLengthCounter(mDigitalInput);
    }

    @Override
    public void close() {
        if (mDigitalInput == null) {
            return;
        }

        mDigitalInput.close();
        mDigitalInput = null;
    }
}
