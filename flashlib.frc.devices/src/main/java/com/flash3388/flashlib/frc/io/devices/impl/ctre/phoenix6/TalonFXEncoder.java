package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;

import java.io.IOException;

public class TalonFXEncoder implements CTREEncoder {

    private final TalonFX mMotor;
    private final double mGearRatio;
    private final double mWheelRadius;

    private final StatusSignal<Double> mPosition;
    private final StatusSignal<Double> mVelocity;

    public TalonFXEncoder(TalonFX motor, double gearRatio, double wheelRadius) {
        mMotor = motor;
        mGearRatio = gearRatio;
        mWheelRadius = wheelRadius;

        mPosition = motor.getRotorPosition();
        mVelocity = motor.getRotorVelocity();
    }

    @Override
    public void setPositionDegrees(double degrees) {
        double raw = CTRESensors.degreesToRotations(degrees, mGearRatio);
        mMotor.setPosition(raw);
    }

    @Override
    public double getPosition() {
        mPosition.refresh();

        double raw = mPosition.getValueAsDouble();
        return CTRESensors.rotationsToDegrees(raw, mGearRatio);
    }

    @Override
    public double getRate() {
        mVelocity.refresh();

        double raw = mVelocity.getValueAsDouble();
        return CTRESensors.rotationsPerSecondToDegreesPerSecond(raw, mGearRatio);
    }

    @Override
    public double getDistancePassed() {
        mPosition.refresh();

        double raw = mPosition.getValueAsDouble();
        return CTRESensors.rotationsToMeters(raw, mGearRatio, mWheelRadius);
    }

    @Override
    public double getVelocity() {
        mVelocity.refresh();

        double raw = mVelocity.getValueAsDouble();
        return CTRESensors.rotationsPerSecondToMetersPerSecond(raw, mGearRatio, mWheelRadius);
    }

    @Override
    public void close() throws IOException {

    }
}
