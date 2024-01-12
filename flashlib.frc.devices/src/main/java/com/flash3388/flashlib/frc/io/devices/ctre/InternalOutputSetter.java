package com.flash3388.flashlib.frc.io.devices.ctre;

import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;

public interface InternalOutputSetter extends OutputSetter {

    double getLastSetOutput();
    OutputType getLastSetOutputType();

    void reset();
}
