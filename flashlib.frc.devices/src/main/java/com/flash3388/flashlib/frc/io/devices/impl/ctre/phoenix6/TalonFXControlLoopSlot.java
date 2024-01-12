package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonControlLoopSlot;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.BaseTalonControlLoopSlotConfigurationEditor;

public class TalonFXControlLoopSlot implements TalonControlLoopSlot {

    private final TalonFXConfigurator mConfigurator;

    private final SlotConfigs mSlotConfigs;

    public TalonFXControlLoopSlot(TalonFXConfigurator configurator, Slot slot) {
        mConfigurator = configurator;
        mSlotConfigs = new SlotConfigs();
        mSlotConfigs.SlotNumber = slot.ordinal();
    }

    @Override
    public ConfigurationEditor configure() {
        return new ConfigurationEditorImpl(this, mSlotConfigs);
    }

    @Override
    public double getError() {
        throw new UnsupportedOperationException();
    }

    private void refresh() {
        mConfigurator.refresh(mSlotConfigs);
    }

    private void save() {
        mConfigurator.apply(mSlotConfigs);
    }

    public static class ConfigurationEditorImpl extends BaseTalonControlLoopSlotConfigurationEditor {

        private final TalonFXControlLoopSlot mControlLoopSlot;
        private final SlotConfigs mSlotConfigs;

        public ConfigurationEditorImpl(TalonFXControlLoopSlot controlLoopSlot,
                                       SlotConfigs slotConfigs) {
            mControlLoopSlot = controlLoopSlot;
            mSlotConfigs = slotConfigs;
        }

        @Override
        public ConfigurationEditor setIZone(double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ConfigurationEditor setMaxIAccumulator(double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ConfigurationEditor setAllowableError(double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ConfigurationEditor setPeakOutput(double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void save() {
            mControlLoopSlot.refresh();

            if (mKp != null) {
                mSlotConfigs.kP = mKp;
            }

            if (mKi != null) {
                mSlotConfigs.kI = mKi;
            }

            if (mKd != null) {
                mSlotConfigs.kD = mKd;
            }

            if (mKf != null) {
                // TODO: THIS IS STATIC FEED FORWARD?
                //      HOW TO ALLOW ACCESS TO OTHER FEED FORWARDS?
                mSlotConfigs.kS = mKf;
            }

            mControlLoopSlot.save();
        }
    }
}
