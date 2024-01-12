package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.beans.util.function.Suppliers;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.CTRETalonBase;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.TalonControlLoopSlot;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.InternalOutputSetter;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

import java.util.Arrays;
import java.util.Collection;

public class Phoenix6TalonFX extends CTRETalonBase {

    protected final TalonFX mMotor;
    private final InternalOutputSetter mOutputSetter;
    private final CTRELimitSwitch mForwardLimitSwitch;
    private final CTRELimitSwitch mReverseLimitSwitch;
    private final MotorOutputConfigs mMotorOutputConfigs;

    protected TalonFXConfiguration mConfiguration;
    protected TalonControlLoopSlot.Slot mSelectedSlot;

    private Phoenix6TalonFX(TalonFX motor) {
        mMotor = motor;

        mForwardLimitSwitch = new TalonFXForwardHardwareLimitSwitch(motor);
        mReverseLimitSwitch = new TalonFXReverseHardwareLimitSwitch(motor);

        mConfiguration = new TalonFXConfiguration();
        mMotorOutputConfigs = new MotorOutputConfigs();
        mMotor.getConfigurator().refresh(mConfiguration);

        mSelectedSlot = TalonControlLoopSlot.Slot.SLOT0;
        mOutputSetter = new TalonFXOutputSetter(this);
    }

    @DeviceConstructor
    public Phoenix6TalonFX(
            @NamedArg("channel") int channel
    ) {
        this(new TalonFX(channel));
    }

    @Override
    public TalonConfigurationEditor configure() {
        return new TalonFXConfigurationEditor(this);
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
    public CTREEncoder selectFeedbackSensorMagEncoder(TalonControlLoopSlot.Slot slot, double gearRatio, double wheelRadius) {
        FeedbackConfigs configs = new FeedbackConfigs();
        mMotor.getConfigurator().refresh(configs);

        configs.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        mMotor.getConfigurator().apply(configs);

        mSelectedSlot = slot;

        return new Phoenix6RelativeEncoder(
                mMotor.getRotorPosition(),
                mMotor.getRotorVelocity(),
                mMotor::setPosition,
                Suppliers.of(gearRatio),
                Suppliers.of(wheelRadius));
    }

    @Override
    public void setInverted(boolean inverted) {
        mMotor.getConfigurator().refresh(mMotorOutputConfigs);
        mMotorOutputConfigs.Inverted = inverted ?
                InvertedValue.Clockwise_Positive :
                InvertedValue.CounterClockwise_Positive;

        mMotor.getConfigurator().apply(mMotorOutputConfigs);
    }

    @Override
    public boolean isInverted() {
        // TODO: DO WE NEED TO ALWAYS REFRESH? BECAUSE CHANGES TO CONFIG ARE ONLY POSSIBLE THROUGH US
        mMotor.getConfigurator().refresh(mMotorOutputConfigs);
        return mMotorOutputConfigs.Inverted != InvertedValue.Clockwise_Positive;
    }

    @Override
    public InternalOutputSetter output() {
        return mOutputSetter;
    }

    @Override
    protected Collection<TalonControlLoopSlot.Slot> supportedSlots() {
        return Arrays.asList(
                TalonControlLoopSlot.Slot.SLOT0,
                TalonControlLoopSlot.Slot.SLOT1,
                TalonControlLoopSlot.Slot.SLOT2
        );
    }

    @Override
    protected TalonControlLoopSlot createSlot(TalonControlLoopSlot.Slot slot) {
        return new TalonFXControlLoopSlot(mMotor.getConfigurator(), slot);
    }
}
