package com.flash3388.flashlib.frc.devices;

import com.flash3388.flashlib.io.devices.AbstractDeviceProvider;
import edu.wpi.first.wpilibj.AnalogEncoder;

public class FrcDeviceProvider extends AbstractDeviceProvider {

    public FrcDeviceProvider() {
        // pneumatics
        registerDevice("flashlib.frc.Solenoid", FrcSolenoid.class);
        registerDevice("flashlib.frc.DoubleSolenoid", FrcDoubleSolenoid.class);

        // speed controllers
        registerDevice("flashlib.frc.Talon", Talon.class);
        registerDevice("flashlib.frc.VictorSP", VictorSP.class);
        registerDevice("flashlib.frc.PWMTalonSRX", PWMTalonSRX.class);
        registerDevice("flashlib.frc.PWMTalonFX", PWMTalonFX.class);
        registerDevice("flashlib.frc.PWMVictorSPX", PWMVictorSPX.class);
        registerDevice("flashlib.frc.CANTalonSRX", CANTalonSRX.class);
        registerDevice("flashlib.frc.CANTalonFX", CANTalonFX.class);
        registerDevice("flashlib.frc.CANVictorSPX", CANVictorSPX.class);

        // position controllers
        registerDevice("flashlib.frc.Servo", Servo.class);

        // sensors
        registerDevice("flashlib.frc.Ultrasonic", Ultrasonic.class);
        registerDevice("flashlib.frc.AnalogAccelerometer", AnalogAccelerometer.class);
        registerDevice("flashlib.frc.AnalogGyro", AnalogGyro.class);
        registerDevice("flashlib.frc.AnalogEncoder", AnalogEncoder.class);
    }
}
