package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.control.Direction;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.Arrays;
import java.util.Collection;

public class FrcSpeedControllerGroup implements com.flash3388.flashlib.io.devices.SpeedController {

    private final Collection<? extends MotorController> mControllers;
    private boolean mIsInverted;

    public FrcSpeedControllerGroup(Collection<? extends MotorController> speedControllers) {
        mControllers = speedControllers;
        mIsInverted = false;
    }

    public FrcSpeedControllerGroup(MotorController... speedControllers) {
        this(Arrays.asList(speedControllers));
    }

    @Override
    public void set(double speed) {
        Direction direction = mIsInverted ? Direction.BACKWARD : Direction.FORWARD;
        speed *= direction.sign();

        for (MotorController controller : mControllers) {
            controller.set(speed);
        }
    }

    @Override
    public double get() {
        double totalSpeed = 0.0;

        for (MotorController controller : mControllers) {
            totalSpeed += controller.get();
        }

        return totalSpeed / mControllers.size();
    }

    @Override
    public void stop() {
        for (MotorController controller : mControllers) {
            controller.stopMotor();
        }
    }

    @Override
    public void setInverted(boolean inverted) {
        mIsInverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return mIsInverted;
    }
}
