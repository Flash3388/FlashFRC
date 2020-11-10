package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.base.iterative.LoopingRobotControl;
import com.flash3388.flashlib.util.resources.ResourceHolder;
import edu.wpi.first.wpilibj.RobotBase;

public class RobotMain {

    private RobotMain() {}

    public static void start(IterativeFrcRobot.Initializer initializer, RobotConfiguration configuration) {
        ResourceHolder resourceHolder = ResourceHolder.empty();

        FrcRobotControl robotControl = new FrcRobotControlImpl(resourceHolder, configuration);
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl, resourceHolder));
    }

    public static void start(IterativeFrcRobot.Initializer initializer) {
        ResourceHolder resourceHolder = ResourceHolder.empty();

        FrcRobotControl robotControl = new FrcRobotControlImpl(resourceHolder);
        RobotBase.startRobot(()-> new LoopingRobotControl(initializer, robotControl, resourceHolder));
    }
}
