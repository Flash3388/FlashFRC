package frc.team3388.robot;

import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import com.flash3388.flashlib.frc.io.devices.FrcDevicesIds;
import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.hid.Joystick;
import com.flash3388.flashlib.hid.JoystickAxis;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;

import java.util.Map;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final CTRETalon mDriveRight;
    private final CTRETalon mDriveLeft;

    private final Joystick mStickRight;
    private final Joystick mStickLeft;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        // Creating the speed controllers.
        // Each side of the drive has 2.
        // We use the DeviceInterface to create the controllers
        mDriveRight = getDeviceInterface().newDevice(
                FrcDevicesIds.CANTalonSRX,
                CTRETalon.class,
                Map.of("channel", 0)
        );
        mDriveLeft = getDeviceInterface().newDevice(
                FrcDevicesIds.CANTalonSRX,
                CTRETalon.class,
                Map.of("channel", 1)
        );

        // Creating the joysticks.
        // We will use these joysticks to control the motions of the drive system.
        mStickRight = getHidInterface().newJoystick(RobotMap.STICK_RIGHT);
        mStickLeft = getHidInterface().newJoystick(RobotMap.STICK_LEFT);

        // This example demonstrates user control using joysticks.
        // Because of that, we will use the teleop mode for the robot.
        // Go to teleopPeriodic.
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
        // This method is called periodically (times differ, but around 25ms).
        // In here we can implement control of the drive system using the
        // joystick. This control needs to run periodically to update with
        // changes to the joystick.

        // We get the Y axis value from the right joystick.
        // This will move the right side of the robot.
        double right = mStickRight.getAxis(JoystickAxis.Y).getAsDouble();
        mDriveRight.set(right);

        // We do the same for the left side.
        double left = mStickLeft.getAxis(JoystickAxis.Y).getAsDouble();
        mDriveLeft.set(left);
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
