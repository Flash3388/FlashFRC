package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.OutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;

public interface InternalOutputSetter extends OutputSetter {

    double getLastSetOutput();
    OutputType getLastSetOutputType();

    void reset();
}
