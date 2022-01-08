package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.io.devices.SpeedController;

public class FrcSpeedController implements SpeedController {

    private final edu.wpi.first.wpilibj.motorcontrol.MotorController mSpeedController;

    public FrcSpeedController(edu.wpi.first.wpilibj.motorcontrol.MotorController speedController) {
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
