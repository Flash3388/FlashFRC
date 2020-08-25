package com.flash3388.flashlib.frc.robot.systems;

import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedControllerGroup;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.SpeedControllers;
import com.flash3388.flashlib.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.io.devices.actuators.SpeedControllerGroup;
import com.flash3388.flashlib.robot.systems.SingleMotorSystem;
import com.flash3388.flashlib.robot.systems.drive.MecanumDriveSystem;
import com.flash3388.flashlib.robot.systems.drive.OmniDriveSystem;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Systems {

    private Systems() {}

    public static class TankDriveBuilder {
        private final SpeedControllers mRight = new SpeedControllers();
        private final SpeedControllers mLeft = new SpeedControllers();

        public TankDriveBuilder right(SpeedController... speedControllers) {
            mRight.add(speedControllers);
            return this;
        }
        public TankDriveBuilder right(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mRight.add(speedControllers);
            return this;
        }

        public TankDriveBuilder left(SpeedController... speedControllers) {
            mLeft.add(speedControllers);
            return this;
        }
        public TankDriveBuilder left(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mLeft.add(speedControllers);
            return this;
        }

        public TankDriveSystem build() {
            return new TankDriveSystem(
                    mRight.build(),
                    mLeft.build());
        }
    }

    public static TankDriveBuilder newTankDrive() {
        return new TankDriveBuilder();
    }

    public static class OmniDriveBuilder {
        private final SpeedControllers mRight = new SpeedControllers();
        private final SpeedControllers mLeft = new SpeedControllers();
        private final SpeedControllers mFront = new SpeedControllers();
        private final SpeedControllers mBack = new SpeedControllers();

        public OmniDriveBuilder right(SpeedController... speedControllers) {
            mRight.add(speedControllers);
            return this;
        }
        public OmniDriveBuilder right(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mRight.add(speedControllers);
            return this;
        }

        public OmniDriveBuilder left(SpeedController... speedControllers) {
            mLeft.add(speedControllers);
            return this;
        }
        public OmniDriveBuilder left(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mLeft.add(speedControllers);
            return this;
        }

        public OmniDriveBuilder front(SpeedController... speedControllers) {
            mFront.add(speedControllers);
            return this;
        }
        public OmniDriveBuilder front(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mFront.add(speedControllers);
            return this;
        }

        public OmniDriveBuilder back(SpeedController... speedControllers) {
            mBack.add(speedControllers);
            return this;
        }
        public OmniDriveBuilder back(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
            mBack.add(speedControllers);
            return this;
        }

        public OmniDriveSystem build() {
            return new OmniDriveSystem(
                    mFront.build(),
                    mRight.build(),
                    mBack.build(),
                    mLeft.build());
        }
    }

    public static OmniDriveBuilder newOmniDrive() {
        return new OmniDriveBuilder();
    }

    public static class MecanumDriveBuilder {
        private SpeedController mRightFront;
        private SpeedController mRightBack;
        private SpeedController mLeftFront;
        private SpeedController mLeftBack;

        public MecanumDriveBuilder rightFront(SpeedController speedController) {
            mRightFront = speedController;
            return this;
        }
        public MecanumDriveBuilder rightFront(edu.wpi.first.wpilibj.SpeedController speedController) {
            return rightFront(new FrcSpeedController(speedController));
        }

        public MecanumDriveBuilder rightBack(SpeedController speedController) {
            mRightBack = speedController;
            return this;
        }
        public MecanumDriveBuilder rightBack(edu.wpi.first.wpilibj.SpeedController speedController) {
            return rightBack(new FrcSpeedController(speedController));
        }

        public MecanumDriveBuilder leftFront(SpeedController speedController) {
            mLeftFront = speedController;
            return this;
        }
        public MecanumDriveBuilder leftFront(edu.wpi.first.wpilibj.SpeedController speedController) {
            return leftFront(new FrcSpeedController(speedController));
        }

        public MecanumDriveBuilder leftBack(SpeedController speedController) {
            mLeftBack = speedController;
            return this;
        }
        public MecanumDriveBuilder leftBack(edu.wpi.first.wpilibj.SpeedController speedController) {
            return leftBack(new FrcSpeedController(speedController));
        }

        public MecanumDriveSystem build() {
            return new MecanumDriveSystem(
                    Objects.requireNonNull(mRightFront, "right front null"),
                    Objects.requireNonNull(mRightBack, "right back null"),
                    Objects.requireNonNull(mLeftFront, "left front null"),
                    Objects.requireNonNull(mLeftBack, "left back null"));
        }
    }

    public MecanumDriveBuilder newMecanumDrive() {
        return new MecanumDriveBuilder();
    }

    public static SingleMotorSystem newSingleMotor(SpeedController speedController) {
        return new SingleMotorSystem(speedController);
    }

    public static SingleMotorSystem newSingleMotor(edu.wpi.first.wpilibj.SpeedController speedController) {
        return newSingleMotor(new FrcSpeedController(speedController));
    }

    public static SingleMotorSystem newSingleMotor(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
        return newSingleMotor(new FrcSpeedControllerGroup(speedControllers));
    }
}
