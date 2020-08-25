package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.RoboRio;
import com.flash3388.flashlib.hid.HidChannel;

public class RobotMap {

    public static final int DRIVE_FRONT_RIGHT = 0;
    public static final int DRIVE_BACK_RIGHT = 1;
    public static final int DRIVE_FRONT_LEFT = 2;
    public static final int DRIVE_BACK_LEFT = 3;

    public static final HidChannel XBOX = RoboRio.newHidChannel(0);
}
