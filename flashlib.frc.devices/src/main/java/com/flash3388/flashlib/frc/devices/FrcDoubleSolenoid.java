package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.DoubleSolenoid;
import com.flash3388.flashlib.io.devices.NamedArg;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class FrcDoubleSolenoid implements DoubleSolenoid {

    private final edu.wpi.first.wpilibj.DoubleSolenoid mSolenoid;

    public FrcDoubleSolenoid(edu.wpi.first.wpilibj.DoubleSolenoid solenoid) {
        mSolenoid = solenoid;
    }

    @DeviceConstructor
    public FrcDoubleSolenoid(
            @NamedArg("moduleType") PneumaticsModuleType moduleType,
            @NamedArg("forwardChannel") int forwardChannel,
            @NamedArg("reverseChannel") int reverseChannel
    ) {
        this(new edu.wpi.first.wpilibj.DoubleSolenoid(moduleType, forwardChannel, reverseChannel));
    }

    @DeviceConstructor
    public FrcDoubleSolenoid(
            @NamedArg("moduleNumber") int moduleNumber,
            @NamedArg("moduleType") PneumaticsModuleType moduleType,
            @NamedArg("forwardChannel") int forwardChannel,
            @NamedArg("reverseChannel") int reverseChannel
    ) {
        this(new edu.wpi.first.wpilibj.DoubleSolenoid(moduleNumber, moduleType, forwardChannel, reverseChannel));
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
