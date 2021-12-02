package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import com.flash3388.flashlib.scheduling.actions.Action;
import com.flash3388.flashlib.vision.control.VisionControl;
import com.flash3388.frc.nt.vision.NtRemoteVisionControl;
import frc.team3388.robot.actions.VisionAction;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    // VisionControl is an interface for object which control the running of vision code.
    // It allows configuring, listening and querying information about the code and its
    // results.
    // It has several implementations, to control code running in the current JVM instance,
    // or in "remotely".
    private final VisionControl mVisionControl;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        // NtRemoteVisionControl is a vision control implementation which controls
        // a remote vision code runner and uses networktables to communicate.
        // It is a simple implementation and useful when working in FRC.
        // "vision" is the networktables table use to store information.
        mVisionControl = new NtRemoteVisionControl(getClock(), "vision");

        // goto teleopInit
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {
        // let's start our mock action that uses vision.
        Action action = new VisionAction(mVisionControl);
        action.start();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void robotStop() {

    }
}
