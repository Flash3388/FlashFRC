package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.impl.ctre.CTRETalonBase;
import com.flash3388.flashlib.frc.io.devices.ctre.ConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.ControlLoopSlot;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class Phoenix6TalonFX extends CTRETalonBase {

    // TODO: UPDATE StatusSignals

    protected final TalonFX mMotor;

    protected TalonFXConfiguration mConfiguration;
    private MotorOutputConfigs mMotorOutputConfigs;

    private Phoenix6TalonFX(TalonFX motor) {
        super(
                new TalonFXOutputSetter(motor),
                (slot)-> new TalonFXControlLoopSlot(motor.getConfigurator(), slot),
                new TalonFXForwardHardwareLimitSwitch(motor),
                new TalonFXReverseHardwareLimitSwitch(motor));

        mMotor = motor;

        mConfiguration = new TalonFXConfiguration();
        mMotorOutputConfigs = new MotorOutputConfigs();
        mMotor.getConfigurator().refresh(mConfiguration);
    }

    @DeviceConstructor
    public Phoenix6TalonFX(
            @NamedArg("channel") int channel
    ) {
        this(new TalonFX(channel));
    }

    @Override
    public ConfigurationEditor configure() {
        return new TalonFXConfigurationEditor(this);
    }

    @Override
    public CTREEncoder selectFeedbackSensorMagEncoder(int slotIdx, double gearRatio, double wheelRadius) {
        FeedbackConfigs configs = new FeedbackConfigs();
        mMotor.getConfigurator().refresh(configs);

        configs.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        mMotor.getConfigurator().apply(configs);

        return new TalonFXEncoder(mMotor, gearRatio, wheelRadius);
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
    protected ControlLoopSlot.Slot[] supportedSlots() {
        return new ControlLoopSlot.Slot[] {
                ControlLoopSlot.Slot.SLOT0,
                ControlLoopSlot.Slot.SLOT1,
                ControlLoopSlot.Slot.SLOT2
        };
    }
}
