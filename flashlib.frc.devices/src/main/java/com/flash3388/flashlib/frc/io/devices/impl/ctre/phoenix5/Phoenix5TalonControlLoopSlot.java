package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonControlLoopSlot;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.BaseTalonControlLoopSlotConfigurationEditor;

public class Phoenix5TalonControlLoopSlot implements TalonControlLoopSlot {

    private final BaseTalon mMotor;
    private final Slot mSlot;

    private final SlotConfiguration mConfiguration;

    public Phoenix5TalonControlLoopSlot(BaseTalon motor, Slot slot) {
        mMotor = motor;
        mSlot = slot;

        mConfiguration = new SlotConfiguration();
    }

    @Override
    public ConfigurationEditor configure() {
        return new ConfigurationEditorImpl(this);
    }

    @Override
    public double getError() {
        return mMotor.getClosedLoopError(mSlot.index());
    }

    private void refresh() {
        mMotor.getSlotConfigs(mConfiguration, mSlot.index(), 0);
    }

    private void apply() {
        mMotor.configureSlot(mConfiguration, mSlot.index(), 0);
    }

    private static class ConfigurationEditorImpl extends BaseTalonControlLoopSlotConfigurationEditor {

        private final Phoenix5TalonControlLoopSlot mControlLoopSlot;

        private ConfigurationEditorImpl(Phoenix5TalonControlLoopSlot controlLoopSlot) {
            mControlLoopSlot = controlLoopSlot;
        }

        @Override
        public void save() {
            mControlLoopSlot.refresh();

            if (mKp != null) {
                mControlLoopSlot.mConfiguration.kP = mKp;
            }

            if (mKi != null) {
                mControlLoopSlot.mConfiguration.kI = mKi;
            }

            if (mKd != null) {
                mControlLoopSlot.mConfiguration.kD = mKd;
            }

            if (mKf != null) {
                mControlLoopSlot.mConfiguration.kF = mKf;
            }

            if (mIZone != null) {
                mControlLoopSlot.mConfiguration.integralZone = mIZone;
            }

            if (mMaxIAccumulator != null) {
                mControlLoopSlot.mConfiguration.maxIntegralAccumulator = mMaxIAccumulator;
            }

            if (mAllowableError != null) {
                mControlLoopSlot.mConfiguration.allowableClosedloopError = mAllowableError;
            }

            if (mPeakOutput != null) {
                mControlLoopSlot.mConfiguration.closedLoopPeakOutput = mPeakOutput;
            }

            mControlLoopSlot.apply();
        }
    }
}
