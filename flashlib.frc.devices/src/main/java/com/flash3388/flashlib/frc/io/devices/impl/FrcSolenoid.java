package com.flash3388.flashlib.frc.io.devices.impl;

import com.flash3388.flashlib.io.devices.NamedArg;
import com.flash3388.flashlib.io.devices.Solenoid;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class FrcSolenoid implements Solenoid {

    private final edu.wpi.first.wpilibj.Solenoid mSolenoid;

    public FrcSolenoid(edu.wpi.first.wpilibj.Solenoid solenoid) {
        mSolenoid = solenoid;
    }

    public FrcSolenoid(
            @NamedArg("moduleType") PneumaticsModuleType moduleType,
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.Solenoid(moduleType, channel));
    }

    public FrcSolenoid(
            @NamedArg("moduleNumber") int moduleNumber,
            @NamedArg("moduleType") PneumaticsModuleType moduleType,
            @NamedArg("channel") int channel
    ) {
        this(new edu.wpi.first.wpilibj.Solenoid(moduleNumber, moduleType, channel));
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
