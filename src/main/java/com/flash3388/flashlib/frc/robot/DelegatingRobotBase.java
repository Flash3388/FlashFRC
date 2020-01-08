package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.Robot;

import java.util.function.Function;

public class DelegatingRobotBase extends TimedFrcRobot {

    private final Function<Robot, IterativeRobotInterface> mInterfaceCreator;
    private IterativeRobotInterface mInterface;

    public DelegatingRobotBase(Function<Robot, IterativeRobotInterface> interfaceCreator,
                               RobotConfiguration robotConfiguration) {
        super(robotConfiguration);
        mInterfaceCreator = interfaceCreator;
        mInterface = null;
    }

    @Override
    protected void robotInit() {
        mInterface = mInterfaceCreator.apply(this);
    }

    @Override
    public void disabledInit() {
        mInterface.disabledInit();
    }

    @Override
    public void disabledPeriodic() {
        mInterface.disabledPeriodic();
    }

    @Override
    public void teleopInit() {
        mInterface.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        mInterface.teleopPeriodic();
    }

    @Override
    public void autonomousInit() {
        mInterface.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        mInterface.autonomousPeriodic();
    }

    @Override
    public void testInit() {
        mInterface.testInit();
    }

    @Override
    public void testPeriodic() {
        mInterface.testPeriodic();
    }

    @Override
    public void robotPeriodic() {
        mInterface.robotPeriodic();
    }
}
