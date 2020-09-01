package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.io.RoboRioChannel;
import com.flash3388.flashlib.hid.HidChannel;
import com.flash3388.flashlib.hid.generic.GenericHidChannel;
import com.flash3388.flashlib.io.IoChannel;

public class RoboRio {

    private RoboRio() {}

    public static IoChannel newIoChannel(int channel) {
        return new RoboRioChannel(channel);
    }

    public static HidChannel newHidChannel(int channel) {
        return new GenericHidChannel(channel);
    }
}
