package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.io.devices.DoubleSolenoid;

public class FrcDoubleSolenoid implements DoubleSolenoid {

    private final edu.wpi.first.wpilibj.DoubleSolenoid mSolenoid;

    public FrcDoubleSolenoid(edu.wpi.first.wpilibj.DoubleSolenoid solenoid) {
        mSolenoid = solenoid;
    }

    public FrcDoubleSolenoid(int forwardChannel, int reverseChannel) {
        this(new edu.wpi.first.wpilibj.DoubleSolenoid(forwardChannel, reverseChannel));
    }

    public FrcDoubleSolenoid(int moduleNumber, int forwardChannel, int reverseChannel) {
        this(new edu.wpi.first.wpilibj.DoubleSolenoid(moduleNumber, forwardChannel, reverseChannel));
    }

    @Override
    public void set(Value value) {
        switch (value) {
            case FORWARD:
                mSolenoid.set(edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward);
                break;
            case REVERSE:
                mSolenoid.set(edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse);
                break;
            case OFF:
                mSolenoid.set(edu.wpi.first.wpilibj.DoubleSolenoid.Value.kOff);
                break;
            default:
                throw new AssertionError("unknown value");
        }
    }

    @Override
    public Value get() {
        edu.wpi.first.wpilibj.DoubleSolenoid.Value value = mSolenoid.get();
        switch (value) {
            case kOff:
                return Value.OFF;
            case kForward:
                return Value.FORWARD;
            case kReverse:
                return Value.REVERSE;
            default:
                throw new AssertionError("unknown value");
        }
    }
}
