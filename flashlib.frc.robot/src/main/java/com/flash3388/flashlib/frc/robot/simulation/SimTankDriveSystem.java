package com.flash3388.flashlib.frc.robot.simulation;

import com.flash3388.flashlib.frc.robot.RunningFrcRobot;
import com.flash3388.flashlib.frc.robot.simulation.TankDriveSim;
import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.scheduling.actions.ActionFlag;
import com.flash3388.flashlib.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.RobotController;

import java.util.Objects;

public class SimTankDriveSystem extends TankDriveSystem {

    private final TankDriveSim mDriveSim;

    public SimTankDriveSystem(SpeedController rightController, SpeedController leftController, TankDriveSim driveSim) {
        super(rightController, leftController);
        mDriveSim = Objects.requireNonNull(driveSim);

        if (RunningFrcRobot.getControl().isRuntimeSimulation()) {
            // this action will update or sim data
            Actions.fromRunnable(this::updateSim)
                    .configure()
                        .setName("SimTankDriveSystem-SimUpdater")
                        .addFlags(ActionFlag.RUN_ON_DISABLED)
                        .save()
                    .start();
        }
    }

    public Pose2d getSimPose() {
        assertInSimMode();
        return mDriveSim.getPose();
    }

    public double getSimLeftPositionMeters() {
        assertInSimMode();
        return mDriveSim.getLeftPositionMeters();
    }

    public double getSimRightPositionMeters() {
        assertInSimMode();
        return mDriveSim.getRightPositionMeters();
    }

    public double getSimAngleDegrees() {
        assertInSimMode();
        return mDriveSim.getAngleDegrees();
    }

    private void updateSim() {
        double rightVolts = getRightController().get() * RobotController.getBatteryVoltage();
        double leftVolts = getLeftController().get() * RobotController.getBatteryVoltage();

        // expected loop time for actions
        mDriveSim.update(rightVolts, leftVolts, Time.milliseconds(20));
    }

    private void assertInSimMode() {
        if (!RunningFrcRobot.getControl().isRuntimeSimulation()) {
            throw new IllegalStateException("cannot call sim-only methods when runtime is not simulation");
        }
    }
}
