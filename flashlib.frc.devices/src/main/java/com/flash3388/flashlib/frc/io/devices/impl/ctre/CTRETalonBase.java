package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRETalon;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;
import com.flash3388.flashlib.frc.io.devices.ctre.OutputSetter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class CTRETalonBase implements CTRETalon {

    private final InternalOutputSetter mOutputSetter;
    private final Map<ControlLoopSlot.Slot, ControlLoopSlot> mControlLoopSlots;
    private final CTRELimitSwitch mForwardLimitSwitch;
    private final CTRELimitSwitch mReverseLimitSwitch;

    protected CTRETalonBase(InternalOutputSetter outputSetter,
                            Function<ControlLoopSlot.Slot, ControlLoopSlot> slotCreator,
                            CTRELimitSwitch forwardLimitSwitch,
                            CTRELimitSwitch reverseLimitSwitch) {
        // cached due to frequent uses
        mOutputSetter = outputSetter;
        mForwardLimitSwitch = forwardLimitSwitch;
        mReverseLimitSwitch = reverseLimitSwitch;

        mControlLoopSlots = new HashMap<>();
        for (ControlLoopSlot.Slot slot : supportedSlots()) {
            ControlLoopSlot controlLoopSlot = slotCreator.apply(slot);
            mControlLoopSlots.put(slot, controlLoopSlot);
        }
    }

    @Override
    public ControlLoopSlot slot(ControlLoopSlot.Slot slot) {
        ControlLoopSlot controlLoopSlot = mControlLoopSlots.get(slot);
        if (controlLoopSlot == null) {
            throw new IllegalArgumentException("unsupported slot: " + slot.index());
        }

        return controlLoopSlot;
    }

    @Override
    public OutputSetter output() {
        mOutputSetter.reset();
        return mOutputSetter;
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
    public double get() {
        return mOutputSetter.getLastSetOutput();
    }

    @Override
    public void set(double speed) {
        output().precentVBus(speed).set();
    }

    @Override
    public void stop() {
        output().neutral().set();
    }

    protected abstract ControlLoopSlot.Slot[] supportedSlots();
}
