package com.flash3388.flashlib.frc.robot.modes;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.function.Supplier;

public class FrcRobotModeSupplier implements Supplier<FrcRobotMode> {

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
