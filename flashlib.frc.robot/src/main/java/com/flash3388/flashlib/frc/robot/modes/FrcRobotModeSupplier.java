package com.flash3388.flashlib.frc.robot.modes;

import com.flash3388.flashlib.robot.modes.RobotModeSupplier;
import edu.wpi.first.wpilibj.DriverStation;

public class FrcRobotModeSupplier implements RobotModeSupplier {

    @Override
    public FrcRobotMode get() {
        if (DriverStation.isDisabled()) {
            return FrcRobotMode.DISABLED;
        }

        if (DriverStation.isAutonomous()) {
            return FrcRobotMode.AUTONOMOUS;
        }
        if (DriverStation.isTeleop()) {
            return FrcRobotMode.OPERATOR_CONTROL;
        }
        if (DriverStation.isTest()) {
            return FrcRobotMode.TEST;
        }

        return FrcRobotMode.DISABLED;
    }
}
