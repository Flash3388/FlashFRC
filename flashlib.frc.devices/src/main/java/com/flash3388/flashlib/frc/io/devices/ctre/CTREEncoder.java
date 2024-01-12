package com.flash3388.flashlib.frc.io.devices.ctre;

import com.flash3388.flashlib.io.devices.RelativeEncoder;

public interface CTREEncoder extends RelativeEncoder {

    void setPositionDegrees(double degrees);

    @Override
    default void reset() {
        setPositionDegrees(0);
    }
}
