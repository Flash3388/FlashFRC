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
