package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.robot.io.DigitalInput;

public class FrcDigitalInput implements DigitalInput {

    private edu.wpi.first.wpilibj.DigitalInput mDigitalInput;

    public FrcDigitalInput(edu.wpi.first.wpilibj.DigitalInput digitalInput) {
        mDigitalInput = digitalInput;
    }

    public FrcDigitalInput(int port) {
        this(new edu.wpi.first.wpilibj.DigitalInput(port));
    }

    @Override
    public boolean get() {
        return mDigitalInput.get();
    }

    @Override
    public void free() {
        if (mDigitalInput == null) {
            return;
        }

        mDigitalInput.close();
        mDigitalInput = null;
    }
}
