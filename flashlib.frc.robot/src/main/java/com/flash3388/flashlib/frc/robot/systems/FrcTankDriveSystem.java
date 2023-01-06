package com.flash3388.flashlib.frc.robot.systems;

import com.flash3388.flashlib.frc.robot.RunningFrcRobot;
import com.flash3388.flashlib.frc.robot.simulation.SimTankDriveSystem;
import com.flash3388.flashlib.frc.robot.simulation.TankDriveSim;
import com.flash3388.flashlib.io.devices.Encoder;
import com.flash3388.flashlib.io.devices.Gyro;
import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.scheduling.actions.ActionFlag;
import com.flash3388.flashlib.scheduling.actions.Actions;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

public class FrcTankDriveSystem extends SimTankDriveSystem {

    private final Encoder mRightEncoder;
    private final Encoder mLeftEncoder;
    private final Gyro mGyro;
    private final DifferentialDriveOdometry mOdometry;

    public FrcTankDriveSystem(SpeedController rightController,
                              SpeedController leftController,
                              Encoder rightEncoder,
                              Encoder leftEncoder,
                              Gyro gyro,
                              TankDriveSim driveSim) {
        super(rightController, leftController, driveSim);

        mRightEncoder = rightEncoder;
        mLeftEncoder = leftEncoder;
        mGyro = gyro;

        if (!RunningFrcRobot.getControl().isRuntimeSimulation()) {
            mOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), 0, 0);

            // this action will update our odometry data
            Actions.fromRunnable(this::update)
                    .configure()
                        .setName("FrcTankDriveSystem-Updater")
                        .addFlags(ActionFlag.RUN_ON_DISABLED)
                        .save()
                    .start();
        } else {
            mOdometry = null;
        }
    }

    public Pose2d getPose() {
        if (RunningFrcRobot.getControl().isRuntimeSimulation()) {
            return getSimPose();
        }

        return mOdometry.getPoseMeters();
    }

    public double getLeftPositionMeters() {
        if (RunningFrcRobot.getControl().isRuntimeSimulation()) {
            return getSimLeftPositionMeters();
        }

        return mLeftEncoder.getDistance();
    }

    public double getRightPositionMeters() {
        if (RunningFrcRobot.getControl().isRuntimeSimulation()) {
            return getSimRightPositionMeters();
        }

        return mRightEncoder.getDistance();
    }

    public double getAngleDegrees() {
        if (RunningFrcRobot.getControl().isRuntimeSimulation()) {
            return getSimAngleDegrees();
        }

        return mGyro.getAngle();
    }

    private void update() {
        mOdometry.update(
                Rotation2d.fromDegrees(getAngleDegrees()),
                getLeftPositionMeters(),
                getRightPositionMeters()
        );
    }
}
