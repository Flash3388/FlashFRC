package com.flash3388.flashlib.frc.io.devices;

import com.flash3388.flashlib.frc.io.devices.impl.AnalogAccelerometer;
import com.flash3388.flashlib.frc.io.devices.impl.AnalogGyro;
import com.flash3388.flashlib.frc.io.devices.impl.CANTalonFX;
import com.flash3388.flashlib.frc.io.devices.impl.CANTalonSRX;
import com.flash3388.flashlib.frc.io.devices.impl.CANVictorSPX;
import com.flash3388.flashlib.frc.io.devices.impl.FrcDoubleSolenoid;
import com.flash3388.flashlib.frc.io.devices.impl.FrcSolenoid;
import com.flash3388.flashlib.frc.io.devices.impl.PWMTalonFX;
import com.flash3388.flashlib.frc.io.devices.impl.PWMTalonSRX;
import com.flash3388.flashlib.frc.io.devices.impl.PWMVictorSPX;
import com.flash3388.flashlib.frc.io.devices.impl.Servo;
import com.flash3388.flashlib.frc.io.devices.impl.Talon;
import com.flash3388.flashlib.frc.io.devices.impl.Ultrasonic;
import com.flash3388.flashlib.frc.io.devices.impl.VictorSP;
import com.flash3388.flashlib.io.devices.AbstractDeviceProvider;
import edu.wpi.first.wpilibj.AnalogEncoder;

public class FrcDeviceProvider extends AbstractDeviceProvider {

    public FrcDeviceProvider() {
        // pneumatics
        registerDevice(FrcDevicesIds.Solenoid, FrcSolenoid.class);
        registerDevice(FrcDevicesIds.DoubleSolenoid, FrcDoubleSolenoid.class);

        // speed controllers
        registerDevice(FrcDevicesIds.Talon, Talon.class);
        registerDevice(FrcDevicesIds.VictorSP, VictorSP.class);
        registerDevice(FrcDevicesIds.PWMTalonSRX, PWMTalonSRX.class);
        registerDevice(FrcDevicesIds.PWMTalonFX, PWMTalonFX.class);
        registerDevice(FrcDevicesIds.PWMVictorSPX, PWMVictorSPX.class);
        registerDevice(FrcDevicesIds.CANTalonSRX, CANTalonSRX.class);
        registerDevice(FrcDevicesIds.CANTalonFX, CANTalonFX.class);
        registerDevice(FrcDevicesIds.CANVictorSPX, CANVictorSPX.class);

        // position controllers
        registerDevice(FrcDevicesIds.Servo, Servo.class);

        // sensors
        registerDevice(FrcDevicesIds.Ultrasonic, Ultrasonic.class);
        registerDevice(FrcDevicesIds.AnalogAccelerometer, AnalogAccelerometer.class);
        registerDevice(FrcDevicesIds.AnalogGyro, AnalogGyro.class);
        registerDevice(FrcDevicesIds.AnalogEncoder, AnalogEncoder.class);
    }
}
