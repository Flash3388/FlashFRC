package com.flash3388.flashlib.frc.io.devices.ctre;

public interface OutputSetter {

    // TODO: SUPPORT MORE FUNCTIONALITY

    OutputSetter neutral();
    OutputSetter precentVBus(double value);
    OutputSetter velocity(double value);
    OutputSetter position(double value);

    OutputSetter feedForward(double value);

    void set();
}
