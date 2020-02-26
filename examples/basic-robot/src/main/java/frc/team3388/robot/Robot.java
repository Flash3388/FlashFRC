package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.base.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.frc.robot.systems.Systems;
import com.flash3388.flashlib.robot.DelegatingRobotControl;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final TankDriveSystem mDriveSystem;

    private final Joystick mStickRight;
    private final Joystick mStickLeft;

    public Robot(RobotControl robotControl) {
        super(robotControl);

        mDriveSystem = Systems.newTankDrive()
                .right(new PWMTalonSRX(RobotMap.DRIVE_FRONT_RIGHT))
                .right(new PWMTalonSRX(RobotMap.DRIVE_BACK_RIGHT))
                .left(new PWMTalonSRX(RobotMap.DRIVE_FRONT_LEFT))
                .left(new PWMTalonSRX(RobotMap.DRIVE_BACK_LEFT))
                .build();

        mStickRight = new Joystick(RobotMap.STICK_RIGHT);
        mStickLeft = new Joystick(RobotMap.STICK_LEFT);

        mDriveSystem.setDefaultAction(new TankDriveAction(mDriveSystem,
                mStickRight.getAxis(JoystickAxis.Y),
                mStickLeft.getAxis(JoystickAxis.Y))
                .requires(mDriveSystem));

        mStickLeft.getButton(0).whenPressed(Actions.instant(()-> {
            mStickRight.getAxis(JoystickAxis.Y).invert();
            mStickLeft.getAxis(JoystickAxis.Y).invert();
        }));
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
