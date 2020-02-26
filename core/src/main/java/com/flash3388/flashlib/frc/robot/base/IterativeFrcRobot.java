package com.flash3388.flashlib.frc.robot.base;

import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.RobotInitializationException;

public interface IterativeFrcRobot {

    @FunctionalInterface
    interface Initializer {
        IterativeFrcRobot init(RobotControl robotControl) throws RobotInitializationException;
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
