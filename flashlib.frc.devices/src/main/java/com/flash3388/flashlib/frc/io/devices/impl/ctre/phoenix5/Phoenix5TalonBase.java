package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.CTRETalonBase;
import com.flash3388.flashlib.frc.io.devices.ctre.ConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.InternalOutputSetter;

import java.util.Arrays;
import java.util.Collection;

public abstract class Phoenix5TalonBase extends CTRETalonBase {

    protected final BaseTalon mTalon;
    private final InternalOutputSetter mOutputSetter;
    private final CTRELimitSwitch mForwardLimitSwitch;
    private final CTRELimitSwitch mReverseLimitSwitch;

    public Phoenix5TalonBase(BaseTalon talon, int ppr) {
        mTalon = talon;
        mOutputSetter = new BaseTalonOutputSetter(talon, ppr);
        mForwardLimitSwitch = new BaseTalonForwardHardwareLimitSwitch(talon);
        mReverseLimitSwitch = new BaseTalonReverseHardwareLimitSwitch(talon);
    }

    @Override
    public ConfigurationEditor configure() {
        return new BaseTalonConfigurationEditor(mTalon);
    }

    @Override
    public CTRELimitSwitch getForwardLimitSwitch() {
        return mForwardLimitSwitch;
    }

    @Override
    public CTRELimitSwitch getReverseLimitSwitch() {
        return mReverseLimitSwitch;
    }

    @Override
    public void setInverted(boolean inverted) {
        configure()
                .inverted(inverted)
                .save();
    }

    @Override
    public boolean isInverted() {
        return mTalon.getInverted();
    }

    @Override
    public InternalOutputSetter output() {
        return mOutputSetter;
    }

    @Override
    protected Collection<ControlLoopSlot.Slot> supportedSlots() {
        return Arrays.asList(
                ControlLoopSlot.Slot.SLOT0,
                ControlLoopSlot.Slot.SLOT1
        );
    }

    @Override
    protected ControlLoopSlot createSlot(ControlLoopSlot.Slot slot) {
        return new BaseTalonControlLoopSlot(mTalon, slot);
    }
}
