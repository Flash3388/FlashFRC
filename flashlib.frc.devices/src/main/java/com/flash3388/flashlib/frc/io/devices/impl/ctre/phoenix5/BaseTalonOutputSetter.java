package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.InternalOutputSetter;
import com.flash3388.flashlib.frc.io.devices.ctre.OutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;

public class BaseTalonOutputSetter implements InternalOutputSetter {

    private final BaseTalon mMotor;

    private OutputType mOutputType;
    private double mValue;
    private double mFeedForward;

    private OutputType mLastSetOutputType;
    private double mLastSetOutput;

    public BaseTalonOutputSetter(BaseTalon motor) {
        mMotor = motor;

        reset();
        mLastSetOutputType = OutputType.NEUTRAL;
        mLastSetOutput = 0;
    }

    @Override
    public OutputSetter neutral() {
        mOutputType = OutputType.NEUTRAL;
        mValue = 0;
        return this;
    }

    @Override
    public OutputSetter precentVBus(double value) {
        mOutputType = OutputType.DUTY_CYCLE;
        mValue = value;
        return this;
    }

    @Override
    public OutputSetter velocity(double value) {
        mOutputType = OutputType.VELOCITY;
        mValue = value;
        return this;
    }

    @Override
    public OutputSetter position(double value) {
        mOutputType = OutputType.POSITION;
        mValue = value;
        return this;
    }

    @Override
    public OutputSetter feedForward(double value) {
        mFeedForward = value;
        return this;
    }

    @Override
    public void set() {
        switch (mOutputType) {
            case NEUTRAL:
                mMotor.set(ControlMode.Disabled, 0);
                break;
            default:
                ControlMode controlMode = controlModeForOutputType(mOutputType);
                DemandType demandType = DemandType.Neutral;
                double demandValue = 0;

                if (mFeedForward != 0) {
                    demandType = DemandType.ArbitraryFeedForward;
                    demandValue = mFeedForward;
                }

                mMotor.set(controlMode, mValue, demandType, demandValue);
                break;
        }

        mLastSetOutputType = mOutputType;
        mLastSetOutput = mValue;

        reset();
    }

    @Override
    public double getLastSetOutput() {
        return mLastSetOutput;
    }

    @Override
    public OutputType getLastSetOutputType() {
        return mLastSetOutputType;
    }

    @Override
    public void reset() {
        mOutputType = OutputType.NEUTRAL;
        mValue = 0;
        mFeedForward = 0;
    }

    private ControlMode controlModeForOutputType(OutputType type) {
        switch (type) {
            case DUTY_CYCLE:
                return ControlMode.PercentOutput;
            case POSITION:
                return ControlMode.Position;
            case VELOCITY:
                return ControlMode.Velocity;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
