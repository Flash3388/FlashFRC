package com.flash3388.flashlib.frc.io.devices.impl.ctre;

import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRETalon;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class CTRETalonBase implements CTRETalon {

    private final Map<ControlLoopSlot.Slot, ControlLoopSlot> mControlLoopSlots;

    protected CTRETalonBase() {
        mControlLoopSlots = new HashMap<>();
    }

    @Override
    public ControlLoopSlot slot(ControlLoopSlot.Slot slot) {
        ControlLoopSlot controlLoopSlot = mControlLoopSlots.get(slot);
        if (controlLoopSlot == null) {
            if (!supportedSlots().contains(slot)) {
                throw new IllegalArgumentException("unsupported slot: " + slot.index());
            }

            controlLoopSlot = createSlot(slot);
            mControlLoopSlots.put(slot, controlLoopSlot);
        }

        return controlLoopSlot;
    }

    @Override
    public double get() {
        return output().getLastSetOutput();
    }

    @Override
    public void set(double speed) {
        output().precentVBus(speed).set();
    }

    @Override
    public void stop() {
        output().neutral().set();
    }

    @Override
    public abstract InternalOutputSetter output();

    protected abstract Collection<ControlLoopSlot.Slot> supportedSlots();
    protected abstract ControlLoopSlot createSlot(ControlLoopSlot.Slot slot);
}
