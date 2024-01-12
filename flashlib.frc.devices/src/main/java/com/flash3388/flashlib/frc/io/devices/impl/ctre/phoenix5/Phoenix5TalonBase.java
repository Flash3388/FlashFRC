package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.CTRETalonBase;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonControlLoopSlot;
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
        mOutputSetter = new Phoenix5TalonOutputSetter(talon, ppr);
        mForwardLimitSwitch = new Phoenix5TalonForwardHardwareLimitSwitch(talon);
        mReverseLimitSwitch = new Phoenix5TalonReverseHardwareLimitSwitch(talon);
    }

    @Override
    public TalonConfigurationEditor configure() {
        return new Phoenix5TalonConfigurationEditor(mTalon);
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
                .setInverted(inverted)
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
    protected Collection<TalonControlLoopSlot.Slot> supportedSlots() {
        return Arrays.asList(
                TalonControlLoopSlot.Slot.SLOT0,
                TalonControlLoopSlot.Slot.SLOT1
        );
    }

    @Override
    protected TalonControlLoopSlot createSlot(TalonControlLoopSlot.Slot slot) {
        return new Phoenix5TalonControlLoopSlot(mTalon, slot);
    }
}
