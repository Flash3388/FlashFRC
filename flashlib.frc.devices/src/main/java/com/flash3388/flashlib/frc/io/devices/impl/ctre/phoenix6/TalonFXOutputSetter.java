package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;
import com.flash3388.flashlib.frc.io.devices.ctre.OutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.InternalOutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;

public class TalonFXOutputSetter implements InternalOutputSetter {

    // TODO: PositionDutyCycle (and velocity) vs PositionVoltage

    private final Phoenix6TalonFX mTalon;
    private final NeutralOut mNeutralRequest;
    private final DutyCycleOut mDutyCycleRequest;
    private final PositionDutyCycle mPositionRequest;
    private final VelocityDutyCycle mVelocityRequest;
    private final VoltageOut mVoltageRequest;

    private OutputType mOutputType;
    private OutputType mLastSetOutputType;
    private double mLastSetOutput;

    public TalonFXOutputSetter(Phoenix6TalonFX talon) {
        mTalon = talon;

        mNeutralRequest = new NeutralOut();
        mDutyCycleRequest = new DutyCycleOut(0);
        mPositionRequest = new PositionDutyCycle(0);
        mVelocityRequest = new VelocityDutyCycle(0);
        mVoltageRequest = new VoltageOut(0);

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
        mDutyCycleRequest.Output = value;
        mOutputType = OutputType.DUTY_CYCLE;
        return this;
    }

    @Override
    public OutputSetter velocity(double value) {
        value = CTRESensors.degreesPerSecondToRotationsPerSecond(value, 1);
        return velocityRaw(value);
    }

    @Override
    public OutputSetter velocityRaw(double value) {
        mVelocityRequest.Velocity = value;
        mVelocityRequest.Slot = mTalon.mSelectedSlot.index();
        mOutputType = OutputType.VELOCITY;
        return this;
    }

    @Override
    public OutputSetter position(double value) {
        value = CTRESensors.degreesToRotations(value, 1);
        return positionRaw(value);
    }

    @Override
    public OutputSetter positionRaw(double value) {
        mPositionRequest.Position = value;
        mPositionRequest.Slot = mTalon.mSelectedSlot.index();
        mOutputType = OutputType.POSITION;
        return this;
    }

    @Override
    public OutputSetter voltage(double value) {
        mVoltageRequest.Output = value;
        mOutputType = OutputType.VOLTAGE;
        return this;
    }

    @Override
    public OutputSetter feedForward(double value) {
        switch (mOutputType) {
            case POSITION:
                mPositionRequest.FeedForward = value;
                break;
            case VELOCITY:
                mVelocityRequest.FeedForward = value;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return this;
    }

    @Override
    public void set() {
        switch (mOutputType) {
            case DUTY_CYCLE:
                mTalon.mMotor.setControl(mDutyCycleRequest);
                mLastSetOutput = mDutyCycleRequest.Output;
                break;
            case POSITION:
                mTalon.mMotor.setControl(mPositionRequest);
                mLastSetOutput = mPositionRequest.Position;
                break;
            case VELOCITY:
                mTalon.mMotor.setControl(mVelocityRequest);
                mLastSetOutput = mVelocityRequest.Velocity;
                break;
            case NEUTRAL:
                mTalon.mMotor.setControl(mNeutralRequest);
                mLastSetOutput = 0;
                break;
            case VOLTAGE:
                mTalon.mMotor.setControl(mVoltageRequest);
                mLastSetOutput = mVoltageRequest.Output;
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
        mVoltageRequest.Output = 0;
        mDutyCycleRequest.Output = 0;
        mPositionRequest.Position = 0;
        mPositionRequest.FeedForward = 0;
        mPositionRequest.Slot = 0;
        mVelocityRequest.Velocity = 0;
        mVelocityRequest.FeedForward = 0;
        mVelocityRequest.Slot = 0;
    }
}
