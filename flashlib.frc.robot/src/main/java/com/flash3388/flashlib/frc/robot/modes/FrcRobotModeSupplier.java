package com.flash3388.flashlib.frc.robot.modes;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.function.Supplier;

public class FrcRobotModeSupplier implements Supplier<FrcRobotMode> {

    private final DriverStation mDriverStation;

    public FrcRobotModeSupplier(DriverStation driverStation) {
        mDriverStation = driverStation;
    }

    @Override
    public FrcRobotMode get() {
        if (mDriverStation.isDisabled()) {
            return FrcRobotMode.DISABLED;
        }

        if (mDriverStation.isAutonomous()) {
            return FrcRobotMode.AUTONOMOUS;
        }
        if (mDriverStation.isOperatorControl()) {
            return FrcRobotMode.OPERATOR_CONTROL;
        }
        if (mDriverStation.isTest()) {
            return FrcRobotMode.TEST;
        }

        return FrcRobotMode.DISABLED;
    }
}
