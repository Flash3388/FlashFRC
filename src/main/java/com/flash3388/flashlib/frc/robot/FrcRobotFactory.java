package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import com.flash3388.flashlib.time.Clock;

public class FrcRobotFactory {

    private FrcRobotFactory() {}

    public static Clock newDefaultClock() {
        return new FpgaClock();
    }
}
