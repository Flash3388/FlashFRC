package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.IterativeFrcRobot;
import edu.wpi.first.wpilibj.RobotBase;

public class RobotMain {

    private RobotMain() {}

    public static void start(IterativeFrcRobot.Initializer initializer, RobotConfiguration configuration) {
        RobotBase.startRobot(()->new IterativeRobotControl(configuration, initializer));
    }

    public static void start(IterativeFrcRobot.Initializer initializer) {
        RobotBase.startRobot(()->new IterativeRobotControl(initializer));
    }

    private static class IterativeRobotControl extends IntervalLoopingFrcRobotControl {

        public IterativeRobotControl(RobotConfiguration configuration, IterativeFrcRobot.Initializer robotInitializer) {
            super(configuration, robotInitializer);
        }

        public IterativeRobotControl(IterativeFrcRobot.Initializer robotInitializer) {
            super(robotInitializer);
        }
    }
}
