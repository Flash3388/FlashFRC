package edu.flash3388.flashlib.frc.robot.time;

import edu.flash3388.flashlib.time.Clock;
import edu.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.Timer;

public class FrcHalClock implements Clock {

    @Override
    public Time currentTime() {
        double timestampSeconds = Timer.getFPGATimestamp();
        return Time.milliseconds((long) (timestampSeconds * 1000));
    }
}
