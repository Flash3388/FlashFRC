package com.flash3388.flashlib.frc.robot.time;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.Timer;

public class FpgaClock implements Clock {

    @Override
    public Time currentTime() {
        double timestampSeconds = Timer.getFPGATimestamp();
        return Time.milliseconds((long) (timestampSeconds * 1000));
    }
}
