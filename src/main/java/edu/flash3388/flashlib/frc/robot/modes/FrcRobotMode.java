package edu.flash3388.flashlib.frc.robot.modes;

import edu.flash3388.flashlib.robot.modes.RobotMode;

public class FrcRobotMode {

    private FrcRobotMode() {}

    public static final RobotMode DISABLED = RobotMode.DISABLED;
    public static final RobotMode OPERATOR_CONTROL = new RobotMode(1);
    public static final RobotMode AUTONOMOUS = new RobotMode(2);
    public static final RobotMode TEST = new RobotMode(3);
}
