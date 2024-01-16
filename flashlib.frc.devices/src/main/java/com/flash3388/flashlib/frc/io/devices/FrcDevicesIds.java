package com.flash3388.flashlib.frc.io.devices;

import com.flash3388.flashlib.frc.io.devices.ctre.CANCoder;
import com.flash3388.flashlib.frc.io.devices.ctre.CTRETalon;
import com.flash3388.flashlib.io.devices.Accelerometer;
import com.flash3388.flashlib.io.devices.DeviceId;
import com.flash3388.flashlib.io.devices.Gyro;
import com.flash3388.flashlib.io.devices.PositionController;
import com.flash3388.flashlib.io.devices.RangeFinder;
import com.flash3388.flashlib.io.devices.SpeedController;

public class FrcDevicesIds {

    private FrcDevicesIds() {}

    /**
     * Defines a basic single-action solenoid connected to the <em>PCM</em>/<em>PH</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>moduleNumber</em> (<em>int</em>): channel identifier for the <em>PCM</em>/<em>PH</em> module.</li>
     *     <li><em>moduleType</em> ({@link edu.wpi.first.wpilibj.PneumaticsModuleType}): type of the module used.</li>
     *     <li><em>channel</em> (<em>int</em>): identifier of the port the device is connected to.</li>
     * </ul>
     *
     * <p>
     * Constructor 2:
     * Creates a device connected to the main module (usually <em>id=0</em>)
     * <ul>
     *     <li><em>moduleType</em> ({@link edu.wpi.first.wpilibj.PneumaticsModuleType}): type of the module used.</li>
     *     <li><em>channel</em> (<em>int</em>): identifier of the port the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<com.flash3388.flashlib.io.devices.Solenoid> Solenoid =
            DeviceId.of(1011, com.flash3388.flashlib.io.devices.Solenoid.class);

    /**
     * Defines a basic double-action solenoid connected to the <em>PCM</em>/<em>PH</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>moduleNumber</em> (<em>int</em>): channel identifier for the <em>PCM</em>/<em>PH</em> module.</li>
     *     <li><em>moduleType</em> ({@link edu.wpi.first.wpilibj.PneumaticsModuleType}): type of the module used.</li>
     *     <li><em>forwardChannel</em> (<em>int</em>): identifier of the port used to activate the forward action, the device is connected to.</li>
     *     <li><em>reverseChannel</em> (<em>int</em>): identifier of the port used to activate the backward action, the device is connected to.</li>
     * </ul>
     *
     * <p>
     * Constructor 2:
     * Creates a device connected to the main module (usually <em>id=0</em>)
     * <ul>
     *     <li><em>moduleType</em> ({@link edu.wpi.first.wpilibj.PneumaticsModuleType}): type of the module used.</li>
     *     <li><em>forwardChannel</em> (<em>int</em>): identifier of the port used to activate the forward action, the device is connected to.</li>
     *     <li><em>reverseChannel</em> (<em>int</em>): identifier of the port used to activate the backward action, the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<com.flash3388.flashlib.io.devices.DoubleSolenoid> DoubleSolenoid =
            DeviceId.of(1012, com.flash3388.flashlib.io.devices.DoubleSolenoid.class);

    /**
     * Defines a Talon PWM speed (motor) controller.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> Talon = DeviceId.of(1031, SpeedController.class);

    /**
     * Defines a VictorSP PWM speed (motor) controller.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> VictorSP = DeviceId.of(1032, SpeedController.class);

    /**
     * Defines a TalonSRX PWM speed (motor) controller.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> PWMTalonSRX = DeviceId.of(1033, SpeedController.class);

    /**
     * Defines a TalonFX PWM speed (motor) controller.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> PWMTalonFX = DeviceId.of(1034, SpeedController.class);

    /**
     * Defines a VictorSPX PWM speed (motor) controller.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> PWMVictorSPX = DeviceId.of(1035, SpeedController.class);

    /**
     * Defines a TalonSRX CAN speed (motor) controller, based on the <em>phoenix v5 framework</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the device on the CAN bus.</li>
     * </ul>
     */
    public static final DeviceId<CTRETalon> CANTalonSRX = DeviceId.of(1036, CTRETalon.class);

    /**
     * Defines a TalonFX CAN speed (motor) controller, based on the <em>phoenix v5 framework</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the device on the CAN bus.</li>
     * </ul>
     */
    public static final DeviceId<CTRETalon> CANTalonFX = DeviceId.of(1037, CTRETalon.class);

    /**
     * Defines a VictorSPX CAN speed (motor) controller, based on the <em>phoenix v5 framework</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the device on the CAN bus.</li>
     * </ul>
     */
    public static final DeviceId<SpeedController> CANVictorSPX = DeviceId.of(1038, SpeedController.class);

    /**
     * Defines a TalonFX CAN speed (motor) controller, based on the <em>phoenix v6 framework</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the device on the CAN bus.</li>
     * </ul>
     */
    public static final DeviceId<CTRETalon> CANTalonFX_Phoenix6 = DeviceId.of(1039, CTRETalon.class);

    /**
     * Defines a basic servo connected via a PWM port.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the PWM channel the device is connected to.</li>
     * </ul>
     */
    public static final DeviceId<PositionController> Servo = DeviceId.of(1061, PositionController.class);

    /**
     * Defines a basic ultrasonic range finder which is connected to a digital port. Works for devices
     * which use two channels: trigger which forces the device to send a pulse, and echo which the device uses
     * to reply with the distance.
     *
     * Based on <em>HC-SR04</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>pingChannel</em> (<em>int</em>): identifier of the digital channel used to trigger pings (trigger).</li>
     *     <li><em>echoChannel</em> (<em>int</em>): identifier of the digital channel used to read range (echo).</li>
     * </ul>
     */
    public static final DeviceId<RangeFinder> Ultrasonic = DeviceId.of(1071, RangeFinder.class);

    /**
     * Defines a basic analog single-axis accelerometer.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the analog channel used to read measurements.</li>
     *     <li><em>zeroGVoltage</em> (<em>double</em>): voltage when acceleration is 0, as defined by the device.</li>
     *     <li><em>voltsPerG</em> (<em>double</em>): conversion factor from volts to G acceleration.</li>
     * </ul>
     */
    public static final DeviceId<Accelerometer> AnalogAccelerometer = DeviceId.of(1072, Accelerometer.class);

    /**
     * Defines a basic analog single-axis gyro.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the analog channel used to read measurements.</li>
     * </ul>
     */
    public static final DeviceId<Gyro> AnalogGyro = DeviceId.of(1073, Gyro.class);

    /**
     * Defines a CANCoder device, based on the <em>phoenix v6 framework</em>.
     *
     * <p>
     * Params:
     * <ul>
     *     <li><em>channel</em> (<em>int</em>): identifier of the device on the CAN bus.</li>
     * </ul>
     */
    public static final DeviceId<CANCoder> CANCoder_Phoenix6 = DeviceId.of(1075, CANCoder.class);
}
