package com.flash3388.flashlib.frc.robot.modes;

import com.flash3388.flashlib.robot.modes.RobotMode;
import com.flash3388.flashlib.robot.modes.RobotModeSupplier;
import edu.wpi.first.wpilibj.DriverStation;

public class FrcRobotModeSupplier implements RobotModeSupplier {

    private final DriverStation mDriverStation;

    public FrcRobotModeSupplier(DriverStation driverStation) {
        mDriverStation = driverStation;
    }

    @Override
    public RobotMode getMode() {
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
