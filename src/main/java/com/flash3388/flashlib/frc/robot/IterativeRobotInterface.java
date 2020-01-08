package com.flash3388.flashlib.frc.robot;

public interface IterativeRobotInterface {
    void disabledInit();
    void disabledPeriodic();

    void teleopInit();
    void teleopPeriodic();

    void autonomousInit();
    void autonomousPeriodic();

    void testInit();
    void testPeriodic();

    void robotPeriodic();
}
