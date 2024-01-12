package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.TalonControlLoopSlot;

public abstract class BaseTalonControlLoopSlotConfigurationEditor implements TalonControlLoopSlot.ConfigurationEditor {

    protected Double mKp;
    protected Double mKi;
    protected Double mKd;
    protected Double mKf;
    protected Double mIZone;
    protected Double mMaxIAccumulator;
    protected Double mAllowableError;
    protected Double mPeakOutput;

    public BaseTalonControlLoopSlotConfigurationEditor() {
        mKp = null;
        mKi = null;
        mKd = null;
        mKf = null;
        mIZone = null;
        mMaxIAccumulator = null;
        mAllowableError = null;
        mPeakOutput = null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setP(double value) {
        mKp = value;
        return this;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setI(double value) {
        mKi = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setD(double value) {
        mKd = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setF(double value) {
        mKf = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setIZone(double value) {
        mIZone = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setMaxIAccumulator(double value) {
        mMaxIAccumulator = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setAllowableError(double value) {
        mAllowableError = value;
        return null;
    }

    @Override
    public TalonControlLoopSlot.ConfigurationEditor setPeakOutput(double value) {
        mPeakOutput = value;
        return null;
    }
}
