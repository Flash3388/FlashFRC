package com.flash3388.flashlib.frc.io.devices.impl;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.flash3388.flashlib.frc.io.devices.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.CTRESensors;

import java.io.IOException;

public class CTREEncoderImpl implements CTREEncoder {

    private final BaseTalon mBaseTalon;
    private final int mPulsesPerRevolution;
    private final int mSlotIdx;
    private final double mGearRatio;
    private final double mWheelRadius;

    public CTREEncoderImpl(BaseTalon baseTalon, int pulsesPerRevolution, int slotIdx, double gearRatio, double wheelRadius) {
        mBaseTalon = baseTalon;
        mPulsesPerRevolution = pulsesPerRevolution;
        mSlotIdx = slotIdx;
        mGearRatio = gearRatio;
        mWheelRadius = wheelRadius;
    }

    @Override
    public void setPositionDegrees(double degrees) {
        double raw = CTRESensors.degreesToRawSensorUnits(degrees, mPulsesPerRevolution, mGearRatio);
        mBaseTalon.setSelectedSensorPosition(raw, mSlotIdx, 0);
    }

    @Override
    public double getPosition() {
        double raw = mBaseTalon.getSelectedSensorPosition(mSlotIdx);
        return CTRESensors.rawSensorUnitsToDegrees(raw, mPulsesPerRevolution, mGearRatio);
    }

    @Override
    public double getRate() {
        double raw = mBaseTalon.getSelectedSensorVelocity(mSlotIdx);
        return CTRESensors.rawSensorUnitsToDegreesPerSecond(raw, mPulsesPerRevolution, mGearRatio);
    }

    @Override
    public double getVelocity() {
        double raw = mBaseTalon.getSelectedSensorVelocity(mSlotIdx);
        return CTRESensors.rawSensorUnitsToMetersPerSecond(raw, mPulsesPerRevolution, mGearRatio, mWheelRadius);
    }

    @Override
    public double getDistancePassed() {
        double raw = mBaseTalon.getSelectedSensorPosition(mSlotIdx);
        return CTRESensors.rawSensorUnitsToMeters(raw, mPulsesPerRevolution, mGearRatio, mWheelRadius);
    }

    @Override
    public void close() throws IOException {

    }
}
