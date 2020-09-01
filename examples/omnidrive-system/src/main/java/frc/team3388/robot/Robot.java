package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.systems.Systems;
import com.flash3388.flashlib.hid.Joystick;
import com.flash3388.flashlib.hid.JoystickAxis;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.systems.drive.OmniDriveSystem;
import com.flash3388.flashlib.robot.systems.drive.actions.OmniDriveAction;
import com.flash3388.flashlib.scheduling.actions.Actions;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final OmniDriveSystem mDriveSystem;

    private final Joystick mStick;

    public Robot(RobotControl robotControl) {
        super(robotControl);

        // Creating the omni drive system.
        // We define the speed controllers for each side, there is one per side, and four sides
        mDriveSystem = Systems.newOmniDrive()
                .front(new PWMTalonSRX(RobotMap.DRIVE_FRONT))
                .right(new PWMTalonSRX(RobotMap.DRIVE_RIGHT))
                .left(new PWMTalonSRX(RobotMap.DRIVE_LEFT))
                .back(new PWMTalonSRX(RobotMap.DRIVE_BACK))
                .build();

        // Creating the joystick.
        // We will use this joystick to control the motions of the drive system.
        mStick = getHidInterface().newJoystick(RobotMap.STICK);

        // We set the default action for the drive system.
        // This action, will run by default on the system.
        // Using it, we can define the default behaviour for the system.
        // In this case, the default behaviour is to move the system as such:
        //      - Joystick - Y axis: move the drive forward and backward (along Y axis)
        //      - Joystick - X axis: move the drive left and right (along X axis)
        //
        // OmniDriveAction defines mDriveSystem as a requirements in
        // the constructor, using `requires` call. This is superrrrr important.
        // It defines to the Scheduler (which runs the actions) that mDriveSystem is used
        // in this action. Using this, the Scheduler will prevent two actions from using the
        // drive system at the same time.
        //
        // It is important to make sure that the action we are using does that.
        // It is something that all actions from FlashLib guarantee.
        mDriveSystem.setDefaultAction(new OmniDriveAction(mDriveSystem,
                        mStick.getAxis(JoystickAxis.Y),
                        mStick.getAxis(JoystickAxis.X)));

        // We can define actions to be executed when a button is pressed.
        // Here we define that when button number 0 (labeled on the joystick) is pressed,
        // the joystick axes are inverted, making forwards backwards, and backwards forwards.
        //
        // `Actions.instant` allows defining an actions which only runs once. The actual content
        // of the action, is defined in the lambda:
        // ()-> {
        //      // do stuff in action
        // }
        //
        // Because we defined that by default, the drive system runs an action that moves using
        // the joysticks; when this is pressed, we will have reverse motion from the drive system.
        mStick.getButton(0).whenActive(Actions.instant(()-> {
            mStick.getAxis(JoystickAxis.Y).invert();
            mStick.getAxis(JoystickAxis.X).invert();
        }));

        // This time we use `whileActive`. This means that the action defined, will run while the
        // button is held, stopping when it is released.
        //
        // Using this mechanism, we define a rotation action, which will allow us to rotate the robot
        // in place, around its center axis.
        //
        // When the button is released, we will return to the default action.
        mStick.getButton(0).whileActive(new RotateAction(mDriveSystem,
                mStick.getAxis(JoystickAxis.X)));
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
