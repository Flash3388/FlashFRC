package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.flash3388.flashlib.frc.io.devices.ctre.InternalOutputSetter;
import com.flash3388.flashlib.frc.io.devices.ctre.OutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;

public class TalonFXOutputSetter implements InternalOutputSetter {

    // TODO: SUPPORT MORE OPTIONS

    private final TalonFX mMotor;
    private final NeutralOut mNeutralRequest;
    private final DutyCycleOut mDutyCycleRequest;
    // TODO: PositionDutyCycle (and velocity) vs VelocityVoltage
    private final PositionDutyCycle mPositionRequest;
    private final VelocityDutyCycle mVelocityRequest;

    private OutputType mOutputType;
    private OutputType mLastSetOutputType;
    private double mLastSetOutput;

    public TalonFXOutputSetter(TalonFX motor) {
        mMotor = motor;

        mNeutralRequest = new NeutralOut();
        mDutyCycleRequest = new DutyCycleOut(0);
        mPositionRequest = new PositionDutyCycle(0);
        mVelocityRequest = new VelocityDutyCycle(0);

        reset();
        mLastSetOutputType = OutputType.NEUTRAL;
        mLastSetOutput = 0;
    }

    @Override
    public OutputSetter neutral() {
        mOutputType = OutputType.NEUTRAL;
        return this;
    }

    @Override
    public OutputSetter precentVBus(double value) {
        mDutyCycleRequest.withOutput(value);
        mOutputType = OutputType.DUTY_CYCLE;
        return this;
    }

    @Override
    public OutputSetter velocity(double value) {
        mVelocityRequest.withVelocity(value);
        mOutputType = OutputType.VELOCITY;
        return this;
    }

    @Override
    public OutputSetter position(double value) {
        mPositionRequest.withPosition(value);
        mOutputType = OutputType.POSITION;
        return this;
    }

    @Override
    public OutputSetter feedForward(double value) {
        switch (mOutputType) {
            case POSITION:
                mPositionRequest.withFeedForward(value);
                break;
            case VELOCITY:
                mVelocityRequest.withFeedForward(value);
                break;
            case DUTY_CYCLE:
            case NEUTRAL:
                throw new UnsupportedOperationException();
        }

        return this;
    }

    @Override
    public void set() {
        switch (mOutputType) {
            case DUTY_CYCLE:
                mMotor.setControl(mDutyCycleRequest);
                mLastSetOutput = mDutyCycleRequest.Output;
                break;
            case POSITION:
                mMotor.setControl(mPositionRequest);
                mLastSetOutput = mPositionRequest.Position;
                break;
            case VELOCITY:
                mMotor.setControl(mVelocityRequest);
                mLastSetOutput = mVelocityRequest.Velocity;
                break;
            case NEUTRAL:
                mMotor.setControl(mNeutralRequest);
                mLastSetOutput = 0;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        mLastSetOutputType = mOutputType;

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
        mDutyCycleRequest.withOutput(0);
        mPositionRequest.withPosition(0).withFeedForward(0);
        mVelocityRequest.withVelocity(0).withFeedForward(0);
    }
}
