package com.flash3388.flashlib.frc.robot.simulation;

import com.flash3388.flashlib.time.Time;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;

public class TankDriveSim {

    private final DifferentialDrivetrainSim mDrivetrainSim;

    public TankDriveSim(DCMotor motors,
                        double driveMotorToWheelGearRatio,
                        double driveMomentOfInertia,
                        double weightKg,
                        double driveWheelRadiusM,
                        double driveTrackWidthM) {
        mDrivetrainSim = new DifferentialDrivetrainSim(
                motors,
                driveMotorToWheelGearRatio,
                driveMomentOfInertia,
                weightKg,
                driveWheelRadiusM,
                driveTrackWidthM,
                null
        );
    }

    public Pose2d getPose() {
        return mDrivetrainSim.getPose();
    }

    public double getLeftPositionMeters() {
        return mDrivetrainSim.getLeftPositionMeters();
    }

    public double getRightPositionMeters() {
        return mDrivetrainSim.getRightPositionMeters();
    }

    public double getAngleDegrees() {
        return getPose().getRotation().getDegrees();
    }

    public void update(double rightVoltage, double leftVoltage, Time dt) {
        mDrivetrainSim.setInputs(rightVoltage, leftVoltage);
        mDrivetrainSim.update(dt.valueAsSeconds());
    }
}
