package edu.flash3388.flashlib.frc.robot.io.devices.actuators;

import edu.flash3388.flashlib.robot.io.devices.actuators.SpeedController;

public class FrcSpeedControllerAdapter implements SpeedController {

    private final edu.wpi.first.wpilibj.SpeedController mSpeedController;

    public FrcSpeedControllerAdapter(edu.wpi.first.wpilibj.SpeedController speedController) {
        mSpeedController = speedController;
    }

    @Override
    public void set(double speed) {
        mSpeedController.set(speed);
    }

    @Override
    public void stop() {
        mSpeedController.stopMotor();
    }

    @Override
    public double get() {
        return mSpeedController.get();
    }

    @Override
    public boolean isInverted() {
        return mSpeedController.getInverted();
    }

    @Override
    public void setInverted(boolean isInverted) {
        mSpeedController.setInverted(isInverted);
    }
}
