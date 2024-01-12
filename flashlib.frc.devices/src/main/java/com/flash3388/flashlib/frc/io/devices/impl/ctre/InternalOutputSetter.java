package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.TalonOutputSetter;

public interface InternalOutputSetter extends TalonOutputSetter {

    double getLastSetOutput();
    OutputType getLastSetOutputType();

    void reset();
}
