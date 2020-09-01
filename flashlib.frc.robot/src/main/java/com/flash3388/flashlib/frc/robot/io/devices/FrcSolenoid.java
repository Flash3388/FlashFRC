package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.io.devices.Solenoid;
import com.flash3388.flashlib.time.Time;

public class FrcSolenoid implements Solenoid {

    private final edu.wpi.first.wpilibj.Solenoid mSolenoid;

    public FrcSolenoid(edu.wpi.first.wpilibj.Solenoid solenoid) {
        mSolenoid = solenoid;
    }

    @Override
    public void set(boolean on) {
        mSolenoid.set(on);
    }

    @Override
    public boolean get() {
        return mSolenoid.get();
    }

    @Override
    public void pulse(Time duration) {
        mSolenoid.setPulseDuration(duration.valueAsSeconds());
    }
}
