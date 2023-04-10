package frc.team3388.robot;

import com.flash3388.flashlib.frc.io.devices.impl.FrcSpeedController;
import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.io.devices.SpeedControllers;
import com.flash3388.flashlib.hid.Joystick;
import com.flash3388.flashlib.hid.JoystickAxis;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import com.flash3388.flashlib.robot.motion.actions.MoveAction;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.systems.MotorSystem;
import com.flash3388.flashlib.robot.systems.TankDriveSystem;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final TankDriveSystem mDriveSystem;
    private final MotorSystem mShooter;

    private final Joystick mStickRight;
    private final Joystick mStickLeft;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        // Creating the tank drive system.
        // We define the speed controllers for each side, there are 2 per each.
        mDriveSystem = new TankDriveSystem(
                new SpeedControllers()
                        .add(new PWMTalonSRX(RobotMap.DRIVE_FRONT_RIGHT))
                        .add(new PWMTalonSRX(RobotMap.DRIVE_BACK_RIGHT))
                        .build(),
                new SpeedControllers()
                        .add(new PWMTalonSRX(RobotMap.DRIVE_FRONT_LEFT))
                        .add(new PWMTalonSRX(RobotMap.DRIVE_BACK_LEFT))
                        .build()
        );

        // Creating another system, a shooter with a single motor.
        mShooter = new MotorSystem(
                new FrcSpeedController(new PWMTalonSRX(RobotMap.SHOOTER))
        );

        // Creating the joysticks.
        // We will use these joysticks to control the motions of the drive system.
        mStickRight = getHidInterface().newJoystick(RobotMap.STICK_RIGHT);
        mStickLeft = getHidInterface().newJoystick(RobotMap.STICK_LEFT);

        // We set the default action for the drive system.
        // This action, will run by default on the system.
        // Using it, we can define the default behaviour for the system.
        // In this case, the default behaviour is to move the system as such:
        //      - Right joystick - Y axis: move right side of drive
        //      - Left joystick - Y axis: move left side of drive
        //
        // TankDriveAction defines mDriveSystem as a requirements in
        // the constructor, using `requires` call. This is superrrrr important.
        // It defines to the Scheduler (which runs the actions) that mDriveSystem is used
        // in this action. Using this, the Scheduler will prevent two actions from using the
        // drive system at the same time.
        //
        // It is important to make sure that the action we are using does that.
        // It is something that all actions from FlashLib guarantee.
        mDriveSystem.setDefaultAction(mDriveSystem.tankDrive(
                mStickRight.getAxis(JoystickAxis.Y),
                mStickLeft.getAxis(JoystickAxis.Y)));

        // The shooter doesn't need to have a default action.
        // No system technically has to have, it's a choice.
        //
        // In this example, we'll use button presses to control the shooter.
        // The RotateAction will rotate the motor in a given speed (100%).
        //
        // While the 0 button (usually the trigger) is held the joystick, this action
        // will run, rotating the motor causing the system to fire.
        mStickRight.getButton(0).whileActive(mShooter.rotate(1.0));


        // Let's try using the systems together.
        // There are several ways to do so, and it mostly depends on
        // on how we want the robot to physically move/act.
        //
        // In this example, we'll make both systems act at the same time,
        // the drive system move forward and the shooter firing.
        // This will continue for a few seconds and then stop.
        // A sort of "insane action"
        //
        // Using alongWith, we can define an action to run
        // at the same time as another. This effectively groups them
        // together.
        //
        // withTimeout defines a timeout for the action. This timeout
        // is measured from the start of the action. Once that time has passed,
        // the action will be canceled, and stop running.
        //
        // The requirements for the action group will be both
        // the requirements of the MoveAction and of the RotateAction.
        mStickRight.getButton(1).whenActive(
                mDriveSystem.move(1.0)
                    .alongWith(mShooter.rotate(1.0))
                    .withTimeout(Time.seconds(2.0)));
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
