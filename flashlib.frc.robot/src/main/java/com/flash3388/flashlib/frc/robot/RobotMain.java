package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.base.iterative.LoopingRobotControl;
import com.flash3388.flashlib.robot.RobotControl;
import edu.wpi.first.wpilibj.RobotBase;

public class RobotMain {

    private RobotMain() {}

    public static void start(IterativeFrcRobot.Initializer initializer, RobotConfiguration configuration) {
        RobotControl robotControl = new FrcRobotControl(configuration);
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl));
    }

    public static void start(IterativeFrcRobot.Initializer initializer) {
        RobotControl robotControl = new FrcRobotControl();
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl));
    }
}
