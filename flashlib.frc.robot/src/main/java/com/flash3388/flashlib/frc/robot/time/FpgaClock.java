package com.flash3388.flashlib.frc.robot.time;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.RobotController;

import java.util.concurrent.TimeUnit;

public class FpgaClock implements Clock {

    @Override
    public Time currentTime() {
        return Time.of(RobotController.getFPGATime(), TimeUnit.MICROSECONDS);
    }
}
