package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTREPidController;

public class CTREPidControllerImpl implements CTREPidController {

    private final BaseTalon mTalon;
    private final int mSlotIdx;

    public CTREPidControllerImpl(BaseTalon talon, int slotIdx) {
        mTalon = talon;
        mSlotIdx = slotIdx;
    }

    @Override
    public double getError() {
        return mTalon.getClosedLoopError(mSlotIdx);
    }

    @Override
    public void setP(double kp) {
        mTalon.config_kP(mSlotIdx, kp);
    }

    @Override
    public void setI(double ki) {
        mTalon.config_kI(mSlotIdx, ki);
    }

    @Override
    public void setD(double kd) {
        mTalon.config_kD(mSlotIdx, kd);
    }

    @Override
    public void setF(double kf) {
        mTalon.config_kF(mSlotIdx, kf);
    }

    @Override
    public void setIZone(double iZone) {
        mTalon.config_IntegralZone(mSlotIdx, iZone);
    }

    @Override
    public void setMaxIAccumulator(double max) {
        mTalon.configMaxIntegralAccumulator(mSlotIdx, max);
    }

    @Override
    public void setAllowableError(double error) {
        mTalon.configAllowableClosedloopError(mSlotIdx, error);
    }

    @Override
    public void setPeakOutput(double output) {
        mTalon.configClosedLoopPeakOutput(mSlotIdx, output);
    }
}
