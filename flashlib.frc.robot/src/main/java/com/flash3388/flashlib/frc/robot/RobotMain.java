package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.base.iterative.LoopingRobotBase;
import com.flash3388.flashlib.util.resources.ResourceHolder;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * Starter for FRC robot code. Should be called directly from the application main.
 *
 * @since FlashLib 2020.1.0
 */
public class RobotMain {

    private RobotMain() {}

    public static void start(IterativeFrcRobot.Initializer initializer, RobotConfiguration configuration) {
        RobotBase.startRobot(()-> {
            ResourceHolder resourceHolder = ResourceHolder.empty();

            FrcRobotControl robotControl = new FrcRobotControlImpl(resourceHolder, configuration);
            RunningFrcRobot.setControlInstance(robotControl);
            return new LoopingRobotBase(initializer, robotControl, resourceHolder);
        });
    }

    public static void start(IterativeFrcRobot.Initializer initializer) {
        start(initializer, RobotConfiguration.defaultConfiguration());
    }
}
