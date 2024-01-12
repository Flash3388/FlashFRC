package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;

public class BaseTalonControlLoopSlot implements ControlLoopSlot {

    private final BaseTalon mMotor;
    private final Slot mSlot;

    private final SlotConfiguration mConfiguration;

    public BaseTalonControlLoopSlot(BaseTalon motor, Slot slot) {
        mMotor = motor;
        mSlot = slot;

        mConfiguration = new SlotConfiguration();
    }

    @Override
    public ConfigurationEditor configure() {
        refresh();
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

    private static class ConfigurationEditorImpl implements ConfigurationEditor {

        private final BaseTalonControlLoopSlot mControlLoopSlot;

        private ConfigurationEditorImpl(BaseTalonControlLoopSlot controlLoopSlot) {
            mControlLoopSlot = controlLoopSlot;
        }

        @Override
        public ConfigurationEditor setP(double value) {
            mControlLoopSlot.mConfiguration.kP = value;
            return this;
        }

        @Override
        public ConfigurationEditor setI(double value) {
            mControlLoopSlot.mConfiguration.kI = value;
            return this;
        }

        @Override
        public ConfigurationEditor setD(double value) {
            mControlLoopSlot.mConfiguration.kD = value;
            return this;
        }

        @Override
        public ConfigurationEditor setF(double value) {
            mControlLoopSlot.mConfiguration.kF = value;
            return this;
        }

        @Override
        public ConfigurationEditor setIZone(double value) {
            mControlLoopSlot.mConfiguration.integralZone = value;
            return this;
        }

        @Override
        public ConfigurationEditor setMaxIAccumulator(double value) {
            mControlLoopSlot.mConfiguration.maxIntegralAccumulator = value;
            return this;
        }

        @Override
        public ConfigurationEditor setAllowableError(double value) {
            mControlLoopSlot.mConfiguration.allowableClosedloopError = value;
            return this;
        }

        @Override
        public ConfigurationEditor setPeakOutput(double value) {
            mControlLoopSlot.mConfiguration.closedLoopPeakOutput = value;
            return this;
        }

        @Override
        public void save() {
            mControlLoopSlot.apply();
        }
    }
}
