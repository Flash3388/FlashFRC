package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

abstract class CTREBaseTalon extends FrcSpeedController implements CTRETalon {

    protected final BaseTalon mTalon;
    protected final int mPidLoopIndex;
    private ControlMode mControlMode;

    protected CTREBaseTalon(MotorController speedController, BaseTalon baseTalon, int pidLoopIndex) {
        super(speedController);
        mTalon = baseTalon;
        mPidLoopIndex = pidLoopIndex;

        mTalon.configNominalOutputForward(0);
        mTalon.configNominalOutputReverse(0);
        mTalon.configPeakOutputForward(1);
        mTalon.configPeakOutputReverse(-1);
        mControlMode = ControlMode.PercentOutput;
    }

    protected CTREBaseTalon(MotorController speedController, BaseTalon baseTalon) {
        this(speedController, baseTalon, 0);
    }


    @Override
    public void setP(double kp) {
        mTalon.config_kP(mPidLoopIndex, kp);
    }

    @Override
    public void setI(double ki) {
        mTalon.config_kI(mPidLoopIndex, ki);
    }

    @Override
    public void setD(double kd) {
        mTalon.config_kD(mPidLoopIndex, kd);
    }

    @Override
    public void setF(double kf) {
        mTalon.config_kF(mPidLoopIndex, kf);
    }

    @Override
    public void setControlMode(ControlMode controlMode) {
        mControlMode = controlMode;
    }

    @Override
    public ControlMode getControlMode() {
        return mControlMode;
    }

    @Override
    public void set(double speed) {
        mTalon.set(mControlMode, speed);
    }
}
