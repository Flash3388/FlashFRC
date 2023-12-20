package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTRELimitSwitch;
import com.flash3388.flashlib.frc.io.devices.CTREPidController;
import com.flash3388.flashlib.frc.io.devices.CTRETalon;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

abstract class CTREBaseTalon extends FrcSpeedController implements CTRETalon {

    protected final BaseTalon mTalon;
    private final CTRELimitSwitch mForwardLimitSwitch;
    private final CTRELimitSwitch mReverseLimitSwitch;

    protected CTREBaseTalon(MotorController speedController, BaseTalon baseTalon) {
        super(speedController);
        mTalon = baseTalon;

        mTalon.configFactoryDefault();
        mTalon.configNominalOutputForward(0);
        mTalon.configNominalOutputReverse(0);
        mTalon.configPeakOutputForward(1);
        mTalon.configPeakOutputReverse(-1);

        mForwardLimitSwitch = new CTREForwardLimitSwitch(baseTalon);
        mReverseLimitSwitch = new CTREReverseLimitSwitch(baseTalon);
    }

    @Override
    public void set(ControlMode controlMode, double value) {
        mTalon.set(controlMode, value);
    }

    @Override
    public void configureNeutralMode(NeutralMode mode) {
        mTalon.setNeutralMode(mode);
    }

    @Override
    public CTREPidController getPidController(int slotIdx) {
        return new CTREPidControllerImpl(mTalon, slotIdx);
    }

    @Override
    public CTRELimitSwitch getForwardLimitSwitch() {
        return mForwardLimitSwitch;
    }

    @Override
    public CTRELimitSwitch getReverseLimitSwitch() {
        return mReverseLimitSwitch;
    }
}
