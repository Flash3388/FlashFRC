package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.InternalOutputSetter;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonOutputSetter;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.OutputType;
import edu.wpi.first.wpilibj.RobotController;

public class Phoenix5TalonOutputSetter implements InternalOutputSetter {

    private final BaseTalon mMotor;
    private final int mPPR;

    private OutputType mOutputType;
    private double mValue;
    private double mFeedForward;

    private OutputType mLastSetOutputType;
    private double mLastSetOutput;

    public Phoenix5TalonOutputSetter(BaseTalon motor, int ppr) {
        mMotor = motor;
        mPPR = ppr;

        reset();
        mLastSetOutputType = OutputType.NEUTRAL;
        mLastSetOutput = 0;
    }

    @Override
    public TalonOutputSetter neutral() {
        mOutputType = OutputType.NEUTRAL;
        mValue = 0;
        return this;
    }

    @Override
    public TalonOutputSetter precentVBus(double value) {
        mOutputType = OutputType.DUTY_CYCLE;
        mValue = value;
        return this;
    }

    @Override
    public TalonOutputSetter velocity(double value) {
        mOutputType = OutputType.VELOCITY;
        mValue = CTRESensors.degreesPerSecondToRawSensorUnits(value, mPPR, 1);
        return this;
    }

    @Override
    public TalonOutputSetter velocityRaw(double value) {
        mOutputType = OutputType.VELOCITY;
        mValue = value;
        return this;
    }

    @Override
    public TalonOutputSetter position(double value) {
        mOutputType = OutputType.POSITION;
        mValue = CTRESensors.degreesToRawSensorUnits(value, mPPR, 1);
        return this;
    }

    @Override
    public TalonOutputSetter positionRaw(double value) {
        mOutputType = OutputType.POSITION;
        mValue = value;
        return this;
    }

    @Override
    public TalonOutputSetter voltage(double value) {
        mOutputType = OutputType.VOLTAGE;
        mValue = value;
        return this;
    }

    @Override
    public TalonOutputSetter feedForward(double value) {
        mFeedForward = value;
        return this;
    }

    @Override
    public void set() {
        switch (mOutputType) {
            case NEUTRAL:
                mMotor.set(ControlMode.Disabled, 0);
                break;
            case VOLTAGE:
                double voltage = mValue / RobotController.getBatteryVoltage();
                mMotor.set(ControlMode.PercentOutput, voltage);
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
