package com.flash3388.flashlib.frc.io.devices.impl.ctre.phoenix6;

import com.beans.DoubleProperty;
import com.beans.properties.SimpleDoubleProperty;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.flash3388.flashlib.frc.io.devices.ctre.CANCoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CANCoderConfigurationEditor;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRESensors;
import com.flash3388.flashlib.io.devices.DeviceConstructor;
import com.flash3388.flashlib.io.devices.NamedArg;

public class Phoenix6CANCoder extends Phoenix6RelativeEncoder implements CANCoder {

    private final CANcoder mEncoder;
    private final StatusSignal<Double> mAbsolutePosition;

    private final DoubleProperty mGearRatio;
    private final DoubleProperty mWheelRadius;
    private final MagnetSensorConfigs mMagnetSensorConfigs;

    private Phoenix6CANCoder(CANcoder encoder, DoubleProperty gearRatio, DoubleProperty wheelRadius) {
        super(
                encoder.getPositionSinceBoot(),
                encoder.getVelocity(),
                encoder::setPosition,
                gearRatio,
                wheelRadius
        );

        mEncoder = encoder;
        mAbsolutePosition = encoder.getAbsolutePosition();
        mGearRatio = gearRatio;
        mWheelRadius = wheelRadius;

        mMagnetSensorConfigs = new MagnetSensorConfigs();
        mMagnetSensorConfigs.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        encoder.getConfigurator().apply(mMagnetSensorConfigs);
    }

    private Phoenix6CANCoder(CANcoder encoder) {
        this(encoder, new SimpleDoubleProperty(), new SimpleDoubleProperty());
    }

    @DeviceConstructor
    public Phoenix6CANCoder(
            @NamedArg("channel") int channel
    ) {
        this(new CANcoder(channel));
    }

    @Override
    public CANCoderConfigurationEditor configure() {
        return new ConfigurationEditor(this);
    }

    @Override
    public double getAbsolutePosition() {
        mAbsolutePosition.refresh();

        double value = mAbsolutePosition.getValue();
        return CTRESensors.rotationsToDegrees(value, 1);
    }

    private static class ConfigurationEditor implements CANCoderConfigurationEditor {

        private final Phoenix6CANCoder mDevice;

        private double mAbsoluteOffset;
        private double mGearRatio;
        private double mWheelRadius;

        private ConfigurationEditor(Phoenix6CANCoder device) {
            mDevice = device;

            mGearRatio = device.mGearRatio.getAsDouble();
            mWheelRadius = device.mWheelRadius.getAsDouble();
        }

        @Override
        public CANCoderConfigurationEditor setAbsoluteSensorOffset(double value) {
            mAbsoluteOffset = CTRESensors.degreesToRotations(value, 1);
            return this;
        }

        @Override
        public CANCoderConfigurationEditor setGearRatio(double value) {
            mGearRatio = value;
            return this;
        }

        @Override
        public CANCoderConfigurationEditor setWheelRadius(double value) {
            mWheelRadius = value;
            return this;
        }

        @Override
        public void save() {
            mDevice.mGearRatio.setAsDouble(mGearRatio);
            mDevice.mWheelRadius.setAsDouble(mWheelRadius);

            mDevice.mEncoder.getConfigurator().refresh(mDevice.mMagnetSensorConfigs);
            mDevice.mMagnetSensorConfigs.MagnetOffset = mAbsoluteOffset;
            mDevice.mEncoder.getConfigurator().apply(mDevice.mMagnetSensorConfigs);
        }
    }
}
