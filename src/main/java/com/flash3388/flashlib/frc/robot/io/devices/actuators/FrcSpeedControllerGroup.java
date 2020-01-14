package com.flash3388.flashlib.frc.robot.io.devices.actuators;

import com.flash3388.flashlib.robot.motion.Direction;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.Arrays;
import java.util.Collection;

public class FrcSpeedControllerGroup implements com.flash3388.flashlib.robot.io.devices.actuators.SpeedController {

    private final Collection<? extends SpeedController> mControllers;
    private boolean mIsInverted;

    public FrcSpeedControllerGroup(Collection<? extends SpeedController> speedControllers) {
        mControllers = speedControllers;
        mIsInverted = false;
    }

    public FrcSpeedControllerGroup(SpeedController... speedControllers) {
        this(Arrays.asList(speedControllers));
    }

    @Override
    public void set(double speed) {
        Direction direction = mIsInverted ? Direction.BACKWARD : Direction.FORWARD;
        speed *= direction.sign();

        for (SpeedController controller : mControllers) {
            controller.set(speed);
        }
    }

    @Override
    public double get() {
        double totalSpeed = 0.0;

        for (SpeedController controller : mControllers) {
            totalSpeed += controller.get();
        }

        return totalSpeed / mControllers.size();
    }

    @Override
    public void stop() {
        for (SpeedController controller : mControllers) {
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
