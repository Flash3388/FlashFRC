package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.RoboRio;
import com.flash3388.flashlib.hid.HidChannel;

public class RobotMap {

    public static final int DRIVE_RIGHT = 0;
    public static final int DRIVE_BACK = 1;
    public static final int DRIVE_FRONT = 2;
    public static final int DRIVE_LEFT = 3;

    public static final HidChannel STICK = RoboRio.newHidChannel(0);
}
