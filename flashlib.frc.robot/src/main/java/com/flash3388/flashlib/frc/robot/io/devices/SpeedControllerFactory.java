package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.io.devices.SpeedController;
import edu.wpi.first.wpilibj.motorcontrol.DMC60;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.NidecBrushless;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVenom;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.SD540;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class SpeedControllerFactory {

    private SpeedControllerFactory() {}

    public static SpeedController newTalon(int channel) {
        return frcController(new Talon(channel));
    }

    public static SpeedController newVictor(int channel) {
        return frcController(new Victor(channel));
    }

    public static SpeedController newVictorSP(int channel) {
        return frcController(new VictorSP(channel));
    }

    public static SpeedController newSpark(int channel) {
        return frcController(new Spark(channel));
    }

    public static SpeedController newSD540(int channel) {
        return frcController(new SD540(channel));
    }

    public static SpeedController newJaguar(int channel) {
        return frcController(new Jaguar(channel));
    }

    public static SpeedController newDMC60(int channel) {
        return frcController(new DMC60(channel));
    }

    public static SpeedController newNidecBrushless(int pwmChannel, int dioChannel) {
        return frcController(new NidecBrushless(pwmChannel, dioChannel));
    }

    public static SpeedController newPwmSparkMax(int channel) {
        return frcController(new PWMSparkMax(channel));
    }

    public static SpeedController newPwmTalonFX(int channel) {
        return frcController(new PWMTalonFX(channel));
    }

    public static SpeedController newPwmTalonSRX(int channel) {
        return frcController(new PWMTalonSRX(channel));
    }

    public static SpeedController newPwmVenom(int channel) {
        return frcController(new PWMVenom(channel));
    }

    public static SpeedController newPwmVictorSPX(int channel) {
        return frcController(new PWMVictorSPX(channel));
    }

    public static SpeedController frcController(MotorController controller) {
        return new FrcSpeedController(controller);
    }
}
