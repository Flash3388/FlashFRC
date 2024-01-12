package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.HardwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitSourceValue;
import com.ctre.phoenix6.signals.ForwardLimitTypeValue;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class TalonFXForwardHardwareLimitSwitch implements CTRELimitSwitch {

    private final TalonFX mMotor;
    private final StatusSignal<ForwardLimitValue> mValue;

    public TalonFXForwardHardwareLimitSwitch(TalonFX motor) {
        mMotor = motor;
        mValue = motor.getForwardLimit();
    }

    @Override
    public boolean isClosed() {
        return mValue.getValue() == ForwardLimitValue.ClosedToGround;
    }

    @Override
    public void configureIntegrated(boolean normallyOpen) {
        ForwardLimitTypeValue typeValue = normallyOpen ?
                ForwardLimitTypeValue.NormallyOpen :
                ForwardLimitTypeValue.NormallyClosed;

        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ForwardLimitSource = ForwardLimitSourceValue.LimitSwitchPin;
        limitSwitchConfigs.ForwardLimitType = typeValue;
        limitSwitchConfigs.ForwardLimitEnable = true;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }

    @Override
    public void configure(Source source, boolean normallyOpen, int deviceId) {
        ForwardLimitTypeValue typeValue = normallyOpen ?
                ForwardLimitTypeValue.NormallyOpen :
                ForwardLimitTypeValue.NormallyClosed;

        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ForwardLimitSource = Helper.getForwardLimitSwitchSource(source);
        limitSwitchConfigs.ForwardLimitType = typeValue;
        limitSwitchConfigs.ForwardLimitRemoteSensorID = deviceId;
        limitSwitchConfigs.ForwardLimitEnable = true;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }

    @Override
    public void disable() {
        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ForwardLimitEnable = false;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }
}
