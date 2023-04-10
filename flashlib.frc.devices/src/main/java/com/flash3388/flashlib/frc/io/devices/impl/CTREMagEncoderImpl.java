package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTREMagEncoder;

import java.io.IOException;

public class CTREMagEncoderImpl implements CTREMagEncoder {

    private final BaseTalon mBaseTalon;
    private final int mPulsesPerRevolution;
    private final double mRevolutionToDistance;

    public CTREMagEncoderImpl(BaseTalon baseTalon,
                              int pulsesPerRevolution,
                              double devolutionToDistance) {
        mBaseTalon = baseTalon;
        mPulsesPerRevolution = pulsesPerRevolution;
        mRevolutionToDistance = devolutionToDistance;
    }

    @Override
    public void reset() {
        mBaseTalon.setSelectedSensorPosition(0);
    }

    @Override
    public double getPosition() {
        return mBaseTalon.getSelectedSensorPosition() / mPulsesPerRevolution * 360;
    }

    @Override
    public double getRate() {
        return mBaseTalon.getSelectedSensorVelocity() / 10 / mPulsesPerRevolution * 360;
    }

    @Override
    public double getVelocity() {
        return mBaseTalon.getSelectedSensorVelocity() * 600 / mPulsesPerRevolution * mRevolutionToDistance;
    }

    @Override
    public double getDistancePassed() {
        return mBaseTalon.getSelectedSensorPosition() / mPulsesPerRevolution * mRevolutionToDistance;
    }

    @Override
    public void close() throws IOException {

    }
}
