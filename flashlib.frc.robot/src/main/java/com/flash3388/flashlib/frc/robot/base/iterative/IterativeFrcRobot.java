package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.robot.RobotInitializationException;

public interface IterativeFrcRobot {

    @FunctionalInterface
    interface Initializer {
        IterativeFrcRobot init(FrcRobotControl robotControl) throws RobotInitializationException;
    }

    void disabledInit();
    void disabledPeriodic();

    void teleopInit();
    void teleopPeriodic();

    void autonomousInit();
    void autonomousPeriodic();

    void testInit();
    void testPeriodic();

    void robotPeriodic();
    void robotStop();
}
