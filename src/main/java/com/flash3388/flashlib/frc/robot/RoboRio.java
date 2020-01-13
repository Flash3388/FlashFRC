package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.io.RoboRioChannel;
import com.flash3388.flashlib.robot.io.IoChannel;

public class RoboRio {

    private RoboRio() {}

    public static IoChannel newChannel(int channel) {
        return new RoboRioChannel(channel);
    }
}
