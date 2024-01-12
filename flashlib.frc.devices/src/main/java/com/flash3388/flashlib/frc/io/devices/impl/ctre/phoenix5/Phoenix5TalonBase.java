package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix5;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.CTRETalonBase;
import com.flash3388.flashlib.frc.io.devices.ctre.ConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;

public abstract class Phoenix5TalonBase extends CTRETalonBase {

    protected final BaseTalon mTalon;

    public Phoenix5TalonBase(BaseTalon talon) {
        super(
                new BaseTalonOutputSetter(talon),
                (slot)-> new BaseTalonControlLoopSlot(talon, slot),
                new BaseTalonForwardHardwareLimitSwitch(talon),
                new BaseTalonReverseHardwareLimitSwitch(talon));

        mTalon = talon;
    }

    @Override
    public ConfigurationEditor configure() {
        return new BaseTalonConfigurationEditor(mTalon);
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
    protected ControlLoopSlot.Slot[] supportedSlots() {
        return new ControlLoopSlot.Slot[] {
                ControlLoopSlot.Slot.SLOT0,
                ControlLoopSlot.Slot.SLOT1
        };
    }
}
