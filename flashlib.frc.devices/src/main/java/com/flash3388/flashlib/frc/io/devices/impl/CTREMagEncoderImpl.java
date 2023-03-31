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
    public int getRaw() {
        return (int) mBaseTalon.getSelectedSensorPosition();
    }

    @Override
    public double getRate() {
        return mBaseTalon.getSelectedSensorVelocity();
    }

    @Override
    public double getVelocity() {
        return mBaseTalon.getSelectedSensorVelocity() * 600 / mPulsesPerRevolution * mRevolutionToDistance;
    }

    @Override
    public double getDistance() {
        return mBaseTalon.getSelectedSensorPosition() / mPulsesPerRevolution * mRevolutionToDistance;
    }

    @Override
    public boolean getDirection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {

    }
}
