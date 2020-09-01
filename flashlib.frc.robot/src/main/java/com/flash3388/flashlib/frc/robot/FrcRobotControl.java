package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.robot.RobotControl;

public interface FrcRobotControl extends RobotControl {

    RobotFileSystem getFileSystem();
}
