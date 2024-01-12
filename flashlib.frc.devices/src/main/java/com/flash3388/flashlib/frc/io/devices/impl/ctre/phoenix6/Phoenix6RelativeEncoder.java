package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.ctre.phoenix6.StatusSignal;
import com.flash3388.flashlib.frc.io.devices.ctre.CTREEncoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public class Phoenix6RelativeEncoder implements CTREEncoder {

    private final StatusSignal<Double> mPosition;
    private final StatusSignal<Double> mVelocity;
    private final Consumer<Double> mPositionSetter;

    private final DoubleSupplier mGearRatio;
    private final DoubleSupplier mWheelRadius;

    public Phoenix6RelativeEncoder(StatusSignal<Double> position,
                                   StatusSignal<Double> velocity,
                                   Consumer<Double> positionSetter,
                                   DoubleSupplier gearRatio,
                                   DoubleSupplier wheelRadius) {
        mPosition = position;
        mVelocity = velocity;
        mPositionSetter = positionSetter;
        mGearRatio = gearRatio;
        mWheelRadius = wheelRadius;
    }

    @Override
    public void setPositionDegrees(double degrees) {
        double raw = CTRESensors.degreesToRotations(degrees, mGearRatio.getAsDouble());
        mPositionSetter.accept(raw);
    }

    @Override
    public double getPosition() {
        mPosition.refresh();

        double raw = mPosition.getValueAsDouble();
        return CTRESensors.rotationsToDegrees(raw, mGearRatio.getAsDouble());
    }

    @Override
    public double getRate() {
        mVelocity.refresh();

        double raw = mVelocity.getValueAsDouble();
        return CTRESensors.rotationsPerSecondToDegreesPerSecond(raw, mGearRatio.getAsDouble());
    }

    @Override
    public double getDistancePassed() {
        mPosition.refresh();

        double raw = mPosition.getValueAsDouble();
        return CTRESensors.rotationsToMeters(raw,
                mGearRatio.getAsDouble(),
                mWheelRadius.getAsDouble());
    }

    @Override
    public double getVelocity() {
        mVelocity.refresh();

        double raw = mVelocity.getValueAsDouble();
        return CTRESensors.rotationsPerSecondToMetersPerSecond(raw,
                mGearRatio.getAsDouble(),
                mWheelRadius.getAsDouble());
    }

    @Override
    public void close() throws IOException {

    }
}
