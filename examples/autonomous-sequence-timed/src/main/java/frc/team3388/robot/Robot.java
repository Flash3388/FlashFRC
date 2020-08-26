package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.systems.Systems;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final TankDriveSystem mDriveSystem;

    public Robot(RobotControl robotControl) {
        super(robotControl);

        // Creating the tank drive system.
        // We define the speed controllers for each side, there are 2 per each.
        mDriveSystem = Systems.newTankDrive()
                .right(new PWMTalonSRX(RobotMap.DRIVE_FRONT_RIGHT))
                .right(new PWMTalonSRX(RobotMap.DRIVE_BACK_RIGHT))
                .left(new PWMTalonSRX(RobotMap.DRIVE_FRONT_LEFT))
                .left(new PWMTalonSRX(RobotMap.DRIVE_BACK_LEFT))
                .build();

        // The idea of autonomous is to move/control/operate the robot, without any human interference or
        // interaction. Basically, without any controllers and joysticks (and other means of human direct or
        // indirect control).
        //
        // There are several ways to do that. In here we will see an example of time-based pre-defined motion.
        // Such autonomous define static robot motions, with certain velocities and time periods. Basically pre-planning
        // exact movements of the robot.
        // It is the simplest to make (in terms of code); however, it is highly inaccurate, since it relies on everything
        // acting perfectly. Something quite naive.
        //
        // Go to autonomousInit method.
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {
        // This method will run once when the autonomous mode starts. In here, we will define actions
        // to execute during the autonomous phase.

        // Let's define the following path:
        // - Move forward at 50% speed for 1 second.
        // - Rotate right at 30% speed for 2 seconds.
        // - Move forward at 100% speed for 1.4 seconds.
        new TankDriveAction(mDriveSystem, 0.5)
                // `withTimeout` sets a timeout for the action, from when it starts. When the timeout as passed,
                // the action will be canceled.
                .withTimeout(Time.seconds(1))
                //  `andThen` allows us to start adding actions to be executed
                // sequentially.
                .andThen(new RotateAction(mDriveSystem, 0.3)
                    .withTimeout(Time.seconds(2)))
                .andThen(new TankDriveAction(mDriveSystem, 1)
                    .withTimeout(Time.seconds(1.4)))
                // Once we finished defining the action, we call `start` to start the action.
                .start();
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
