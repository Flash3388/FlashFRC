package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.HardwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ReverseLimitSourceValue;
import com.ctre.phoenix6.signals.ReverseLimitTypeValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRELimitSwitch;

public class TalonFXReverseHardwareLimitSwitch implements CTRELimitSwitch {

    private final TalonFX mMotor;
    private final StatusSignal<ReverseLimitValue> mValue;

    public TalonFXReverseHardwareLimitSwitch(TalonFX motor) {
        mMotor = motor;
        mValue = motor.getReverseLimit();
    }

    @Override
    public boolean isClosed() {
        mValue.refresh();

        return mValue.getValue() == ReverseLimitValue.ClosedToGround;
    }

    @Override
    public void configureIntegrated(boolean normallyOpen) {
        ReverseLimitTypeValue typeValue = normallyOpen ?
                ReverseLimitTypeValue.NormallyOpen :
                ReverseLimitTypeValue.NormallyClosed;

        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ReverseLimitSource = ReverseLimitSourceValue.LimitSwitchPin;
        limitSwitchConfigs.ReverseLimitType = typeValue;
        limitSwitchConfigs.ReverseLimitEnable = true;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }

    @Override
    public void configure(Source source, boolean normallyOpen, int deviceId) {
        ReverseLimitTypeValue typeValue = normallyOpen ?
                ReverseLimitTypeValue.NormallyOpen :
                ReverseLimitTypeValue.NormallyClosed;

        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ReverseLimitSource = Helper.getReverseLimitSwitchSource(source);
        limitSwitchConfigs.ReverseLimitType = typeValue;
        limitSwitchConfigs.ReverseLimitRemoteSensorID = deviceId;
        limitSwitchConfigs.ReverseLimitEnable = true;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }

    @Override
    public void disable() {
        HardwareLimitSwitchConfigs limitSwitchConfigs = Helper.getConfigs(mMotor);

        limitSwitchConfigs.ReverseLimitEnable = false;

        mMotor.getConfigurator().apply(limitSwitchConfigs);
    }
}
