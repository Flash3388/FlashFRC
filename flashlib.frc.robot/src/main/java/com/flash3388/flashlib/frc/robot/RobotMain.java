package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.base.iterative.LoopingRobotControl;
import edu.wpi.first.wpilibj.RobotBase;

public class RobotMain {

    private RobotMain() {}

    public static void start(IterativeFrcRobot.Initializer initializer, RobotConfiguration configuration) {
        FrcRobotControl robotControl = new FrcRobotControlImpl(configuration);
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl));
    }

    public static void start(IterativeFrcRobot.Initializer initializer) {
        FrcRobotControl robotControl = new FrcRobotControlImpl();
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl));
    }
}
